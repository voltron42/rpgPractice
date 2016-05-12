package werewolves

import (
  "bufio"
  "os"
  "sort"
  "fmt"
  "math/rand"
)

var reader = bufio.NewReader(os.Stdin)

func Play(xml GameXML) error {
  fmt.Printf("WELCOME TO %v\n", xml.Title)
  fmt.Print("WHAT IS YOUR NAME, EXPLORER?")
  name, err := reader.ReadString('\n')
  if err != nil {
    return err
  }
  game := buildGame(xml, name)
  //fmt.Println(game);
  for game.Player.CurrentRoom != game.Maze.Exit && game.Player.Strength > 0 {
    room, ok := game.Maze.Rooms[game.Player.CurrentRoom]
    if !ok {
      panic("rooms out of sync")
    }
    for _, line := range room.Description {
      fmt.Println(line)
    }
    fmt.Println(game.Player)
    if room.Contents > 0 {
      fmt.Println("THERE IS TREASURE HERE WORTH %v GOLD!")
      game.Player.Wealth += room.Contents
      room.Contents = 0
    } else if room.Contents < 0 {
      monster, ok := game.Monsters[room.Contents];
      if ok {
        combat(game.Player, monster)
      } else {
        room.Contents = 0
      }
    }
    if game.Player.Strength > 0 {
      fmt.Println("What do you want to do?")
    }
  }
  if game.Player.Strength <= 0 {
    fmt.Println("YOU HAVE DIED.........")
  }
  return nil
}

func combat(player *Player, monster *Monster) {
  fmt.Println("DANCER...THERE IS A MONSTER HERE....")
  fmt.Println("IT IS A %v")
  fmt.Println("THE DANGER LEVEL IS %v!!")
  first := true;
  list := ""
  for key, _ := range combatActions {
    if (first) {
      first = false
    } else {
      list += ","
    }
    list += key
  }
  repeat := true;
  for repeat {
    action := func(player *Player, monster *Monster) bool {
      return false
    }
    ok := false
    for !ok {
      fmt.Printf("WHAT DO YOU WANT TO DO? (%v)\n",list)
      key, err := reader.ReadString('\n')
      if err != nil {
        panic(err)
      }
      action, ok = combatActions[key]
      if !ok {
        fmt.Printf("THIS IS NOT A VALID ACTION (%v)\n", key)
      }
    }
    repeat = action(player, monster)
  }
}

var combatActions = map[string]func(player *Player, monster *Monster) bool {
  "ATTACK":func(player *Player, monster *Monster) bool {
    if rand.Intn(2) > 1 {

    } else {

    }
    return false
  },
  "FLEE":func(player *Player, monster *Monster) bool {
    if rand.Intn(10) < 7 {
      fmt.Println("YOU RUN AWAY!")
      last := len(player.Path) - 1
      player.CurrentRoom = player.Path[last]
      player.Path = player.Path[:last]
      return false
    } else {
      fmt.Println("YOU CANNOT RUN AWAY!")
      fmt.Println("YOU MUST STAND AND FIGHT")
      return true
    }
  },
  "EAT":func(player *Player, monster *Monster) bool {
    item, ok := player.Inventory["FOOD"]
    if !ok {
      fmt.Println("You have no food")
    }
    if item.Count <= 0 {
      fmt.Println("You have no food")
    }
    player.Strength += 5
    item.Count -= 1
    return true;
  },
}

func buildGame(xml GameXML, name string) *Game {
  game := &Game{}
  game.Player = &Player{}
  game.Player.Name = name
  game.Player.CurrentRoom = xml.Init.FirstRoom
  game.Player.Path = []int{}
  game.Player.Strength = xml.Init.InitStats.Strength
  game.Player.Wealth = xml.Init.InitStats.Wealth
  game.Player.Tally = xml.Init.InitStats.Tally
  game.Player.Inventory = map[string]*InventoryItem{}
  game.Maze.Entrance = xml.Init.FirstRoom
  game.Maze.Exit = xml.Init.LastRoom
  game.Maze.Rooms = map[int]*Room{}
  for _, roomXML := range xml.Rooms {
    room := &Room{}
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
  game.Monsters = map[int]*Monster{}
  for _, monsterXml := range xml.Monsters {
    monster := &Monster{}
    monster.Name = monsterXml.Name
    monster.Danger = monsterXml.DangerLevel
    game.Monsters[monsterXml.Index] = monster
  }
  game.Shop = map[string]*Item{}
  for _, shopItemXml := range xml.ShopItems {
    item := &Item{}
    item.Name = shopItemXml.Name
    item.Cost = shopItemXml.Cost
    item.Limit = shopItemXml.Limit
    item.Effect = shopItemXml.Effect
    game.Shop[shopItemXml.Name] = item
  }
  rooms := []int{}
  for index, room := range game.Maze.Rooms {
    if index != game.Maze.Entrance && index != game.Maze.Exit && room.Contents == 0 {
      rooms = append(rooms, index);
    }
  }
  for monsterIndex, _ := range game.Monsters {
    room := &Room{}
    ok := false
    for !ok {
      roomNumber := rand.Intn(len(rooms))
      room, ok = game.Maze.Rooms[roomNumber]
      room.Contents = monsterIndex
      rooms = append(rooms[:roomNumber], rooms[roomNumber+1:]...)
    }
  }
  placeTreasure := xml.Init.PlaceTreasure
  for count := 0; count < placeTreasure.RoomCount; count++ {
    room := &Room{}
    ok := false
    for !ok {
      roomNumber := rand.Intn(len(rooms))
      room, ok = game.Maze.Rooms[roomNumber]
      room.Contents = placeTreasure.Treasure.Eval()
      rooms = append(rooms[:roomNumber], rooms[roomNumber+1:]...)
    }
  }
  sort.Ints(rooms)
  return game
}
