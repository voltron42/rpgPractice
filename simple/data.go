package main

var myGame = Game{
	Stats: &Stats{
		"STRENGTH":      50,
		"WEALTH":        50,
		"MONSTER TALLY": 0,
		"LOCATION":      7,
	},
	Chapters: Chapters{
		1: Chapter{
			Description: "You are in a dank, dark dungeon. The only light comes into the room from a small hole in the west wall.",
			Action: &RandomAction{
				Randomizer: Coins(2),
				Results: []RandomActionResult{
					RandomActionResult{
						Results: []Results{
							Results{"heads", "heads"},
						},
						Actions: []Action{
							&Change{
								Stat:     "LOCATION",
								Value:    11,
								Operator: Set,
							},
						},
					},
					RandomActionResult{
						Results: []Results{
							Results{"tails", "tails"},
						},
						Actions: []Action{
							&Change{
								Stat:     "LOCATION",
								Value:    5,
								Operator: Set,
							},
						},
					},
				},
			},
			Options: []Option{
				Option{
					Description: "You Leave the dungeon.",
					Route:       25,
				},
			},
		},
		2: Chapter{
			Description: "You are in the L-shaped upper hallway of the castle. A moth flutters across the room near the ceiling. To the north is a door and there is a stairwell in the hall as well.",
			Action: &RandomAction{
				Randomizer: Coins(2),
				Results: []RandomActionResult{
					RandomActionResult{
						Results: []Results{
							Results{"tails", "tails"},
						},
						Actions: []Action{
							&Change{
								Stat:     "LOCATION",
								Value:    13,
								Operator: Set,
							},
						},
					},
				},
			},
			Options: []Option{
				Option{
					Description: "Peek through the door to the north.",
					Route:       33,
				},
				Option{
					Description: "Look down the stairwell.",
					Route:       39,
				},
				Option{
					Description: "Go through the door.",
					Route:       30,
				},
				Option{
					Description: "Go down the stairs.",
					Route:       21,
				},
			},
		},
		5: Chapter{
			Description: "The ghost of the guard has awoken!",
			Action: &Change{
				Description: "Your strength is halved du to the fright you suffered.",
				Stat:        "STRENGTH",
				Value:       2,
				Operator:    Div,
			},
			Options: []Option{
				Option{
					Route: 1,
				},
			},
		},
		6: Chapter{
			Description: "You are in the Great Hall of the castle. There are two doors in the L-shaped room. You notice the wood panels around the room are warped and faded. As you look around, a mouse scampers across the floor. You whirl around at a sudden noise.",
			Action: &RandomAction{
				Randomizer: Coins(1),
				Results: []RandomActionResult{
					RandomActionResult{
						Results: []Results{
							Results{"heads"},
						},
						Actions: []Action{
							&Change{
								Stat:     "LOCATION",
								Value:    43,
								Operator: Set,
							},
						},
					},
					RandomActionResult{
						Description: "You see to your relief that there is nothing there.",
						Results: []Results{
							Results{"tails"},
						},
						Actions: []Action{},
					},
				},
			},
			Options: []Option{
				Option{
					Description: "Look out the window to get your bearings.",
					Route:       28,
				},
				Option{
					Description: "Exit by the north doors.",
					Route:       29,
				},
				Option{
					Description: "Move to the east.",
					Route:       21,
				},
				Option{
					Description: "Go to the west.",
					Route:       29,
				},
			},
		},
		7: Chapter{
			Description: "You are at the entrance of a forbidding-looking stone castle. You are facing east. The huge wooden entrance door stands lightly open",
			Options: []Option{
				Option{
					Description: "Enter the castle",
					Route:       40,
				},
			},
		},
		29: Chapter{
			Description: "You are in the castle's Audience Chamber. The faded tapestries on the wall only hint at the splendor which this room once had. There is a window to the west. By craning your neck through it to the right you can see the castle entrance.",
			Action: &RandomAction{
				Randomizer: Coins(2),
				Results: []RandomActionResult{
					RandomActionResult{
						Results: []Results{
							Results{"heads", "heads"},
						},
						Actions: []Action{
							&Change{
								Description: "You find diamonds worth $169",
								Stat:        "WEALTH",
								Value:       169,
								Operator:    Add,
							},
						},
					},
					RandomActionResult{
						Results: []Results{
							Results{"tails", "tails"},
						},
						Actions: []Action{
							&RandomAction{
								Description: "You must fight the fanatical Fleshgorger which has suddenly stumbled into the room.",
								Randomizer:  Coins(2),
								Results: []RandomActionResult{
									RandomActionResult{
										Description: "You have defeated the Fleshgorger!",
										Results: []Results{
											Results{"heads", "tails"},
											Results{"tails", "heads"},
										},
										Actions: []Action{
											&Change{
												Stat:     "MONSTER TALLY",
												Value:    1,
												Operator: Add,
											},
											&Change{
												Stat:     "STRENGTH",
												Value:    2,
												Operator: Mult,
											},
										},
									},
									RandomActionResult{
										Description: "You have been defeated by the Fleshgorger!",
										Results: []Results{
											Results{"tails", "tails"},
											Results{"heads", "heads"},
										},
										Actions: []Action{
											&Change{
												Stat:     "STRENGTH",
												Value:    2,
												Operator: Div,
											},
										},
									},
								},
							},
						},
					},
				},
			},
			Options: []Option{
				Option{
					Description: "Leave by the north",
					Route:       40,
				},
				Option{
					Description: "Leave by the south or the east doors",
					Route:       6,
				},
			},
		},
		40: Chapter{
			Description: "You are in the hallway entrance to the castle. It is dark and gloomy, and the air of decay and desolation is very depressing. You suddenly feel very frightened",
			Options: []Option{
				Option{
					Description: "Run away from the castle",
					Route:       7,
				},
				Option{
					Description: "Proceed through the south door",
					Route:       29,
				},
			},
		},
	},
}
