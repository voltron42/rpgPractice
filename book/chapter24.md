APPENDICES
1 Suggestions for Further Reading
There is an impressive body of support literature for Adventure gaming, as a visit to your local Games shop will demonstrate. The selection which follows is very much a product of my own interest in the field, and it should not be seen as even an attempt at selecting the "best" publications. However, the list is made up from those works which I've found of interest and value. There are probably over a hundred of equal worth, but at least this list should give you a starting point:

THROUGH DUNGEONS DEEP: A Fantasy Gamers' Handbook-Robert Plamondon (Reston Publishing Company, Inc., Reston, Virginia, 1982)

What is Dungeons and Dragons-John Butterfield, Philip Parker and David Honigmann (Penguin Books Ltd., Harmondsworth, Middlesex, England, 1982) [Dungeons and Dragons is the adventure game which was originated by TSR Hobbies Inc. The term is a registered trademark.]

Dicing with Dragons, an Introduction to Role-Playing Games-Ian Livingstone (Routledge & Kegan Paul, London, Melbourne and Henley, 1982)

Fantasy Role-Playing Games-J. Eric Holmes (Hippocrene Books, Inc., New York, 1981)

As well as books, there are a number of game-playing aids which will help you in building the framework environment within which your Adventure will be enacted. Another good source of ideas is rule books for specific games. Among those which may help you are:

MONSTER AND TREASURE ASSORTMENT-TSR Games, POB 756, Lake Geneva, WI 53147, 1980, distributed by Random House, Inc. This publication, which comes in a variety of "sets" and "levels" is a great place for getting ideas for monster names and treasure descriptions.

DUNGEON GEOMORPHS-Also produced by TSR Games, the "geomorphs" are a number of rectangular and square map sections, which can be fitted together in an infinite variety of ways to form dungeon and cavern floor plans.

BOOK 0, AN INTRODUCTION TO TRAVELLER-Loren K. Wiseman (Games Designers' Workshop Inc., POB 1646, Bloomington, IL 61701). This is an example of the rule book I mentioned. Traveller is a science-fiction Adventure in the far future, and BOOK 0 is an introduction to the concepts of role-playing with specific attention to Traveller. As Book 0 points out, the "total amount of Traveller material available is staggering and growing all the time" (p. 11). Despite this, BOOK 0, BOOK 1, BOOK 2, and BOOK 3 are all you really need to play the game, and BOOK 0 is enough, by itself, to give you a taste for laying out Adventure programs in deepest space. Leaf through a copy at your Games shop, and you'll see how many great ideas it contains which you can apply to your own pro grams. Note that Traveller is a registered trademark.

DUNGEONS & DRAGONS boxed game sets are a good way to learn some of the possibilities of Adventure gaming. You can start (and stay, if you like) with the BASIC SET WITH INTRODUCTORY MODULE. This set typically contains two books, and a set of six dice, with various numbers of sides. The books are BASIC RULES, which set out the concepts of role-playing, and explain how characters are "rolled up," how their personalities are derived, how fights are sorted out, and many aspects of the Dungeon or Game Master's role.

As well as dice and BASIC RULES, the set contains a campaign background, THE KEEP ON THE BORDERLANDS. This comes with a great deal of information, including a series of maps, room and player information, and further details on fight resolution. I feel that the BASIC RULES boxed set is probably the best source of ideas you can get your hands on. It is also a very good way to achieve greater understanding of how role-playing games are developed and controlled.

WARNING: These sources of ideas are suggested, of course, only for your own use, to produce games for your own entertainment. You cannot incorporate copyright material into programs for public sale.

2 Random Place/Monster Names
I set my computer the relatively simple task of generating names for monsters. Here's the program I used:

10 REM CHARACTER NAME GENERATOR
20 RANDOMIZE VAL(RIGHT$(TIME$,2))
30 DIM 2(5)
40 FOR T= 1 TO 5:READ Z(T):NEXT
50 FOR H = 1 TO 4
60 FOR T = 1 TO 4 + INT(RND(1)*4)
70 B = 0
80 A = 66 + INT(RND(1)*25)
90 IF RND(1)>.7 AND (A=69 OR A = 73
OR A = 79 OR A = 85) THEN 80
100 IF (T = 2 OR T = 5 OR T=7) THEN
B= Z(INT(RND(1)*5))
110 IF B<>0 THEN LPRINT CHR$(B);:GOTO 130
120 LPRINT CHR$(A);
130 NEXT T
140 LPRINT "    ";
150 NEXT H
160 LPRINT
170 GOTO 50
180 DATA 65,69,73,79,85
And here are some (I got rid of the hopeless ones) of the results of running it, producing a number of names you might like to incorporate into your programs either as names of monsters or as mythical place names:

LAQH      REBLEP    IAMD      TIGNEZA
QOUHEXE   PISBI     OAKHO     BIHLA
GEDIE     CISWAH    VELN      NEYH
GOJUO     PAWTOZI   NAOJM     MEDSA
TEHOOVO   GAMLO     BEDO      HEZRI
MEZPO     KISC      MANMY     KOMNOMO
JELM      LESH      YXPHI     VEDLE
EANBENG   YIQC      DOXT      FATGEKA
BEZMAZI   LIIYEX    NIGRAP    HOPNOG
COLOAZ    SALUENI   NEYOKEE   LOBIEBE
POGWOR    ROKVA     POYNOZ    GODWUR
XEZKE     JEBJO     NEYMEO    WOOF
RECCI     XEVFI     MIDO      MATWEO
GOUL      KEBGA     CEXFAV    JEXV
ZAHNIL    OIYYI     FEBSEX    ZONHIP
JAPDANZ   QINNAYP   VEBXOI    LOCQI
POBLIP    GOLJEB    IERVO     COER
CIKWAB    TEEQAFO   ZARW      FAHSEYA
HOKM      DIRI      GEXNAC    HEHKO
XOEOPU    BIRWAI    YEQMIIE   SAFWOLI
FIDWIY    PAWK      DAHI      COJJOLA
VINPERO   XITFO     JEMR      MOXKO
TEYLENO   LERE      DOOKII    VOCVOF
ZIZS      PAFXI     YOXLO     NISL
RAJYOR    WACNA     YALDAR    BIDBIF
POLVO     LYMC      TERC      WAFMOK
XENTADN   REZN      WILTEG    DINWOL
GEYP      CINQOCA   QABYI     WOKIA
