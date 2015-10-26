package main

import (
	"fmt"
	"strconv"
)

func main() {
	gm := MyGM{}
	for (*myGame.Stats)["STRENGTH"] > 0 && (*myGame.Stats)["LOCATION"] != 27 {
		chapterIndex := (*myGame.Stats)["LOCATION"]
		chapter := myGame.Chapters[chapterIndex]
		if len(chapter.Description) > 0 {
			gm.Narrate(chapter.Description)
		}
		for _, action := range chapter.Actions {
			action.DoAction(gm, myGame.Stats)
		}
		strength := (*myGame.Stats)["STRENGTH"]
		location := (*myGame.Stats)["LOCATION"]
		if strength > 0 && chapterIndex == location {
			optionIndex := -1
			count := len(chapter.Options)
			if count > 1 {
				for optionIndex < 0 {
					gm.Narrate("What do you wish to do? (Enter the number for the option.)")
					for index, option := range chapter.Options {
						gm.Narrate(fmt.Sprintf("%v. %v", index, option.Description))
					}
					response := gm.Query()
					selectedIndex, err := strconv.Atoi(response)
					if err == nil && selectedIndex < count && selectedIndex >= 0 {
						optionIndex = selectedIndex
					}
				}
			} else {
				optionIndex = 0
			}
			option := chapter.Options[optionIndex]
			if len(option.Description) > 0 {
				gm.Narrate(option.Description)
			}
			(*myGame.Stats)["LOCATION"] = option.Route
		}
	}
	score := 5*(*myGame.Stats)["STRENGTH"] + 2*(*myGame.Stats)["WEALTH"] + 30*(*myGame.Stats)["MONSTER TALLY"]
	final := fmt.Sprintf("Your score is %v. You have come to the end of the adventure.", score)
	gm.Narrate(final)
}

type MyGM struct {
}

func (gm MyGM) Narrate(narrative string) {
	fmt.Println(narrative)
}

func (gm MyGM) Query() string {
	response := ""
	fmt.Scanln(response)
	return response
}
