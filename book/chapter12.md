12 PEEKING INTO THE ROOMS
=====
Room Descriptions
----
Room descriptions add a third dimension to the raw plans of your Adventure environment. You can add as many details as you like to describing each room or cavern your Adventurer discovers, or you can keep the descriptions short, and leave it up to the player's imagination to fill in the gaps.

I prefer to steer a middle course, adding some description, but not too much, to trigger the player's imagination but not to stifle it.

This section of the program is pretty simple to understand. The long ON GOSUB line (1010) sends the computer to the relevant room, printing up a brief description of the room, and pointing out where the windows and doors are.

You should now refer back to the maps of the various floors, and see how they relate to the descriptions given below:
```
990 REM ROOM DESCRIPTIONS
1000 PRINT:PRINT
"******************************":PRINT:PRINT
1010 ON RO GOSUB
1040,1100,1170,1230,1280,1360,1410,1470,1540,
1620,1700, 1730,1790,1860,1960,2030,2100,2160,2230
1020 RETURN
1030 REM ************
1040 REM ROOM 1
1050 PRINT "YOU ARE IN THE HALLWAY"
1060 PRINT "THERE IS A DOOR TO THE
SOUTH"
1070 PRINT "THROUGH WINDOWS TO THE NORTH YOU
CAN SEE A SECRET HERB GARDEN"
1080 RETURN
1090 REM ************
1100 REM ROOM 2
1110 PRINT "THIS IS THE AUDIENCE
CHAMBER"
1120 PRINT "THERE IS A WINDOW TO THE WEST. BY
LOOKING TO THE RIGHT"
1130 PRINT "THROUGH IT YOU CAN SEE THE
ENTRANCE TO THE CASTLE."
1140 PRINT "DOORS LEAVE THIS ROOM TO THE
NORTH, EAST AND SOUTH"
1150 RETURN
1160 REM ************
1170 REM ROOM 3
1180 PRINT "YOU ARE IN THE GREAT HALL, AN
L-SHAPED ROOM"
1190 PRINT "THERE ARE DOORS TO THE EAST AND
TO THE NORTH"
1200 PRINT "IN THE ALCOVE IS A DOOR TO THE
WEST"
1210 RETURN
1220 REM ************
1230 REM ROOM 4
1240 PRINT "THIS IS THE MONARCH'S PRIVATE
MEETING ROOM"
1250 PRINT "THERE IS A SINGLE EXIT TO THE
SOUTH"
1260 RETURN
1270 REM ************
1280 REM ROOM 5
1290 PRINT "THIS INNER HALLWAY CONTAINS A
DOOR TO THE NORTH,"
1300 PRINT "AND ONE TO THE WEST, AND A
CIRCULAR STAIRWELL"
1310 PRINT "PASSES THROUGH THE ROOM"
1320 PRINT "YOU CAN SEE AN ORNAMENTAL LAKE
THROUGH THE"
1330 PRINT "WINDOWS TO THE SOUTH"
1340 RETURN
1350 REM ************
1360 REM ROOM 6
1370 PRINT "YOU ARE AT THE ENTRANCE TO A
FORBIDDING-LOOKING"
1380 PRINT "STONE CASTLE. YOU ARE FACING
EAST"
1390 RETURN
1400 REM ************
1410 REM ROOM 7
1420 PRINT "THIS IS THE CASTLE'S KITCHEN.
THROUGH WINDOWS IN"
1430 PRINT "THE NORTH WALL YOU CAN SEE A
SECRET HERB GARDEN."
1440 PRINT "A DOOR LEAVES THE KITCHEN TO THE
SOUTH"
1450 RETURN
1460 REM ************
1470 REM ROOM 8
1480 PRINT "YOU ARE IN THE STORE ROOM, AMIDST
SPICES,"
1490 PRINT "VEGETABLES, AND VAST SACKS OF
FLOUR AND"
1500 PRINT "OTHER PROVISIONS. THERE IS A DOOR
TO THE NORTH"
1510 PRINT "AND ONE TO THE SOUTH"
1520 RETURN
1610 REM ************************
1620 REM ROOM 10
1630 PRINT "YOU ARE IN THE REAR
VESTIBULE"
1640 PRINT "THERE ARE WINDOWS TO THE SOUTH
FROM WHICH"
1650 PRINT "YOU CAN SEE THE ORNAMENTAL
LAKE"
1660 PRINT "THERE IS AN EXIT TO THE EAST,
AND"
1670 PRINT "ONE TO THE NORTH"
1680 RETURN
1720 REM ****************
1730 REM ROOM 12
1740 PRINT "YOU ARE IN THE DANK, DARK
DUNGEON"
1750 PRINT "THERE IS A SINGLE EXIT, A SMALL
HOLE IN"
1760 PRINT "WALL TOWARDS THE WEST"
1770 RETURN
1780 REM ****************
1790 REM ROOM 13
1800 PRINT "YOU ARE IN THE PRISON GUARDROOM,
IN THE"
1810 PRINT "BASEMENT OF THE CASTLE. THE
STAIRWELL"
1820 PRINT "ENDS IN THIS ROOM. THERE IS ONE
OTHER"
1830 PRINT "EXIT, A SMALL HOLE IN THE EAST
WALL"
1840 RETURN
1850 REM ****************
1860 REM ROOM 14
1870 PRINT "YOU ARE IN THE MASTER BEDROOM ON
THE UPPER"
1880 PRINT "LEVEL OF THE CASTLE...."
1890 PRINT "LOOKING DOWN FROM THE WINDOW TO
THE WEST YOU"
1900 PRINT "CAN SEE THE ENTRANCE TO THE
CASTLE, WHILE THE"
1910 PRINT "SECRET HERB GARDEN IS VISIBLE
BELOW THE NORTH"
1920 PRINT "WINDOW. THERE ARE DOORS TO THE
EAST AND"
1930 PRINT "TO THE SOUTH...."
1940 RETURN
1950 REM ***************
1960 REM ROOM 15
1970 PRINT "THIS IS THE L-SHAPED UPPER
HALLWAY."
1980 PRINT "TO THE NORTH IS A DOOR, AND THERE
IS A"
1990 PRINT "STAIRWELL IN THE HALL AS WELL.
YOU CAN SEE"
2000 PRINT "THE LAKE THROUGH THE SOUTH
WINDOWS"
2010 RETURN
2020 REM ****************
2030 REM ROOM 16
2040 PRINT "THIS ROOM WAS USED AS THE CASTLE
TREASURY IN"
2050 PRINT "BY-GONE YEARS...."
2060 PRINT "THERE ARE NO WINDOWS, JUST EXITS
TO THE"
2070 PRINT "NORTH AND TO THE EAST"
2080 RETURN
2090 REM **************
2100 REM ROOM 17
2110 PRINT "OOOOH...YOU ARE IN THE
CHAMBERMAIDS' BEDROOM"
2120 PRINT "THERE IS AN EXIT TO THE WEST AND
A DOOR"
2130 PRINT "TO THE SOUTH...."
2140 RETURN
2150 REM **************
2160 REM ROOM 18
2170 PRINT "THIS TINY ROOM ON THE UPPER LEVEL
IS THE"
2180 PRINT "DRESSING CHAMBER. THERE IS A
WINDOW TO THE"
2190 PRINT "NORTH, WITH A VIEW OF THE HERE
GARDEN DOWN"
2200 PRINT "BELOW. A DOOR LEAVES TO THE
SOUTH"
2210 RETURN
2220 REM *************
2230 REM ROOM 19
2240 PRINT "THIS IS THE SMALL ROOM OUTSIDE
THE CASTLE"
2250 PRINT "LIFT WHICH CAN BE ENTERED BY A
DOOR TO THE NORTH"
2260 PRINT "ANOTHER DOOR LEADS TO THE WEST.
YOU CAN SEE"
2270 PRINT "THE LAKE THROUGH THE SOUTHERN
WINDOWS"
2280 RETURN
```
All these subroutines simply print out a description, then return to line 1020 which returns action to the main program.

Special Handling
--------
Two rooms are different. Room 9 is, as you can see from the map, the elevator, and this needs special handling. The delay loop is called twice, and then RO is set to 10 (line 1590), the room you end up in after using the elevator, before the program returns to the main handler.
```
1530 REM **************
1540 REM ROOM 9
1550 PRINT "YOU HAVE ENTERED THE LIFT..."
1560 GOSUB 3520
1570 PRINT "IT SLOWLY DESCENDS..."
1580 GOSUB 3520
1590 RO=10
1600 GOTO 1000
```
The other room needing special handling is room 11, the final exit. When the computer comes to the relevant subroutine, it immediately hits a return. It is put here, within the other room subroutines, just to keep the program clear.
```
1690 REM *****************
1700 REM ROOM 11
1710 RETURN
```
Once you've made it to room 11, the program stops cycling, and then announces your success:
```
50 PRINT:PRINT "YOU'VE DONE IT!!":GOSUB
3520:PRINT "THAT WAS THE EXIT FROM THE CASTLE"
60 GOSUB 3520
70 PRINT:PRINT "YOU HAVE SUCCEEDED,
";N$;"!"
80 PRINT:PRINT "YOU MANAGED TO GET OUT OF THE
CASTLE"
90 GOSUB 3520
100 PRINT:PRINT "WELL DONE!"
110 GOSUB 3520
```
From here it simply follows on to lines 120 and 130, which we discussed earlier, to print up your score, and end the game.

The examples given in this chapter should help you develop a number of ideas of your own to incorporate into room descriptions and "special events." CHATEAU GAILLARD, the final program in the book, includes some quite difficult "special handling" situations. Although these situations are a little more complex than those outlined in this chapter, they use the ideas we have discussed here. Therefore, it's probably a good idea to review and make sure you understand the material in this chapter before continuing.
