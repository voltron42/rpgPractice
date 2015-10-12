package citadel

type Room struct {
	Id int
	Name string
	Description string
	Death string
	North int
	South int
	East int
	West int
	Up int
	Down int
}

type GameMap map[int]Room

func NewGameMap() *GameMap {
	return &GameMap{
		1:Room{
			Id:1,
			Name:"River",
			North:1,
			South:4,
			West:8,
		},
		2:Room{
			Id: 2,
			Name: "Food Store",
			South: 5,
			West: 3,
		},
		3:Room{
			Id: 3,
			Name: "Kitchen",
			West: 2,
			North: 3,
			South: 7,
		},
		4:Room{
			Id: 4,
			Name: "Library",
			North: 1,
			East: 5,
		},
		5:Room{
			Id: 5,
			Name: "Studio",
			West: 4,
			North: 2,
		},
		6:Room{
			Id: 6,
			Name: "Entrance",
			East: 7,
		},
		7:Room{
			Id: 7,
			Name: "Altar",
			North: 3,
			West: 6,
			South: 14,
			East: 15,
		},
		8:Room{
			Id: 8,
			Name: "Black Tower:,
			North: 1,
			South: 8,
		},
		9:Room{
			Id: 9,
			Name: "North Cellar",
			North: 10,
			South: 11,
		},
		10:Room{
			Id: 10,
			Name: "West Cellar",
			West: 9,
			East: 11,
		},
		11:Room{
			Id:11,
			Name: "Cellar",
			North: 9,
			West: 10,
			South: 13,
			East: 12,
		},
		12:Room{
			Id:12,
			Name: "East Cellar",
			West: 11,
			East: 15,
		},
		13:Room{
			Id:13,
			Name: "South Cellar",
			North: 11,
			South: 16,
			Death: "Drowning",
		},
		14:Room{
			Id:14,
			Name: "Armory",
			North: 7,
		},
		15:
	}
}
