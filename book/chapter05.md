5 CREATING THE STRUCTURE
=======

We'll move now from considering the simple, five-room environment, to the much more complex (19 rooms in all) castle environment you will inhabit when taking the part of the Wanderer in WEREWOLVES AND WANDERER. Let's begin with an explanation of how such a complicated program is designed.

The Master Loop
-------

This Adventure program is structured according to a well-defined outline, which was drawn up long before the castle floor plan was created, or any of the incidents which would occur during the playing of the game were considered. Working to an outline like this, in which the actions the program must take are determined before any attention is given to the actual form or coding of the program, is often called "structured programming." This is discussed in some detail in Chapter 23, but it is appropriate to introduce it briefly at this point. The original WEREWOLVES AND WANDERER outline looked like this, before any program was actually written:

```
MAIN HANDLING ROUTINE
   INFORM PLAYER OF ROOM CONTENTS AND OWN
     STATUS
   PICK UP TREASURE
   FIGHT MONSTER
   EAT FOOD
   BUY PROVISIONS

IF PLAYER STILL ALIVE AND NOT EXIT, GO TO MAIN
HANDLING ROUTINE AGAIN
CONGRATULATE OR OTHERWISE

END
The program consisted only of this bare outline at the beginning. Finally, the program ended up as follows. You can see that the bare outline has strongly controlled the final structure of the program:

IDENTIFY (line 10)

GOTO INITIALIZATION ROUTINE (20)

GOSUB MAJOR HANDLING ROUTINE (30)

IF PLAYER HAS NOT BEEN KILLED OR REACHED THE
EXIT, GO BACK TO THE LINE ABOVE WHICH SENDS AC-
TION TO THE MAJOR HANDLING ROUTINE (40)

CONGRATULATIONS MESSAGE (70-140)

MAJOR HANDLING ROUTINE (160-320)

IF PLAYER HAS LIGHT, DESCRIBE ROOM (330)

CHECK FOR MONSTERS/TREASURE, DESCRIBE (360-440)

ASK FOR PLAYER'S MOVE (450-710)

FIGHT SUBROUTINE (720-970)

ROOM DESCRIPTION SUBROUTINES (990-2280)

DEATH MESSAGE (2300-2330)

PICK UP TREASURE ROUTINE (2350-2400) TELL COWARD
PLAYER HE OR SHE MUST FIGHT (2420-2460)

EAT FOOD, INCREASE STRENGTH (2480-2580)

INITIALIZE (2600-2990)
   ASSIGN VARIABLES
   FILL FLOOR PLAN ARRAY
   GET PLAYER'S NAME
   ALLOT TREASURE TO ROOMS
   ALLOT MONSTERS TO ROOMS

INVENTORY/PROVISIONS SUBROUTINE (3010-3290)

DATA STATEMENTS FOR FLOOR PLAN (3310-3490)

DELAY LOOP (3520-3530)
```

Now that you've seen the overall structure, you should be able to appreciate that writing at least an initial outline helps give form to a program which could otherwise easily get out of control.

The program sits within a "master loop" which calls all the needed subroutines, then checks to see if the game is over (because the player has reached the exit, or is dead). If this check is negative (that is, the player is not dead, and the final exit has not been found), the program loops back to go through it all again. This cycle continues until one of the "end condition" tests proves positive.

Modular Construction
The program was written in a series of discrete modules, a process I strongly suggest you follow. It will help you keep a long and complex program under control, when a less disciplined approach would make the task almost impossible. A program which is constructed in modules is also much easier to modify (as you'll see when we look at the more elaborate form of WEREWOLVES AND WANDERER) and debug. To make it easier to keep track of the separate modules, they are divided by a REM statement full of asterisks, so the separate modules are immediately visible when you look at the listing as a whole.

The program begins, then, with these four lines:

```
10 REM WEREWOLVES AND WANDERER
20 GOSUB 2600:REM INITIALISE
30 GOSUB 160
40 IF RO <> 11 THEN 30
```

If RO equals 11, the game is over, as room eleven is the final exit from the castle. As you can see, line 20 calls the subroutine which starts at line 2600, the initialization subroutine. Line 30 calls the Major Handling Routine, and if the check in line 40 proves negative, goes back to 30 to call this routine again.
