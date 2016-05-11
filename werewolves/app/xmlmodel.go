package werewolves

import (
	"encoding/xml"
	"strings"
	"strconv"
	"fmt"
)

type GameXML struct {
	Title     string     `xml:"title,attr"`
	Init      Init       `xml:"init"`
	ShopItems []ShopItem `xml:"shop>item"`
	Monsters  []Monster  `xml:"monsters>monster"`
	Rooms     []Room     `xml:"rooms>room"`
	Doors     []Door     `xml:"doors>door"`
}

type Init struct {
	FirstRoom int       `xml:"first-room,attr"`
	LastRoom  int       `xml:"last-room,attr"`
	InitStats InitStats `xml:"stats"`
	PlaceTreasure PlaceTreasure `xml:"place-treasure"`
}

type InitStats struct {
	Strength int `xml:"strength,attr"`
	Wealth   int `xml:"wealth,attr"`
	Food     int `xml:"food,attr"`
	Tally    int `xml:"tally,attr"`
}

type PlaceTreasure struct {
	RoomCount int `xml:"room-count,attr"`
	Treasure Treasure `xml:"treasure,attr"`
}

type ShopItem struct {
	Index int    `xml:"index,attr"`
	Name  string `xml:"name,attr"`
	Cost int `xml:"cost,attr"`
	Limit int `xml:"limit,attr"`
	Effect string `xml:"effect,attr"`
}

type Monster struct {
	Index int `xml:"index,attr"`
	Name string `xml:"name,attr"`
	DangerLevel int `xml:"danger-level,attr"`
}

type Room struct {
	Index int `xml:"index,attr"`
	Treasure Treasure `xml:"treasure,attr"`
	Lines []string `xml:"description>line"`
}

type Door struct {
	Index int `xml:"index,attr"`
	North int `xml:"north,attr"`
	South int `xml:"south,attr"`
	East int `xml:"east,attr"`
	West int `xml:"west,attr"`
	Up int `xml:"up,attr"`
	Down int `xml:"down,attr"`
}

type Treasure struct {
	DieCount int
	SideCount int
	Adder int
}

func (t *Treasure) UnmarshalXMLAttr(attr xml.Attr) error {
	value := strings.Split(attr.Value,"+")
	die := strings.Split(value[0],"d")
	i, err := strconv.Atoi(die[0])
	if err != nil {
		return err
	}
	t.DieCount = i
	i, err = strconv.Atoi(die[1])
	if err != nil {
		return err
	}
	t.SideCount = i
	if len(value) > 1 {
		i, err = strconv.Atoi(value[1])
		if err != nil {
			return err
		}
		t.Adder = i
	}
	return nil
}