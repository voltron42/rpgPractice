package main

import (
	"encoding/xml"
	"fmt"
	"io/ioutil"

	"./app"
)

func main() {
	bytes, err := ioutil.ReadFile("./data.xml")
	if err != nil {
		panic(err)
	}
	game := werewolves.GameXML{}
	err = xml.Unmarshal(bytes, &game)
	if err != nil {
		panic(err)
	}
	fmt.Printf("%v\n", game)
}
