package main

import (
	"../util/dir"
	"../util/gm"
	"fmt"
	"strings"
)

func (g Campaign) GameOver() {
	g.GM.Narrate("")
	g.GM.Narrate("You are dead. Your skeleton has been added to the others that will never escape.")
	g.GM.Narrate("")
	g.GM.Narrate("")
	g.GM.Narrate("\t\t... Poor bastard.")
}

func (g Campaign) Congratulate() {
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
	myGM.NarrateF("You have entered the %v.", r.Name)
	if len(r.Description) > 0 {
		myGM.Narrate(r.Description)
	}
}

func (p Player) Update(myGM gm.GM) {
	// todo -- include Equiptment stats in calculations
	myGM.Narrate("Your current status:")
	for stat, value := range p.Stats {
		myGM.NarrateF("\t%v\t%v", stat, value)
	}
	myGM.Narrate("")
}

func (p Player) Retrieve(reward *Treasure, myGM gm.GM) {
	if reward != nil {
		myGM.Narrate("You've found a Treasure!")
		myGM.Narrate("You open the treasure and see that it contains the following:")
		for _, item := range *reward {
			myGM.NarrateF("\t%v", item)
			count, ok := p.Inventory[item]
			if ok {
				p.Inventory[item] = count + 1
			} else {
				p.Inventory[item] = 1
			}
		}
	}
	myGM.Narrate("")
}

func (p Player) Fight(monster Monster, myGM gm.GM) {
	// todo
}

func (p Player) Lives() bool {
	return p.Stats[HEALTH] > 0
}

func (p Player) TakeStock(myGM gm.GM, q QuarterMaster) {
	selection := -1
	length := len(takeStockOptions)
	request := []string{"What would you like to do?"}
	for index, option := range takeStockOptions {
		request = append(request, fmt.Sprintf("%v - %v", index, option))
	}
	request = append(request, fmt.Sprintf("\t%v - Move on it game", length))
	message := strings.Join(request, "\n\t")
	for selection < length && selection >= -1 {
		response := myGM.QueryInt(message)
		if response >= length {
			break
		}
		if response >= 0 {
			action := takeStockFunctions[response]
			action(p, myGM, q)
			p.Update(myGM)
		}
	}
}

var takeStockOptions = []string{
	"Buy From QuarterMaster",
	"Sell To QuarterMaster",
	"Use From Inventory",
	"Store Equiptment",
}

var takeStockFunctions = []func(p Player, myGM gm.GM, q QuarterMaster){
	func(p Player, myGM gm.GM, q QuarterMaster) {
		p.buy(myGM, q)
	},
	func(p Player, myGM gm.GM, q QuarterMaster) {
		p.sell(myGM, q)
	},
	func(p Player, myGM gm.GM, q QuarterMaster) {
		p.use(myGM, q)
	},
	func(p Player, myGM gm.GM, q QuarterMaster) {
		p.unequip(myGM, q)
	},
}

func (p Player) buy(myGM gm.GM, q QuarterMaster) {
	// todo
}

func (p Player) sell(myGM gm.GM, q QuarterMaster) {
	// todo
}

func (p Player) use(myGM gm.GM, q QuarterMaster) {
	// todo
}

func (p Player) unequip(myGM gm.GM, q QuarterMaster) {
	// todo
}

func (p Player) Proceed(myGM gm.GM, room Room) dir.ID {
	var destination (*dir.ID)
	request := []string{"Which way would you like to go?"}
	for direction, _ := range room.Exits {
		request = append(request, strings.ToUpper(direction.String()))
	}
	message := strings.Join(request, "\n\t")
	for destination == nil {
		destStr := myGM.Query(message)
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
