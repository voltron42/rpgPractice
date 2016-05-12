package werewolves

import (
  "bufio"
  "os"
)

func Play(GameXML xml) error {
  reader := bufio.NewReader(os.Stdin)
  fmt.Print("WHAT IS YOUR NAME, EXPLORER?")
  name, err := reader.ReadString('\n')
  if err != nil {
    return err
  }
  game := buildGame(xml, name)
}

func buildGame(xml GameXML, name string) Game {
  game := Game{}
  game.Player = Player{}
  game.Player.Name = name
  game.Player.CurrentRoom = xml.Init.FirstRoom
  game.Player.Strength = xml.Init.InitStats.Strength
  game.Player.Wealth = xml.Init.InitStats.Wealth
  game.Player.Tally = xml.Init.InitStats.Tally
  game.Player.Inventory = map[string]InventoryItem{}
  game.Maze.Entrance = xml.Init.FirstRoom
  game.Maze.Exit = xml.Init.LastRoom
  game.Maze.Rooms = map[int]Room{}
  for index, roomXML := range xml.Rooms {
    room := Room{}
    room.Description = roomXML.Lines
    room.Contents = roomXML.Treasure.Eval()
    room.Doors = map[string]int{}
    if roomXML.Doors.North < 0 {
      room.Doors["NORTH"] = roomXML.Doors.North
    }
    if roomXML.Doors.South < 0 {
      room.Doors["SOUTH"] = roomXML.Doors.South
    }
    if roomXML.Doors.East < 0 {
      room.Doors["EAST"] = roomXML.Doors.East
    }
    if roomXML.Doors.West < 0 {
      room.Doors["WEST"] = roomXML.Doors.West
    }
    if roomXML.Doors.Up < 0 {
      room.Doors["UP"] = roomXML.Doors.Up
    }
    if roomXML.Doors.Down < 0 {
      room.Doors["DOWN"] = roomXML.Doors.Down
    }
    game.Maze.Rooms[roomXML.Index] = room
  }
  game.Monsters = map[int]Monster{}
  for index, monsterXml := range xml.Monsters {
    monster := Monster{}
    monster.Name = monsterXml.Name
    monster.Danger = monsterXml.DangerLevel
    game.Monsters[monsterXml.Index] = monster
  }
  game.Shop = map[string]Item{}
  for index, shopItemXml := range xml.ShopItems {
    item := Item{}
    item.Name = shopItemXml.Name
    item.Cost = shopItemXml.Cost
    item.Limit = shopItemXml.Limit
    item.Effect = shopItemXml.Effect
    game.Shop[shopItemXml.Name] = item
  }
  rooms := map[int]bool
  return game
}
