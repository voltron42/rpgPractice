package main

import (
	"../util/dir"
)

type Player struct {
	Stats      map[Stat]int
	Equiptment []string
	Inventory  map[string]int
}

type Stat string

const (
	ATTACK  Stat = "ATTACK"
	DEFENSE Stat = "DEFENSE"
	HEALTH  Stat = "HEALTH"
	WEALTH  Stat = "WEALTH"
)

type Game struct {
	Dungeons []Dungeon
	Monsters map[string]Monster
	Items    map[string]Item
}

type Dungeon struct {
	Entrance      dir.ID
	Exit          dir.ID
	Rooms         map[dir.ID]Room
	QuarterMaster QuarterMaster
}

type Room struct {
	Name        string
	Description string
	Exits       map[dir.Direction]dir.ID
	Resident    *string
	Reward      *Treasure
}

type Monster struct {
	Name        string
	Description string
	Attack      int
	Block       int
}

type Treasure []string

type Item struct {
	Name        string
	Description string
	Stats       map[Stat]int
	Usage       string
	Cost        int
	Trade       int
	// todo
}

type QuarterMaster []string
