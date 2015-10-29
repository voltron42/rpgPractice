package main

import (
	"../util/dir"
	"../util/gm"
	"fmt"
	"strings"
)

func (g Game) GameOver() {
	g.GM.Narrate("")
	g.GM.Narrate("You are dead. Your skeleton has been added to the others that will never escape.")
	g.GM.Narrate("")
	g.GM.Narrate("")
	g.GM.Narrate("\t\t... Poor bastard.")
}

func (g Game) Congratulate() {
	g.GM.Narrate("")
	g.GM.Narrate("You've found your way out of the dungeon! Well done.")
}

func (d Dungeon) GetRoom(id dir.ID) (Room, error) {
	room, ok := d.Rooms[id]
	var err error
	if !ok {
		err = fmt.Errorf("Room %v does not exist.", id)
	}
	return room, err
}

func (r Room) Describe(myGM gm.GM) {
	myGM.Narrate(fmt.Sprintf("You have entered the %v.", r.Name))
	if len(r.Description) > 0 {
		myGM.Narrate(r.Description)
	}
}

func (p Player) Update(myGM gm.GM) {
	myGM.Narrate("Your current status:")
	for stat, value := range p.Stats {
		myGM.Narrate(fmt.Sprintf("\t%v\t%v", stat, value))
	}
	myGM.Narrate("")
}

func (p Player) Retrieve(reward *Treasure, myGM gm.GM) {
	if reward != nil {
		myGM.Narrate("You've found a Treasure!")
		myGM.Narrate("You open the treasure and see that it contains the following:")
		for _, item := range *reward {
			myGM.Narrate(fmt.Sprintf("\t%v", item.Name))
		}
	}
	myGM.Narrate("")
}

func (p Player) Fight(monster Monster, myGM gm.GM) {

}

func (p Player) Lives() bool {
	return true
}

func (p Player) TakeStock(myGM gm.GM) bool {
	return false
}

func (p Player) Proceed(myGM gm.GM, room Room) dir.ID {
	var destination (*dir.ID)
	for destination == nil {
		myGM.Narrate("Which way would you like to go?")
		for direction, _ := range room.Exits {
			myGM.Narrate(strings.ToUpper(direction.String()))
		}
		destStr := myGM.Query()
		direction, err := dir.Parse(destStr)
		if err == nil {
			dest, ok := room.Exits[direction]
			if ok {
				destination = &dest
			}
		}
	}
	return *destination
}
