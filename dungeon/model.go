package main

import (
	"../util/dir"
)

type Player struct {
	Stats      map[string]int
	Equiptment []Item
	Inventory  []Item
	// todo
}

type Dungeon struct {
	Entrance dir.ID
	Exit     dir.ID
	Rooms    map[dir.ID]Room
}

type Room struct {
	Name        string
	Description string
	Exits       map[dir.Direction]dir.ID
	Resident    *Monster
	Reward      *Treasure
}

type Monster struct {
	Name        string
	Description string
	Attacks     []Attack
}

type Attack struct {
	// todo
}

type Treasure []Item

type Item struct {
	Name        string
	Description string
	Stats       map[string]int
	Usage       string
	Weight      int
	// todo
}
