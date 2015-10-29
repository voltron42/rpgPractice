8 THE MAJOR HANDLING ROUTINE
=======
It is time now to investigate the section of program which lies at the heart of the entire Adventure, the Major Handling Routine.

A Short Delay
--------
Before we look at that, however, I want to show you a very short section of program, really only one line, which is very important to pacing the running of the program.

In contrast with many Adventure programs which allow a large vocabulary, and look at two or more words at a time, our WEREWOLVES AND WANDERER only responds to a limited vocabulary. Because of this, it reacts very rapidly to input from the player. In many cases, this reaction is so fast there is not even time to read what is on the screen before it scrolls off.

Therefore, a delay loop has been included within the program, at line 3520. It runs from 1 to 900 at the moment, which seems ideal to me on the system on which the program was developed. However, you may like to add to, or subtract from, it for your computer.

Here's the delay loop:
```
3500 ******************
3510 REM ADJUST LOOP BELOW TO YOUR SYSTEM
3520 FOR T=1 TO 900:NEXT T
3530 RETURN
```
You'll find this is called over and over again throughout the program, not only to give you a chance to read information on the screen before it vanishes, but to pace such things as a fight with a monster, or the descent of the elevator.

Your Strength Fades
-------
The game is over if you run out of strength, as was pointed out earlier. Therefore, you have to keep an eye on your strength rating, which diminishes throughout the game. You replenish your strength by eating food, which you can buy with the proceeds of treasure you find lying around the castle.

Here's the first part of the Major Handling Routine, which subtracts five from your strength (variable name STRENGTH) each time you move:
```
150 REM **********************
160 REM MAJOR HANDLING ROUTINE
170 STRENGTH = STRENGTH - 5
180 IF STRENGTH<10 THEN PRINT "WARNING, ";N$;",
YOUR STRENGTH":PRINT IS RUNNING LOW" :PRINT
190 IF STRENGTH < 1 THEN 2300: REM DEATH
```
As you can see, line 180 warns you that your strength is getting low if STRENGTH falls below 10. You will discover later that your strength can also fall during a fight. Sometimes you'll see this drops quite drastically if you are wounded.

If your strength falls below one (line 190), the program directs you to the Dead End subroutine from line 2290:
```
2300 REM DEAD END
2310 PRINT "YOU HAVE DIED........."
2320 GOSUB 3520
2330 GOTO 120
```
When you construct your own programs, you'll discover that within limits-the more things the player has to manage at a time, the more satisfying the program is to run. You should therefore aim to have something like our STRENGTH problem in your own programs, and a clear penalty, like our Dead End routine, if the problem is not handled satisfactorily.

The Final Reckoning
-----
The final score in WEREWOLVES AND WANDERER is related-as was pointed out earlier-to a number of factors. One of these is TALLY, the time for which you manage to survive. Each time the program traverses the rocky ground of the Major Handling Routine, it runs through line 200, which increments your TALLY by one:
```
200 TALLY = TALLY + 1
```
At the very end of the program, whether you've managed to find the exit or not, your score will be added up. Your score is three times your TALLY, plus five times your STRENGTH, plus two times your WEALTH, plus the value of your uneaten FOOD and thirty times the number of monsters you have killed (variable name MK). It is evident that the number of monsters killed has more effect on your final score than just about any other item. Here's the section of code which works out your final score:
```
120 PRINT:PRINT "YOUR SCORE IS";
130 PRINT 3*TALLY + 5*STRENGTH + 2*WEALTH + FOOD + 30*MK
140 END
```
There are is an infinite number of ways the final score can be calculated in an Adventure game. This is done by a simple scoring system in WEREWOLVES AND WANDERER, one you may well wish to modify. So long as your own games have some method of assessing the success or otherwise of a trek through your environment, it doesn't really matter what sort of final tallying system you devise. It can be as elaborate, or simple, as you choose.

Maintaining the Status Quo
--------
As you saw in the sample run before we started outlining this program, the player gets a Status Report each time the program cycles through the Major Handling Routine.

The Status Report is handled by the lines from 210. Here is the first section of the Status Report program:
```
210 PRINT N$;", YOUR STRENGTH IS";STRENGTH
220 IF WEALTH > 0 THEN PRINT "YOU HAVE $";WEALTH
230 IF FOOD > 0 THEN PRINT "YOUR PROVISIONS SACK
HOLDS";FOOD;"UNITS OF FOOD"
240 IF SUIT = 1 THEN PRINT "YOU ARE WEARING ARMOR"
250 IF AXE=0 AND SWORD=0 AND AMULET=0 THEN 320
260 PRINT "YOU ARE CARRYING ";
270 IF AXE=1 THEN PRINT "AN AXE ";
280 IF SWORD=1 THEN PRINT "A SWORD ";
290 IF SWORD + AXE > 0 AND AMULET=1 THEN PRINT "AND ";
300 IF AMULET=1 THEN PRINT "THE MAGIC AMULET"
310 PRINT
```
Your name is used in line 210 (N$, for "name string") before you are told your STRENGTH and then WEALTH (line 220). If the variable FOOD is greater than zero (line 230) the computer knows you have bought some food which you have not yet eaten, and line 230 prints up a message to this effect. The same goes for the suit of armor (line 240), the axe (270), sword (280), and magic amulet (300). The other lines (those which print up such things as YOU ARE CARRYING and the word AND) also use the value of the variables AXE, SWORD, and AMULET to determine when these bridging words should be printed.

Let There Be Light
-----
The presence, or otherwise, of a torch is crucial to many Adventure programs, including this one. The variable LIGHT is set initially to zero, meaning the player has not bought a "flaming torch."

If the player has no torch, he or she can see nothing. It is very dark within our castle. Lines 320 and 330 make up our Light/Dark Routine. If the player does not have a flaming torch (that is, variable LIGHT equals zero) the routine prints up IT IS TOO DARK TO SEE ANYTHING, and that's about it. If, however, you are carrying a torch (that is, LIGHT equals one) line 330 sends the computer to the vast subroutine from line 990 which describes what you can see in the room.
```
320 IF LIGHT=0 THEN PRINT "IT IS TOO DARK TO SEE ANYTHING"
330 IF LIGHT=1 THEN GOSUB 990:REM ROOM DESCRIPTION
```
Later on in the program, LIGHT is brought into play again. In the Pick Up Treasure Routine, the first line checks to see if you have a torch. If you do not, you are told curtly YOU CANNOT SEE WHERE IT IS, and are not given the chance to get it:
```
2370 IF LIGHT=0 THEN PRINT "YOU CANNOT SEE WHERE IT IS":
GOSUB 3520:RETURN
```
Objects In Disarray
------
You'll recall that the seventh element of a room held in the A array is set equal to the contents of the room. If this element is zero, there is nothing within the room the player can interact with.

The Object Handler depends totally upon this seventh element for its actions. Line 340 sets the variable K equal to the value of this element. Line 350 checks the value of K, knowing that if K equals zero, the room is effectively empty. If K is greater than nine (see line 360) the computer knows there is treasure in the room. If the room is not empty (K = 0) and does not contain treasure (K > 9) then the room must have a monster in it, and line 370 breaks the happy news.
```
340 K = A(RO,7):REM K IS SET TO CONTENTS OF ROOM
350 IF K=0 THEN 440:REM ROOM IS EMPTY
360 IF K>9 THEN PRINT "THERE IS TREASURE HERE WORTH $"K:
GOTO 440
370 PRINT:PRINT:PRINT "DANGER...THERE IS A MONSTER HERE...."
:GOSUB 3520
```
If there is a monster in the room (that is, if K is less than 0) the string variables M$ (for "monster string") is assigned to the name of one of the monsters. The variable FF (for "ferocity factor") is also assigned. The higher the FF, the lower the chance you have of winning a battle against the relevant monster.

However, as you'll see when we look at The Battle later on, the weapons you hold, and whether or not you are wearing armor, can modify the ferocity factor in your favor.

The next section of the program (lines 380 through to 440) assign the monster's name to M$, the ferocity factor to FF, and tells you all about it.
```
380 IF K=-1 THEN M$="FEROCIOUS WEREWOLF":FF=5
390 IF K=-2 THEN M$="FANATICAL FLESHGORGER":FF=10
400 IF K=-3 THEN M$="MALOVENTY MALDEMER":FF=15
410 IF K=-4 THEN M$="DEVASTATING ICE-DRAGON":FF=20
420 PRINT:PRINT "IT IS A ";M$
430 PRINT:PRINT "THE DANGER LEVEL IS";FF ;"!!"
440 GOSUB 3520
```
You can see how the "major handling routine" contributes another link in the chain which forges a feeling of reality in the Adventure environment. Any player action-such as picking up the torch-which produces a change within the Adventure, helps build mental images which sustain the illusion of the Adventure.
