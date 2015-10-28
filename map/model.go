package main

import (
	"errors"
	"sort"
	"strings"
)

type FloorPlan map[ID]Room

type Room map[Direction]ID

type ID int

type Direction int

const (
	EAST Direction = iota
	NORTH
	SOUTH
	WEST
)

var directions = []string{
	"east",
	"north",
	"south",
	"west",
}

func Parse(dir string) (Direction, error) {
	myDir := strings.ToLower(dir)
	index := sort.SearchStrings(directions, myDir)
	if directions[index] == myDir {
		return Direction(index), nil
	}
	return -1, errors.New("")
}

func (d Direction) String() string {
	return directions[d]
}
