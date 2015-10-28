10
SPEAKING IN OTHER TONGUES
Here's the routine which acts on the other possible inputs:

590 IF A$="I" THEN GOSUB 3010:RETURN
600 IF A$="C" AND FOOD=0 THEN PRINT
"YOU HAVE NO FOOD":GOTO 440
610 IF A$="P" THEN GOSUB 2350:RETURN
620 IF A$="F" THEN 730
640 IF A$="C" THEN GOSUB 2480:RETURN
650 IF A$="N" THEN RO=A(RO,1)
660 IF A$="S" THEN RO=A(RO,2)
670 IF A$="E" THEN RO=A(RO,3)
680 IF A$="W" THEN RO=A(RO,4)
690 IF A$="U" THEN RO=A(RO,5)
700 IF A$="D" THEN RO=A(RO,6)
710 RETURN
You'll see (line 600) that if the player has entered "C" (for "consume," indicating a desire to eat), a check is made to see if there is any available food. If the variable FOOD equals zero, you are told YOU HAVE NO FOOD, and the computer returns to 440 for a new command.

The lines 650 through to 700 move you within the castle environment, as was explained earlier.

Making Magic
If you enter "M" (for "magic amulet") you'll be transported somewhere at random within the castle (but not to the entrance or exit). Only one line is needed to achieve this:

630 IF A$="M" THEN RO=INT(RND(1)*19)+1:
IF RO=6 OR RO=11 THEN 630 ELSE 710
If you enter a "C" (for "consume") you are sent to the Eat Food routine, from line 2470, where the computer first checks to see if you have any food. If you do not (that is, the variable FOOD equals zero) the program returns from the subroutine.

If you do have food, the computer tells you how much you have, and then you are asked how much you want to eat (line 2520). Each unit of food gives you five units of strength (line 2560). After a short delay (called by 2570) the program returns:

2470 **************
2480 REM EAT FOOD
2490 CLS
2500 IF FOOD<1 THEN RETURN
2510 PRINT "YOU HAVE";FOOD;"UNITS OF FOOD"
2520 PRINT "HOW MANY DO YOU WANT TO EAT";
2530 INPUT Z
2540 IF Z>FOOD THEN 2530
2550 FOOD=INT(FOOD-Z)
2560 STRENGTH=INT(STRENGTH+5*Z)
2570 GOSUB 3520:CLS
2580 RETURN
Picking Up Treasure
The Pick Up Treasure routine from 2340 has two checks which are imposed before you are able to actually take possession of treasure to add to your store of wealth.

First the program checks to see if there is actually any treasure there (line 2360), sending you back to enter a new command with the words THERE IS NO TREASURE TO PICK UP. Once you have passed this hurdle, the computer checks to see if you have a flaming torch. You'll recall we discussed this earlier, when looking at the Light/Dark Routine. Line 2370 was outlined at that time, and this is the line which makes the check.

If you have passed both of these checks, the value of the treasure is added to your wealth (line 2380) and the room is "emptied" (2390) by setting the relevant element to zero so that you cannot simply revisit the same room over and over again and pick up the same treasure several times.

2340 REM **************
2350 REM PICK UP TREASURE
2360 IF A(RO,7)<10 THEN PRINT "THERE IS NO
TREASURE TO PICK UP":GOSUB 3520:RETURN
2380 WEALTH=WEALTH + A(RO,7)
2390 A(RO,7)=0
2400 RETURN
The Quartermaster's Store
One of your real tests within Adventure programs-after the major tests of trying to make sense of the environment, and staying alive-is the management of your resources. You'll recall I suggested that the more you expect the player to do on each game turn, the more satisfactory the program is likely to be.

Although you know, for example, that you need a torch in order to see anything, and to pick up treasure, a new player to the game may take quite a while to come to this conclusion. All a new player needs to know is the vocabulary, the fact that if he or she runs out of strength death is on the way, and that the purpose of the quest is to find the exit from the castle.

When you're designing an Adventure program, work out exactly what you're going to tell the player before the start of a game. An Adventure game with a computer is in part like an extended puzzle, in which the player tries to work out the problems set by the programmer. In WEREWOLVES AND WANDERER, as with the programs you are likely to write, there is far more happening than the simple solving of puzzles set by the programmer.

The Inventory/Provisions routine of this program is one of the keys to controlling the game. Once a player realizes that a torch is vital, and that it can be bought by going to the Inventory/provisions routine, and that possession of an axe, sword or magic amulet can be a great help in trouble, he or she is well on the way to ensuring survival until the map of the castle can be deduced.

The Inventory/Provisions routine is largely self-explanatory. Here it is:

3000 REM ****************************
3010 REM INVENTORY/PROVISIONS
3020 PRINT "PROVISIONS & INVENTORY"
3030 GOSUB 3260
3040 IF WEALTH<.1 THEN Z=0:GOTO 3130
3050 PRINT "YOU CAN BUY 1 - FLAMING TORCH ($15)"
3060 PRINT "            2 - AXE ($10)"
3070 PRINT "            3 - SWORD ($20)"
3080 PRINT "            4 - FOOD ($2 PER UNIT)"
3090 PRINT "            5 - MAGIC AMULET ($30)"
3100 PRINT "            6 - SUIT OF ARMOR ($50)"
3110 PRINT "            0 - TO CONTINUE ADVENTURE"
3120 INPUT "ENTER NO. OF ITEM REQUIRED"; Z
3130 IF Z=0 THEN CLS:RETURN
3140 IF Z=1 THEN LIGHT=1:WEALTH=WEALTH-15
3150 IF Z=2 THEN AXE=1:WEALTH=WEALTH-10
3160 IF Z=3 THEN SWORD=1:WEALTH=WEALTH-20
3170 IF Z=5 THEN AMULET=1:WEALTH=WEALTH-30
3180 IF Z=6 THEN SUIT=1:WEALTH=WEALTH-50
3190 IF WEALTH<0 THEN PRINT "YOU HAVE TRIED TO
CHEAT ME!":WEALTH=0:SUIT=0:LIGHT=0:AXE=0:SWORD=0:
AMULET=0:FOOD=INT(FOOD/4):GOSUB 3520
3200 IF Z<>4 THEN 3030
3210 INPUT "HOW MANY UNITS OF FOOD";Q:Q=INT(Q)
3220 IF 2*Q>WEALTH THEN PRINT "YOU HAVEN'T GOT
ENOUGH MONEY":GOTO 3210
3230 FOOD=FOOD+Q
3240 WEALTH=WEALTH-2*Q
3250 GOTO 3030
3260 IF WEALTH>0 THEN PRINT:PRINT:PRINT "YOU HAVE
$";WEALTH
3270 IF WEALTH=0 THEN PRINT "YOU HAVE NO MONEY"
:COSUB 3520:RETURN
3280 FOR J=1 TO 4:PRINT:NEXT J
3290 RETURN
You'll see the program first checks to see how much money you have (by going to the subroutine from 3260) and tells you the state of your finances. It will send you back to the main program (3040) if you have no money left (that is, if the variable WEALTH has a value less than .1).

Next the menu of possibilities is shown, with a code number next to each. You enter a 1 if you want to buy a flaming torch, for instance, 6 for a suit of armor, and 0 to return to the main program. After you enter your chosen number (3120) the computer uses the lines from 3130 through to 3180 to allow you to "make your purchase" by modifying variables and subtracting the cost from your WEALTH. This holds true except for FOOD (item 4 on the menu) which is handled slightly differently.

Do not, whatever you do, try to cheat by buying more than you can afford. The drastic line 3190 prints up YOU HAVE TRIED TO CHEAT ME and everything you own-except for one quarter of your food-is taken away for you.

If you have selected item 4 on the menu, indicating that you want to buy food, the routine from 3210 to 3240 looks after this for you.

The final subroutine (3260 to 3290) tells you how much money (if any) you have, PRINTs four blank lines, then returns for your next menu selection. This routine cycles-as I pointed out earlier-until you enter zero, indicating that you wish "TO CONTINUE ADVENTURE."
