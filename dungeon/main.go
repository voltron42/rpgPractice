package main

import (
	"../util/gm"
)

func main() {
	myGM := gm.New()
	dungeon := Dungeon{}
	player := Player{}
	game := NewGame(myGM, dungeon, player)
	game.Play()
}
