package werewolves

import (
  "fmt"
)

type Game struct {
  Player *Player
  Maze *Maze
  Monsters map[int]*Monster
  Shop map[string]*Item
}

type Player struct {
  Name string
  CurrentRoom int
  Path []int
  Strength int
  Wealth int
  Tally int
  Light bool
  Inventory map[string]*InventoryItem
}

func (p *Player) String() string {
  inventory := ""
  for name, item := range p.Inventory {
    inventory += fmt.Sprintf("%v (%v)\n", name, item.Count)
  }
  return fmt.Sprintf("STRENGTH: %v\nWEALTH: %v\n%v", p.Strength, p.Wealth, inventory)
}

type InventoryItem struct {
  Name string
  Count int
  Effect string
  Equipt bool
  MustEquip bool
}

type Maze struct {
  Entrance int
  Exit int
  Rooms map[int]*Room
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
  MustEquip bool
}
