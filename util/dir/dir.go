package dir

import (
	"errors"
	"sort"
	"strings"
)

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
