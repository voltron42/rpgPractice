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
	for g.Player.Health > 0 && location != g.Dungeon.Exit {
		room, err := g.Dungeon.GetRoom(location)
		if err != nil {
			panic(err)
		}
		room.Describe(g.GM)
		g.Player.Update(g.GM)
		g.Player.Retrieve(room.Reward, g.GM)
		g.Player.Fight(room.Resident, g.GM)
		if g.Player.Health <= 0 {
			break
		}
		g.Player.Update(g.GM)
		g.Player.TakeStock(g.GM)
		g.Player.Update(g.GM)
		location = g.Player.Proceed(g.GM)
	}
	if g.Player.Health <= 0 {
		g.GameOver()
	} else {
		g.Congratulate()
	}
}
