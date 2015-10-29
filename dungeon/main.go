package main

import (
	"../util/gm"
)

func main() {
	myGM := gm.New()
	dungeon := myDungeon
	player := Player{}
	game := NewGame(myGM, dungeon, player)
	game.Play()
}
