package main

import (
	"../util/gm"
)

type Campaign struct {
	GM     gm.GM
	Game   Game
	Player Player
}

func NewCampaign(GM gm.GM, Game Game, Player Player) Campaign {
	return Campaign{GM, Game, Player}
}

func (g Campaign) Play() {
	for _, dungeon := range g.Game.Dungeons {
		location := dungeon.Entrance
		for g.Player.Lives() && location != dungeon.Exit {
			room, err := dungeon.GetRoom(location)
			if err != nil {
				panic(err)
			}
			room.Describe(g.GM)
			if room.Resident != nil {
				g.Player.Update(g.GM)
				monster := g.Game.Monsters[*room.Resident]
				g.Player.Fight(monster, g.GM)
				if !g.Player.Lives() {
					break
				}
			}
			g.Player.Retrieve(room.Reward, g.GM)
			g.Player.Update(g.GM)
			g.Player.TakeStock(g.GM, dungeon.QuarterMaster)
			location = g.Player.Proceed(g.GM, room)
		}
	}
	if !g.Player.Lives() {
		g.GameOver()
	} else {
		g.Congratulate()
	}
}
