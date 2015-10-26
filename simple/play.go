package main

import (
	"fmt"
	"strconv"
)

func main() {
	gm := MyGM{}
	for (*myGame.Stats)["STRENGTH"] > 0 {
		chapterIndex := (*myGame.Stats)["LOCATION"]
		chapter := myGame.Chapters[chapterIndex]
		gm.Narrate(chapter.Description)
		if chapter.Action != nil {
			chapter.Action.DoAction(gm, myGame.Stats)
		}
		location := (*myGame.Stats)["LOCATION"]
		if chapterIndex == location {
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
			gm.Narrate(option.Description)
			(*myGame.Stats)["LOCATION"] = option.Route
		}
	}
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
