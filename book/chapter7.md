7
TREASURE AND TERROR
Now that we have created a section of program to handle the physical environment of the castle in a form which we can manipulate, we need to fill some of the rooms, so the player will have treasure to find and monsters to battle.

Distributing the Treasure
This is easy to do. Let's look at adding treasure for a start. Here is the section of code required:

2830 REM **************
2840 REM ALLOT TREASURE
2850 FOR J = 1 TO 4
2860 M=INT(RND(1)*19)+1
2870 IF M=6 OR M=11 OR A(M,7)<>0 THEN 2860
2880 A(M,7)=INT(RND(1)*100)+10
2890 NEXT J
Lines 2860 and 2870 choose a room number between 1 and 19, check that it is not 6 (the entrance, outside the castle) or 11 (the exit, outside), and then check that this room is empty. You will recall we mentioned in the last chapter that the seventh element for each room was left blank to allow for contents. Now we are filling it. If this element is zero, the room is currently empty.

Once a room has been chosen with line 2860, line 2880 puts an amount of money in it between $10 and $109.

Distributing the Monsters
With the treasure in place, it is time to allot our terrors. The next routine does this, choosing a room in the same way as our money routine did, then setting that element equal to a negative number (-1 to -4):

2900 REM **************
2910 REM ALLOT MONSTERS
2920 FOR J = 1 TO 4
2930 M=INT(RND(1)*18)+1
2940 IF M=6 OR M=11 OR A(M,7)<>0 THEN 2930
2950 A(M,7)= -J
2960 NEXT J
You can see now that if the computer looks at the seventh element of a room, it can tell if it is empty (equals 0), contains treasure (greater than 9) or a monster (less than 1) and can give the player a message to this effect. Extending the use of the room array in this way points up again how vital the array is to the construction and control of an Adventure environment.

Finally, we put treasure in two rooms (the Private Meeting Room and the Treasury) regardless of whether the above routines have already put anything there. These lines wipe over anything else which has been placed there:

2970 A(4,7)=100+INT(RND(1)*100)
2980 A(16,7)=100+INT(RND(1)*100)
2990 RETURN
The RETURN line, of course, is simply used to terminate the initialization subroutine.

Adding Variables
A number of variables are needed to add to the creation of a realistic Adventure environment. I make it a practice to use explicit variable names which will remind me (or tell me straight out) what the variable stands for. Many computer systems only recognize the first two letters of a variable name, so I have made sure in this program (and the others in the book) that the same first two letters are not used for different variables within a single program. You can save time and memory by just entering the first two letters of a variable's name. You should still find the first two letters remind you what the variable name represents.

The first line in our next section clears the screen, before the following variables are initialized: STRENGTH, WEALTH, FOOD, TALLY, MK. If STRENGTH ever gets to zero, the game is over. WEALTH can be used to buy FOOD which can be eaten to restore STRENGTH. TALLY is a simple count of how long you have survived within the environment (one is added to TALLY each time you move) and MK is the count of monsters killed. These lines initialize the variables we've just discussed:

2610 CLS
2620 STRENGTH=100
2630 WEALTH=75
2640 FOOD=0
2650 TALLY=0
2660 MK=0:REM NO. OF MONSTERS KILLED
The Adventure is heightened if the computer uses the player's name from time to time, so line 2750 asks for the name, and allots N$ (for "Name string") to this. Line 2760 clears the screen of the question and answer, ready for the game itself to get underway.

The game begins outside the castle, in what we've designated "room 6," so the variable RO (for "ROom") is set equal to 6 in line 2770.

There are a number of things the player can manipulate or wear during the game, and the variable names to show possession of these are set to zero in lines 2780 to 2820. If any of these is changed to equal one, the player is carrying or wearing the object. This makes the purchase of, say, an axe very easy. If the variable AXE equals zero, the player does not have an axe. If AXE becomes equal to one, the computer can print out "YOU ARE CARRYING AN AXE." If the player drops the axe in a battle (as can happen in our second, more elaborate, version of WEREWOLVES AND WANDERER), this can be indicated simply by resetting the variable AXE to equal zero.

Here's the code to get the player's name, set the initial room value, and assign a value of zero to a number of variables:

2750 INPUT "WHAT IS YOUR NAME, EXPLORER";N$
2760 CLS
2770 RO=6:REM STARTING POSITION (RO=ROOM NUMBER)
2780 SWORD=0
2790 AMULET=0
2800 AXE=0
2810 SUIT=0
2820 LIGHT=0
From this chapter, you have learned how to place objects and entities within the Adventure program. As you can see, these add not only to the "reality" of the Adventure environment, but form part of the reasons why the Adventure is being undertaken. Who would risk multiple death, if precious gems were not the reward?
