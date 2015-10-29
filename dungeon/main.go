package main

import (
	"../util/gm"
)

func main() {
	myGM := gm.New()
	game := myGame
	player := Player{}
	campaign := NewCampaign(myGM, game, player)
	campaign.Play()
}
