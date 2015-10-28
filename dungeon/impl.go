package main

import (
	"../util/dir"
	"../util/gm"
)

func (g Game) GameOver() {

}

func (g Game) Congratulate() {

}

func (d Dungeon) GetRoom(id dir.ID) (Room, error) {
	return Room{}, nil
}

func (r Room) Describe(myGM gm.GM) {

}

func (p Player) Update(myGM gm.GM) {

}

func (p Player) Retrieve(reward *Treasure, myGM gm.GM) {

}

func (p Player) Fight(monster *Monster, myGM gm.GM) {

}

func (p Player) TakeStock(myGM gm.GM) {

}

func (p Player) Proceed(myGM gm.GM) dir.ID {
	return 1
}
