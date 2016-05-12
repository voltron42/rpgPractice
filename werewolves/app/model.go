package werewolves

type Game struct {
  Player Player
  Maze Maze
  Monsters map[int]Monster
  Shop map[string]Item
}

type Player struct {
  Name string
  CurrentRoom int
  Strength int
  Wealth int
  Tally int
  Light bool
  Inventory map[string]InventoryItem
}

type InventoryItem struct {
  Name string
  Count int
  Effect string
}

type Maze struct {
  Entrance int
  Exit int
  Rooms map[int]Room
}

type Room struct {
  Description []string
  Doors map[string]int
  Contents int
}

type Monster struct {
  Name string
  Danger int
}

type Item struct {
  Name  string
  Cost int
  Limit int
  Effect string
}
