package main

import (
	"../util/dir"
)

var myGame = Game{
	Dungeons: []Dungeon{
		Dungeon{
			Entrance: 1,
			Exit:     5,
			Rooms: map[dir.ID]Room{
				1: Room{
					Name: "Front Hall",
					Exits: map[dir.Direction]dir.ID{
						dir.EAST: 2,
					},
				},
				2: Room{
					Name: "Lounge",
					Exits: map[dir.Direction]dir.ID{
						dir.SOUTH: 3,
						dir.WEST:  1,
					},
				},
				3: Room{
					Name: "Study",
					Exits: map[dir.Direction]dir.ID{
						dir.WEST:  4,
						dir.NORTH: 2,
					},
				},
				4: Room{
					Name: "Conservatory",
					Exits: map[dir.Direction]dir.ID{
						dir.EAST:  3,
						dir.SOUTH: 5,
					},
				},
			},
		},
	},
}
