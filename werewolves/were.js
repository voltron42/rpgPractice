var were = {
  init:{
    enter:6,
    exit:11,
    stats:{
      strength:100,
      wealth:75,
      food:0,
      tally:0
    },
    treasure:{
      count:4,
      treasure:"new Dice(1,100,10)"
    }
  },
  store:[{
    name:"FLAMING TOUCH",
    cost:15,
    limit:1,
    effect:"self.hasLight = true"
  },{
    name:"AXE",
    cost:10,
    limit:1,
    effect:"target.danger *= (4/5)",
    mustEquip:true,
    combatOnly:true
  },{
    name:"SWORD",
    cost:20,
    limit:1,
    effect:"target.danger *= (3/4)",
    mustEquip:true,
    combatOnly:true
  },{
    name:"FOOD",
    cost:2,
    effect:"self.strength += 5",
  },{
    name:"MAGIC AMULET",
    cost:30,
    limit:1,
  },{
    name:"SUIT OF ARMOR",
    cost:10,
    limit:1,
    effect:"target.danger *= (4/5)",
    combatOnly:true
  }],
  monsters:{
    "-1":{
      name:"FEROCIOUS WEREWOLF",
      danger:5
    },
    "-2":{
      name:"FANATICAL FLESHGORGER",
      danger:10
    },
    "-3":{
      name:"MALOVENTY MALDEMER",
      danger:15
    },
    "-4":{
      name:"DEVASTATING ICE-DRAGON",
      danger:20
    },
  },
  rooms:{
    "1":{
      doors:{
        south:2
      },
      description:[
        "YOU ARE IN THE HALLWAY",
        "THERE IS A DOOR TO THE SOUTH",
        "THROUGH WINDOWS TO THE NORTH YOU CAN SEE A SECRET HERB GARDEN"
      ]
    },
    "2":{
      doors:{
        north:1,
        south:3,
        east:3
      },
      description:[
        "THIS IS THE AUDIENCE CHAMBER",
        "THERE IS A WINDOW TO THE WEST. BY LOOKING TO THE RIGHT",
        "THROUGH IT YOU CAN SEE THE ENTRANCE TO THE CASTLE.",
        "DOORS LEAVE THIS ROOM TO THE NORTH, EAST AND SOUTH"
      ]
    },
    "3":{
      doors:{
        north:2,
        east:5,
        west:2
      },
      description:[
        "YOU ARE IN THE GREAT HALL, AN L-SHAPED ROOM",
        "THERE ARE DOORS TO THE EAST AND TO THE NORTH",
        "IN THE ALCOVE IS A DOOR TO THE WEST"
      ]
    },
    "4":{
      treasure:"new Dice(1,100,100)",
      doors:{
        south:5
      },
      description:[
        "THIS IS THE MONARCH'S PRIVATE MEETING ROOM",
        "THERE IS A SINGLE EXIT TO THE SOUTH"
      ]
    },
    "5":{
      doors:{
        north:4,
        west:3,
        up:15,
        down:13
      },
      description:[
        "THIS INNER HALLWAY CONTAINS A DOOR TO THE NORTH,",
        "AND ONE TO THE WEST, AND A CIRCULAR STAIRWELL",
        "PASSES THROUGH THE ROOM",
        "YOU CAN SEE AN ORNAMENTAL LAKE THROUGH THE",
        "WINDOWS TO THE SOUTH"
      ]
    },
    "6":{
      doors:{
        east:1
      },
      description:[
        "YOU ARE AT THE ENTRANCE TO A FORBIDDING-LOOKING",
        "STONE CASTLE. YOU ARE FACING EAST"
      ]
    },
    "7":{
      doors:{
        south:8
      },
      description:[
        "THIS IS THE CASTLE'S KITCHEN THROUGH WINDOWS IN",
        "THE NORTH WALL YOU CAN SEE A SECRET HERB GARDEN.",
        "A DOOR LEAVES THE KITCHEN TO THE SOUTH"
      ]
    },
    "8":{
      doors:{
        north:7,
        south:10
      },
      description:[
        "YOU ARE IN THE STORE ROOM, AMIDST SPICES,",
        "VEGETABLES, AND VAST SACKS OF FLOUR AND",
        "OTHER PROVISIONS. THERE IS A DOOR TO THE NORTH",
        "AND ONE TO THE SOUTH"
      ]
    },
    "9":{
      doors:{
        south:19,
        west:8,
        down:8
      },
      description:[
        "YOU HAVE ENTERED THE LIFT...",
        "IT SLOWLY DESCENDS..."
      ]
    },
    "10":{
      doors:{
        north:8,
        east:11
      },
      description:[
        "YOU ARE IN THE REAR VESTIBULE",
        "THERE ARE WINDOWS TO THE SOUTH FROM WHICH YOU",
        "YOU CAN SEE THE ORNAMENTAL LAKE",
        "THERE IS AN EXIT TO THE EAST AND",
        "ONE TO THE NORTH"
      ]
    },
    "11":{
      doors:{
        east:10
      },
      description:[
        "YOU'VE DONE IT!!",
        "THAT WAS THE EXIT FROM THE CASTLE",
        "YOU HAVE SUCCEEDED, ${name}!",
        "YOU MANAGED TO GET OUT OF THE CASTLE",
        "WELL DONE!"
      ]
    },
    "12":{
      doors:{
        west:13
      },
      description:[
        "YOU ARE IN THE DANK, DARK DUNGEON",
        "THERE IS A SINGLE EXIT, A SMALL HOLE IN",
        "WALL TOWARDS THE WEST"
      ]
    },
    "13":{
      doors:{
        east:12,
        up:5
      },
      description:[
        "YOU ARE IN THE PRISON GUARDROOM, IN THE",
        "BASEMENT OF THE CASTLE. THE STAIRWELL",
        "ENDS IN THIS ROOM. THERE IS ONE OTHER",
        "EXIT, A SMALL HOLE IN THE EAST WALL"
      ]
    },
    "14":{
      treasure:"new Dice(1,100,100)",
      doors:{
        south:15,
        east:17
      },
      description:[
        "YOU ARE IN THE MASTER BEDROOM ON THE UPPER",
        "LEVEL OF THE CASTLE....",
        "LOOKING DOWN FROM THE WINDOW TO THE WEST YOU",
        "CAN SEE THE ENTRANCE TO THE CASTLE, WHILE THE",
        "SECRET HERB GARDEN IS VISIBLE BELOW THE NORTH",
        "WINDOW. THERE ARE DOORS TO THE EAST AND",
        "TO THE SOUTH...."
      ]
    },
    "15":{
      doors:{
        north:14,
        down:5
      },
      description:[
        "THIS IS THE L-SHAPPED UPPER HALLWAY.",
        "TO THE NORTH IS A DOOR, AND THERE IS A",
        "STAIRWELL IN THE HALL AS WELL. YOU CAN SEE",
        "THE LAKE THROUGH THE SOUTH WINDOWS"
      ]
    },
    "16":{
      doors:{
        north:17,
        east:19
      },
      description:[
        "THIS ROOM WAS USED AS THE CASTLE TREASURY IN",
        "BY-GONE YEARS....",
        "THERE ARE NO WINDOWS, JUST EXITS TO THE",
        "NORTH AND TO THE EAST"
      ]
    },
    "17":{
      doors:{
        north:18,
        south:16,
        west:14
      },
      description:[
        "OOOOH...YOU ARE IN THE CHAMBERMAIDS' BEDROOM",
        "THERE IS AN EXIT TO THE WEST AND A DOOR",
        "TO THE SOUTH...."
      ]
    },
    "18":{
      doors:{
        south:17
      },
      description:[
        "THIS TINY ROOM ON THE UPPER LEVEL IS THE",
        "DRESSING CHAMBER. THERE IS A WINDOW TO THE",
        "NORTH, WITH A VIEW OF THE HERB GARDEN DOWN",
        "BELOW. A DOOR LEAVES TO THE SOUTH"
      ]
    },
    "19":{
      doors:{
        north:9,
        east:16
      },
      description:[
        "THIS IS THE SMALL ROOM OUTSIDE THE CASTLE",
        "LIFT WHICH CAN BE ENTERED BY A DOOR TO THE NORTH",
        "ANOTHER DOOR LEADS TO THE WEST. YOU CAN SEE",
        "THE LAKE THROUGH THE SOUTHERN WINDOWS"
      ]
    }
  }
}
