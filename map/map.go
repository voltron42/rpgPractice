package main

import (
	"../gm"
	"errors"
	"fmt"
	"strings"
)

func main() {
	myGm := gm.New()
	location := ID(1)
	for true {
		room, ok := myMap[ID(location)]
		if !ok {
			panic("invalid location key")
		}
		options := []string{}
		for dir, _ := range room {
			options = append(options, strings.ToUpper(dir.String()))
		}
		choice := Direction(-1)
		var err error
		for choice == -1 || err != nil {
			myGm.Narrate(fmt.Sprintf("You are in room %v.", location))
			myGm.Narrate(fmt.Sprintf("You may go %v.", strings.Join(options, ", ")))
			myGm.Narrate("Where do you wish to go?")
			response := myGm.Query()
			myGm.Narrate(fmt.Sprintf("response: (%v)", response))
			choice, err = Parse(response)
			myGm.Narrate(fmt.Sprintf("choice: %v", choice))
			newLoc, ok := room[choice]
			if !ok {
				err = errors.New("Must choose one of the directions for this room")
				myGm.Narrate(err.Error())
			} else {
				location = newLoc
			}
			myGm.Narrate("")
		}
	}
}
