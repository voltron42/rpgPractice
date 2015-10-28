9
INTERPRETING YOUR COMMANDS
A Limited Vocabulary
WEREWOLVES AND WANDERER recognizes a strictly limited vocabulary. While this diminishes the realism of the Adventure to some extent (if such an arcane environment and such possibilities of interaction could ever be said to be "realistic"), it simplifies the construction of the program, and ensures that it runs as quickly as possible.

The program accepts your commands in lines 450 to 470, prompting you with WHAT DO YOU WANT TO DO?

You can enter the full word (such as NORTH) or just the first letter. As you can see, the last part of line 460 cuts the input down to a single letter anyway, so you may as well just enter the first letter of your command.

450 PRINT:PRINT:PRINT "WHAT DO YOU WANT TO DO";
460 INPUT A$:A$=LEFT$(A$,1)
470 IF K<0 AND A$<>"F" AND A$<>"R" THEN 460
Here is the vocabulary the program recognizes:

N-NORTH S-SOUTH
E-EAST W-WEST
U-UP D-DOWN
F-FIGHT R-RUN
M-MAGIC (AMULET, used to trigger the amulet, which moves you to a room selected at random within the castle)
I-INVENTORY (to add to your possessions, and used if you want, for example, to buy food or an axe)
Q-QUIT (to terminate the Adventure at any time)
P-PICK UP (treasure)

Once you've entered your command, and it has been reduced (if necessary) to a single letter, the program draws a line across the screen to help organize the information on it.

If you have entered "Q" (for "quit"), the program goes straight to line 120 where your final score is calculated and printed:

480 PRINT:PRINT:PRINT "--------------
----------------------":PRINT
490 IF AS="Q" THEN 120
As was explained much earlier in this book, the computer knows from the contents of the A array which directions are valid for travel from the current room. You'll see here that a different message is triggered for an attempt to move in each direction. This gives the impression that the computer is choosing from a vast set of possible replies, rather than just saying each time "YOU CAN'T GO THAT WAY."

500 IF A$="N" AND A(RO,1)=0 THEN
PRINT "NO EXIT THAT WAY":GOTO 440
510 IF A$="S" AND A(RO,2)=0 THEN
PRINT "THERE IS NO EXIT SOUTH":GOTO 440
520 IF A$="E" AND A(RO,3)=0 THEN
PRINT "YOU CANNOT GO IN THAT DIRECTION":GOTO 440
530 IF A$="W" AND A(RO,4)=0 THEN
PRINT "YOU CANNOT MOVE THROUGH SOLID STONE":GOTO 440
540 IF A$="U" AND A(RO,5)=0 THEN
PRINT "THERE IS NO WAY UP FROM HERE":GOTO 440
550 IF A$="D" AND A(RO,6)=0 THEN
PRINT "YOU CANNOT DESCEND FROM HERE":GOTO 440
Fight or Flight
When confronted with a monster, you can enter either "F" (if you want to fight) or "R" (if you want to run). But, because we do not want to encourage cowardice, you'll discover that around 70% of the time you attempt to run, the computer will tell you it is impossible.

Line 560 is used to generate a random number between zero and one if the "R" option is entered, and if this random number is greater than .7, the program jumps to the Unsuccessful Attempt To Run routine:

560 IF A$="R" AND RND(1)>.7 THEN 2420
When it hits the routine, the computer tells you NO, YOU MUST STAND AND FIGHT and then changes your order to "F" and then returns to the field of action:

2410 **************
2420 REM UNSUCCESSFUL ATTEMPT TO RUN
2430 PRINT "NO YOU MUST STAND AND FIGHT"
2440 A$="F"
2450 GOSUB 3520
2460 GOTO 590
If, however, the random number generated is not greater than .7, you are asked which direction you wish to run in:

570 IF A$="R" THEN PRINT "WHICH WAY DO
YOU WANT TO FLEE";:GOTO 460
There is one more possibility. A player may enter "F" when there is no monster in the room. A badly written program may well then obligingly produce a monster out of nowhere. WEREWOLVES AND WANDERER gets around this by checking to see, when an "F" command has been entered, whether the vital seventh element is less than zero, and if it is not, points out THERE IS NOTHING TO FIGHT HERE and then goes back to 440 for a new command:

580 IF A$="F" AND A(RO,7)>-1 THEN PRINT
"THERE IS NOTHING TO FIGHT HERE":GOTO 440
You'll recall how, at the start of the book, I mentioned that some commercial Adventures, such as Zork, allow for whole sentences to be entered by the player. Our single-letter inputs seem pretty tame compared to this. But, as you'll see in our final program-CHATEAU GAILLARD-which accepts two-word commands, the way the program processes the single-letter input in this program forms the basis of the mechanism used for two, or more, word inputs.
