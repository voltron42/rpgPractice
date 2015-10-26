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
			Actions: []Action{
				RandomAction{
					Randomizer: Coins(2),
					Results: []RandomActionResult{
						RandomActionResult{
							Results: []Results{
								Results{"heads", "heads"},
							},
							Actions: []Action{
								Change{
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
								Change{
									Stat:     "LOCATION",
									Value:    5,
									Operator: Set,
								},
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
			Actions: []Action{
				RandomAction{
					Randomizer: Coins(2),
					Results: []RandomActionResult{
						RandomActionResult{
							Results: []Results{
								Results{"tails", "tails"},
							},
							Actions: []Action{
								Change{
									Stat:     "LOCATION",
									Value:    13,
									Operator: Set,
								},
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
		3: Chapter{
			Description: "Looking down the stairwell, you can see a gloomy, unpleasant looking room, the guardroom of the ancient dungeon.",
			Options: []Option{
				Option{
					Route: 21,
				},
			},
		},
		4: Chapter{
			Description: "Inside the sack you find jewels worth $500!",
			Actions: []Action{
				Change{
					Stat:     "WEALTH",
					Value:    500,
					Operator: Add,
				},
			},
			Options: []Option{
				Option{
					Route: 9,
				},
			},
		},
		5: Chapter{
			Description: "The ghost of the guard has awoken!",
			Actions: []Action{
				Change{
					Description: "Your strength is halved du to the fright you suffered.",
					Stat:        "STRENGTH",
					Value:       2,
					Operator:    Div,
				},
			},
			Options: []Option{
				Option{
					Route: 1,
				},
			},
		},
		6: Chapter{
			Description: "You are in the Great Hall of the castle. There are two doors in the L-shaped room. You notice the wood panels around the room are warped and faded. As you look around, a mouse scampers across the floor. You whirl around at a sudden noise.",
			Actions: []Action{
				RandomAction{
					Randomizer: Coins(1),
					Results: []RandomActionResult{
						RandomActionResult{
							Results: []Results{
								Results{"heads"},
							},
							Actions: []Action{
								Change{
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
		8: Chapter{
			Description: "A werewolf awakes in this room. He attacks you.",
			Actions: []Action{
				RandomAction{
					Randomizer: Coins(1),
					Results: []RandomActionResult{
						RandomActionResult{
							Results: []Results{
								Results{"heads"},
							},
							Actions: []Action{
								Change{
									Description: "The werewolf has beaten you.",
									Stat:        "LOCATION",
									Value:       37,
									Operator:    Set,
								},
							},
						},
						RandomActionResult{
							Description: "You have killed the werewolf.",
							Results: []Results{
								Results{"tails"},
							},
							Actions: []Action{
								Change{
									Stat:     "MONSTER TALLY",
									Value:    1,
									Operator: Add,
								},
								Change{
									Stat:     "LOCATION",
									Value:    35,
									Operator: Set,
								},
							},
						},
					},
				},
			},
		},
		9: Chapter{
			Description: "You are in the storeroom, amidst spices, vegetables, and vast sacks of flour and other provisions. The air is thick with spice and curry fumes.",
			Options: []Option{
				Option{
					Description: "Look under some of the sacks",
					Route:       31,
				},
				Option{
					Description: "Look inside of the top sack",
					Route:       4,
				},
				Option{
					Description: "Leave by the south door",
					Route:       42,
				},
				Option{
					Description: "Leave by the north door",
					Route:       35,
				},
			},
		},
		10: Chapter{
			Description: "Looking up the stairwell you can just make out an elaborately decorated hallway.",
			Options: []Option{
				Option{
					Route: 21,
				},
			},
		},
		11: Chapter{
			Description: "There are rats in this dungeon. They steal gold pieces worth $10 from you.",
			Actions: []Action{
				Change{
					Stat:     "WEALTH",
					Value:    10,
					Operator: Sub,
				},
			},
			Options: []Option{
				Option{
					Route: 1,
				},
			},
		},
		12: Chapter{
			Description: "You are descending very, very slowly. Your strength is sapped by a magic spell left in the elevator.",
			Actions: []Action{
				Change{
					Description: "Your strength has been cut in half",
					Stat:        "STRENGTH",
					Value:       2,
					Operator:    Div,
				},
			},
			Options: []Option{
				Option{
					Route: 42,
				},
			},
		},
		13: Chapter{
			Description: "A malevolent Maldemer attacks you. You can smell the sulfur on his breath. ",
			Actions: []Action{
				Change{
					Description: "Your strength has been diminished by 10.",
					Stat:        "STRENGTH",
					Value:       10,
					Operator:    Sub,
				},
				RandomAction{
					Randomizer: Coins(2),
					Results: []RandomActionResult{
						RandomActionResult{
							Description: "You have killed the Maldemer",
							Results: []Results{
								Results{"heads", "heads"},
							},
							Actions: []Action{
								Change{
									Stat:     "MONSTER TALLY",
									Value:    1,
									Operator: Add,
								},
							},
						},
					},
				},
			},
			Options: []Option{
				Option{
					Route: 42,
				},
			},
		},
		14: Chapter{
			Description: "You've done it! That was the exit from the castle.",
			Actions: []Action{
				Change{
					Description: "Your strength has been doubled",
					Stat:        "STRENGTH",
					Value:       2,
					Operator:    Mult,
				},
			},
			Options: []Option{
				Option{
					Route: 27,
				},
			},
		},
		15: Chapter{
			Description: "You are in the castle's ancient, hydraulic elevator.",
			Options: []Option{
				Option{
					Description: "Descend Slowly",
					Route:       12,
				},
				Option{
					Description: "Get down as quickly as possible",
					Route:       24,
				},
			},
		},
		16: Chapter{
			Description: "Horrors. There is a devastating Ice-Dragon here. It leaps toward you. Blood drips from his claws.",
			Actions: []Action{
				RandomAction{
					Randomizer: Coins(1),
					Results: []RandomActionResult{
						RandomActionResult{
							Description: "You have killed the Ice-Dragon",
							Results: []Results{
								Results{"heads"},
							},
							Actions: []Action{
								Change{
									Stat:     "STRENGTH",
									Value:    10,
									Operator: Sub,
								},
								Change{
									Stat:     "MONSTER TALLY",
									Value:    1,
									Operator: Add,
								},
							},
						},
						RandomActionResult{
							Description: "You have escaped the Ice-Dragon",
							Results: []Results{
								Results{"tails"},
							},
							Actions: []Action{
								Change{
									Stat:     "STRENGTH",
									Value:    20,
									Operator: Sub,
								},
							},
						},
					},
				},
			},
			Options: []Option{
				Option{
					Route: 30,
				},
			},
		},
		17: Chapter{
			Description: "This is the monarch's private meeting room. The echo of ancient plotting and wrangling hangs heavy in the musty air.",
			Actions: []Action{
				RandomAction{
					Randomizer: Coins(1),
					Results: []RandomActionResult{
						RandomActionResult{
							Description: "You have found an emerald worth $100!",
							Results: []Results{
								Results{"heads"},
							},
							Actions: []Action{
								Change{
									Stat:     "WEALTH",
									Value:    100,
									Operator: Add,
								},
							},
						},
						RandomActionResult{
							Description: "You are attacked by a ghastly Gruesomeness which was hiding behind the drapes.",
							Results: []Results{
								Results{"tails"},
							},
							Actions: []Action{
								RandomAction{
									Randomizer: Coins(1),
									Results: []RandomActionResult{
										RandomActionResult{
											Description: "You have defeated the ghastly Gruesomeness!",
											Results: []Results{
												Results{"tails"},
											},
											Actions: []Action{
												Change{
													Stat:     "MONSTER TALLY",
													Value:    1,
													Operator: Add,
												},
											},
										},
										RandomActionResult{
											Description: "The Gruesomeness wins.",
											Results: []Results{
												Results{"tails"},
											},
											Actions: []Action{
												Change{
													Description: "While you are lying exhausted on the floor, he steals $100 from your wealth.",
													Stat:        "WEALTH",
													Value:       100,
													Operator:    Sub,
												},
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
					Description: "You exit through the south door.",
					Route:       21,
				},
			},
		},
		18: Chapter{
			Description: "This tiny room on the upper level is the dressing chamber. There is a window to the north. There is a door which 1eaves the room to the south.",
			Options: []Option{
				Option{
					Description: "Look out the window.",
					Route:       22,
				},
				Option{
					Description: "Use the door.",
					Route:       32,
				},
			},
		},
		19: Chapter{
			Description: "The noise is frightening. What on earth (or beyond it) is inside that room?",
			Options: []Option{
				Option{
					Route: 23,
				},
			},
		},
		20: Chapter{
			Description: "Aha... wealth! You find great treasure here worth $900 in gems and gold.",
			Actions: []Action{
				Change{
					Stat:     "WEALTH",
					Value:    900,
					Operator: Add,
				},
			},
			Options: []Option{
				Option{
					Route: 30,
				},
			},
		},
		29: Chapter{
			Description: "You are in the castle's Audience Chamber. The faded tapestries on the wall only hint at the splendor which this room once had. There is a window to the west. By craning your neck through it to the right you can see the castle entrance.",
			Actions: []Action{
				RandomAction{
					Randomizer: Coins(2),
					Results: []RandomActionResult{
						RandomActionResult{
							Results: []Results{
								Results{"heads", "heads"},
							},
							Actions: []Action{
								Change{
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
								RandomAction{
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
												Change{
													Stat:     "MONSTER TALLY",
													Value:    1,
													Operator: Add,
												},
												Change{
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
												Change{
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
		37: Chapter{
			Options: []Option{
				Option{
					Description: "You are dead!!",
					Route:       27,
				},
			},
		},
		38: Chapter{
			Options: []Option{
				Option{
					Route: 14,
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
