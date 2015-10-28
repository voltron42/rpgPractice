11
THE BIG FIGHT
Fights (or melees, as they are often known in Adventure games) are often the high points of a game. Do all you can to bring in the sound and graphics of your particular computer system to make the fights as exciting as possible.

Certainly, you should dress up the whole fight sequence in WEREWOLVES AND WANDERER and then go on to add similar effects to fight sequences you initiate in your own programs.

The fight consists of three distinct segments, and each segment should be handled as a separate programming task. The three segments, which we will discuss one at a time, are Preparation, The Battle itself, and The Result.

Be Prepared
The fight segment uses INKEY$ to get it underway. (You can, by the way, change nearly all player inputs in WEREWOLVES AND WANDERER into INKEY$ inputs.) Line 740 waits until you are not touching the keyboard, then prints up (750) the message PRESS ANY KEY TO FIGHT. Line 760 holds the action until a key is pressed.

Here's the Preparation segment of the program:

720 REM **************************
730 REM FIGHT
740 IF INKEY$<>"" THEN 740
750 PRINT "PRESS ANY KEY TO FIGHT"
760 IF INKEY$="" THEN 760
770 IF SUIT=1 THEN PRINT "YOUR ARMOR INCREASES YOUR
CHANCE OF SUCCESS":FF=3*(INT (FF/4)):GOSUB 3520
780 CLS:FOR J=1 TO 6:PRINT "*_*_*_*_*_*_*_*_*_*_*_*
_*_*_*_*_*_*_*_*": NEXT J
790 IF AXE=0 AND SWORD=0 THEN PRINT "YOU HAVE NO WEAPONS"
:PRINT "YOU MUST FIGHT WITH BARE HANDS":FF=INT(FF + FF/5)
:GOTO 870
800 IF AXE=1 AND SWORD=0 THEN PRINT "YOU HAVE ONLY AN AXE
TO FIGHT WITH":FF=4*INT(FF/5):GOTO 870
810 IF AXE=0 AND SWORD=1 THEN PRINT "YOU MUST FIGHT WITH
YOUR SWORD":FF=3*INT(FF /4):GOTO 870
820 INPUT "WHICH WEAPON? 1 - AXE, 2 - SWORD";Z
830 IF Z<1 OR Z>2 THEN 820
840 IF Z=1 THEN FF=4*INT(FF/5)
850 IF Z=2 THEN FF=3*INT(FF/4)
You'll recall from our talk about monsters that the variable FF stands for ferocity factor, and the higher the FF, the lower is your chance of defeating the particular monster. If you are wearing armor (as line 770 informs you) your chance of success is increased, because FF is set to three-quarters of its former value.

If you have no weapons (that is, AXE equals zero and SWORD equals zero), the FF is increased by one fifth. It is important in programs like this one to give the player a real reason for spending his or her hard-won wealth on things such as weaponry. Adjusting the chance of success-as fiddling with FF does in this program-gives a specific, and rational, reason to invest in weapons.

If you have both an axe and a sword, lines 820 and 830 allow you to choose from between them, modifying your chances accordingly.

Taking Up Arms
The Battle itself is controlled by lines 880 to 940:

860 REM ****************************
870 REM THE BATTLE
880 PRINT:PRINT
890 IF RND(1)>.5 THEN PRINT M$;" ATTACKS" ELSE PRINT
"YOU ATTACK"
900 GOSUB 3520
910 IF RND(1)>.5 THEN PRINT:PRINT "YOU MANAGE TO WOUND
IT":FF=INT(5*FF/6)
920 GOSUB 3520
930 IF RND(1)>.5 THEN PRINT:PRINT "THE MONSTER WOUNDS
YOU!":STRENGTH=STRENGTH-5
940 IF RND(1)>.35 THEN 890
This routine actually "stage-manages" the fight, maintains a role as referee, and reports to you during the fight on how it is going. Each time you manage to wound the monster (line 910) the ferocity factor is reduced to five-sixths of its former value. Your strength is diminished by five each time the monster manages to wound you. Two-thirds of the time the computer comes to line 940, it will go back to line 890 for another round.

The Tumult and the Shouting Dies
Line 950 compares a number generated at random between zero and 15 with the ferocity factor, and if it is higher, gives you the victory, adding one to your MK (Monster Kill count). If you do not win, line 960 tells you the bad news, and halves your remaining strength.

950 IF RND(1)*16>FF THEN PRINT:PRINT "AND YOU MANAGED
TO KILL THE ";M$:MK=MK+1:GOTO 970
960 PRINT:PRINT "THE ";M$;" DEFEATED YOU "
:STRENGTH=INT(STRENGTH/2)
970 A(RO,7)=0:GOSUB 3510:PRINT:PRINT:GOSUB 3520:RETURN
The last line of this section gets rid of the monster, and after a pause, prints a few blank lines and another pause, sends you back to the main section of the program.

As I said at the beginning of this chapter, the melee is one area in Adventure programs when any effects that you can call into play are welcome. You should get some ideas on ways of dressing up fight sequences when I present an elaborated version of this program later on in the book, but you're sure to be able to work out many others for yourself, once you see the kind of things which can be done.
