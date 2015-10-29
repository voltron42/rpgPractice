package main

import (
	"../util/gm"
)

type Game struct {
	GM      gm.GM
	Dungeon Dungeon
	Player  Player
}

func NewGame(GM gm.GM, Dungeon Dungeon, Player Player) Game {
	return Game{GM, Dungeon, Player}
}

func (g Game) Play() {
	location := g.Dungeon.Entrance
	for g.Player.Lives() && location != g.Dungeon.Exit {
		room, err := g.Dungeon.GetRoom(location)
		if err != nil {
			panic(err)
		}
		room.Describe(g.GM)
		if room.Resident != nil {
			g.Player.Update(g.GM)
			g.Player.Fight(*room.Resident, g.GM)
			if !g.Player.Lives() {
				break
			}
		}
		g.Player.Retrieve(room.Reward, g.GM)
		g.Player.Update(g.GM)
		hasUpdated := g.Player.TakeStock(g.GM)
		if hasUpdated {
			g.Player.Update(g.GM)
		}
		location = g.Player.Proceed(g.GM, room)
	}
	if !g.Player.Lives() {
		g.GameOver()
	} else {
		g.Congratulate()
	}
}
