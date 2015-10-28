package main

import (
	"../util/dir"
)

type Player struct {
	Health int
	// todo
}

type Dungeon struct {
	Entrance dir.ID
	Exit     dir.ID
	Rooms    map[int]Room
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
	Effect      *Effect
}

type Effect struct {
	// todo
}
