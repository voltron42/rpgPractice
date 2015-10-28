23
ADDING EXCITEMENT
Finally, we'll look at a few ways you can add interest and "playability" to your programs.

The two most obvious things you should strive for are (a) unpredictability and (b) stability. These may seem to be mutually incompatible, but of course they are not. The unpredictability refers to the unknowns which a player must cope with when traversing the rocky plains of your Adventure. The stability refers to the environment which must (unless you have explicitly arranged for something different, such as a magician who causes the walls to shift every time you turn your head) be sufficiently stable and coherent to allow it to be mapped.

Map-Making
People who try to solve the Adventures you create enjoy making maps as they explore. To allow them to create maps, your Adventures should be relatively stable and coherent. As you've probably gathered, creating an initial map and a Travel Table from it, lies at the heart of Adventure programming. All else is commentary.

Get the map (and the Table) right and the rest should fall into place.

Structured Programming
As in all fields of human activity, conventions have developed in computer programming regarding a "better way" to do things. One of these ways, as we've pointed out, is called "structured programming," which involves starting your Adventure program (or any computer program) "from the top down," working the main components of the program out in broad strokes before fleshing it out with details. This means that there is an overall structure to the program which is not obscured by the actual code.

The Adventures in this book were all written after an outline for the structure was developed. In essence, the programs consist of a "master loop" at the beginning of the program which calls each of the activities the program must carry out. Each of these activities is in a separate subroutine. The subroutines can be developed, and debugged, one by one. If an error is found, for example, in the way the contents of a room are printed out, it is far easier to go straight to a routine headed "ROOM CONTENTS" than it would be to wade through 20K of program.

Of course, in the real world, things are rarely as neat as they are in theory, and my programs are not as clean and transparent as I might have hoped. Reworking areas of code tends to muddy the work of even the best-intentioned programmer. In the ideal world, an Adventure program might start like this:

10  REM NAME OF ADVENTURE
20  GOSUB 9000:REM INITIALIZE
30  GOSUB 8000:REM PRINT PLAYER ATTRIBUTE
40  GOSUB 1000:REM PRINT ROOM DESCRIPTION
50  GOSUB 8000:REM CHECK IF MONSTER PRESENT,
               AND IF SO, PRINT DESCRIPTION
60  GOSUB 7000:REM CHECK IF TREASURE
               PRESENT, AND IF SO, PRINT
               DESCRIPTION
70  GOSUB 2000:REM ACCEPT PLAYER INPUT
80  IF player wants to pick something up then GOSUB 2500
90  IF player wants to fight then GOSUB
    3000.........and so on
150 GOTO 30
A structure similar to this was written for each of the programs in this book, before I really had much idea how I was to accomplish the goals I had set myself in any of the subroutines.

All programs were written out completely on paper before the computer was even turned on, so that the game could be "hand run" before starting to grapple with it on the computer. This made it possible to catch the worst bugs right at the beginning.

I strongly advise you to follow a similar process when you write programs. I had read this advice myself, in several articles and books, and-predictably enough-had ignored it, until I found myself on a two week holiday without a computer, miles from the nearest computer dealer, and with a burning desire to write a major program I had thought up.

I ended up writing most of the program out on paper. Among the many great advantages I discovered about programming in this way was the willingness with which I completely discarded whole blocks of code if they turned out to be unworkable. It is much more difficult to decide to erase a whole section of code from a program once you actually have it in the computer than it is to just tear up a piece of paper. The temptation-when the program is already in the computer-is to fiddle with it, in an attempt to make it work, at least after a fashion.

Working on paper, then, tends to prevent code which really should not be in a program from somehow being patched together to make it work.

As well as helping in the early "get it working" stage, a structured program tends to invite improvements. Once you have your first original Adventure program up and running, you can go back to it some time in the future to try and make the fight sequence more interesting, for example, by going to that part of the code which covers the Big Clash. You will not have to wade through vast acres of codes, trying to work out just what each line does, and which particular lines control the monster interaction.

These methods were used for all four programs in this book. It may prove instructive to follow through the listing of some of the programs and work out which section does what. In many programs, you'll see that I've included lines of asterisks as a REM statement. These break the program into separate modules which should help you follow the program through.

Once you have your program up and running satisfactorily, so the player can move around the environment in accord with your map, and the mechanics of "pick up," "drop," "eat," "trigger magic spell," and the rest are working, you can then start elaborating your masterpiece. The remainder of this final chapter is devoted to ideas which you can include as embroidery on your basic program.

The Purpose
There must be some reason for the player to be taking part in the Adventure. Write a brief scenario explaining where and why the Adventure is taking place. Give the player a realistic goal (such as to survive till he or she escapes, as in our Adventures), or to find the Golden Horn of Apostraphe and bring it safely back to base, to beat the Monster Hunting Club of America' s current record of 342 of the beasties bagged in one session, to rescue the prince stranded at the top of his Castle Tower, or whatever. Make the purpose clear to the player, and make sure a significant percentage of the acts he or she takes part in are related to the achievement of the goal.

The Player Character
Instead of allowing the computer's random number generator to produce the initial character (with x points of intelligence and y of brute strength and only z of magic power), you could perhaps provide for the player to build his or her character from scratch, and then set it out on the Adventure. Running through the same Adventure a number of times with different characters could be very interesting. Of course, you'd have to put limits on the attributes a character could acquire, or you'd have some greedy soul talking the next ten million games worth of strength to be totally beyond defeat. He or she could just blunder wholesale through the environment, crushing all comers.

Experience
You may well wish to provide an opportunity to save a program while it is underway, so a player would not be forced to abort a complex Adventure in mid-flight and then retrace his or her early steps in order to try and solve it. The player's "experience" (attributes, cash, weapons, and the like) would also be saved along with the game in progress.

Quartermastering
Don't settle for just swords and broad axes. Use your imagination to offer a player a host of goodies. Rope often comes in useful. Let a player pass an apparently worthless coil of rope in one room, when six rooms later it is needed to get down a cliff (in the same way the silver key is needed for the first locked door in Citadel) and a player will soon learn that not only weapons and booty should be carried.

Magic
In "real life" role-playing games, combat and magic use are two of the highlights. Because of the infinite variety of magic spells which could be imagined (from causing the ground to fall open beneath an enemy's feet to turning into a canary and singing until everyone goes crazy) it is extremely difficult to program your computer to respond to magic in the same flexible way a Dungeon Master may do. However, we have seen two types of magic in our games (instant, random teleportation and battle victory) and there is no reason at all why you should not add as many additional kinds of magic as you like. Perhaps you might allow your player to choose from a menu of spells at the start of a game, before he or she knew what sort of problems lay ahead. They could have specific effects, which you could spell out in the program, or in notes provided for the player.

Limits
Assign a weight to each object which the player can find, and let the player know that he or she has only limited strength. Soon, the player will have to determine whether it is better to shuck the chain mail armor in order to carry a brace of invisibility spells, or to stick with the hardware and hope the wizardware won't be needed.

Research
Get a few popular history books. Early battle descriptions will give you a host of ideas to convert into terrain, weaponry, enemy reactions, and so on. Every period you read about will suggest at least one Adventure and probably more. The time and place will do much of the work involved in creating the background to the environment.

Handy Extras
As well as offering the player goodies throughout the environment (such as the map we mentioned), you could start with a much bigger arsenal than we have in the programs in this book. How about a small group of mercenaries, who can be sacrificed as dragon fodder, or a pack pony to carry your loot?

Alternative Treks
If one of the purposes of the Adventure campaign is to amass as much treasure as possible, you might like to allow for the player to backtrack from time to time to dump his or her treasure in safety at the entrance, before moving back into the dungeon or whatever. A group of bandits, hidden in room 34, will probably take everything our hapless player is carrying, so if some valuables have already been spirited away-out of the main theater of war-all is not lost.

Time Limits
If you feel some of your Adventures are a little too simple to solve, enhance them by including a real time clock in the program, one which counts down on the screen. This will keep the pressure up, and adds another layer of interest.

Climate
You might like to introduce elements of weather into your environment. A very cold room which means the player must burn something from his or her supplies to survive is one possibility. Another room could be filled with steam, which rusts swords but leaves axes untouched. A slimy floor can produce its own complications. You are sure to be able to think of other climate-like environmental factors which will help maintain interest.

There are a number of other ideas you might like to incorporate into your Adventures, such as the following:

A monster which does not stay passively in one room, but follows you relentlessly, once you've woken him up.
A few other "pseudo-players" under the computer's control which appear to be exploring the environment as well. The player can meet these people from time to time, possibly get ting information about future dangers from them.
A "help" command for the player to invoke if he or she feels hopelessly lost. A severe penalty (such as half the player's gold) should be extracted for using this option to ensure it is not used frequently.
Doors can be locked, impassable, stuck, or traps. Walls can fall in on players, floors give way to a gaping crevasse, and so on. Pictures can slide from walls, hitting our hapless player on the head, and so on. You might like to create an environment just for paranoid players, where every element seems hell-bent on injury.
And so on. I'm sure by now you've got a hundred ideas in your head just waiting to be expressed as Adventure programs. Good Adventuring. The suggestions for further reading in the appendix may well serve-as sources of other ideas to incorporate into your own Adventures.

May the Dreaded Ice-Dragon not molest you, and may all your chests be filled with Elven Gold.
