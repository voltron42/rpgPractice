14 DRESSING IT UP
====
Elaborations
----
Once you have a program up and running properly, behaving in accordance with an external map, the job is not yet done. You can stop for a while with the program you have, and it should be a source of a great deal of enjoyment. However, an urge to elaborate the original program is likely to arise after a while.

You now have a reasonable version of WEREWOLVES AND WANDERER up and running on your system. You know the basics of creating Adventure games, and are probably well on the way to completing your first major epic.

There is still a lot which can be added to WEREWOLVES AND WANDERER to make it more enjoyable for players, and once I'd spent a few weeks with the program in its original form, I decided to tackle the job of dressing it up.

A complete listing of the improved version appears in the next chapter, while this chapter contains part of a sample run of the new program, so you can get an idea of what has been added.

You can easily modify your original listing by changing some lines, and adding others in between those of the first version.

It is best to compare your listing with the next one, line by line (especially in the room descriptions) to make sure you have entered all the modifications. To simplify this, here are the major changes I have made:

* The torch can be knocked out of your hand during a fight-905.
* Your weapons can also be knocked out of your hands during a fight-906, 907.
* More monsters have been added-415, 416, and 2920
* Jewels and the like can be found, rather than just unidentified "treasure"-355, 357.
* Note that not all doors are now described explicitly, leaving more to the player's skill in determining where the exits are. As well as this, the rooms are described in much greater detail, and some factors are added which only appear from time to time. You should check all the room descriptions.
* You can call for a tally at any time during the course of a game, by entering "T" when asked for a command-706.
* There is a pretty effect (which you're sure to be able to elaborate still further) when the amulet is triggered-625.
* The Inventory routine now includes feedback on the objects you have bought-delete 3120 and 3130 and add 3131, 3133, 3134, and 3135. Other lines will have to be renumbered as 3136 and 3137.
* Your starting wealth and strength are now determined ran domly at the start of each game-2620, 2630.

Sample Run
-----
Here's a sample run of the elaborated version:
```
YOU CAN BUY 1 - FIAMING TORCH ($15)
            2 - AXE ($10)
            3 - SWORD ($20)
            4 - FOOD ($2 PER UNIT)
            5 - MAGIC AMULET ($30)
            6 - SUIT OF ARMOR ($50)
            0 - TO CONTINUE ADVEKTURE
YOU HAVE A TORCH

ENTER NO. OF ITEM REOUIRED? 2


YOU HAVE $ 9
YOU HAVE A TORCH
YOUR SUPPLIES NOW INCLUDE ONE AXE

------------------------------------

ANDREW, YOUR STRENGTH IS 107
YOU HAVE $ 9
YOU ARE CARRYING AN AXE


*****************************



YOU ARE IN THE HALLWAY
FROM THE DUST ON THE GROUND YOU CAN TELL
NO-ONE HAS WALKED HERE FOR A LONG, LONG TIME
THERE IS A DOOR TO THE SOUTH
THROUGH WINDOWS TO THE NORTH YOU CAN SEE
A SECRET HERB GARDEN
THERE IS TREASURE HERE WORTH $ 100


WHAT DO YOU WANT TO DO? P


*****************************



YOU ARE IN THE GREAT HALL, AN L-SHAPED ROOM
THERE ARE TWO DOORS IN THIS ROOM
THE WOOD PANELS ARE WARPED AND FADED...
AS YOU STAND THERE, YOU HEAR A MOUSE
SCAMPER ALONG
THE FLOOR BEHIND YOU...
YOU WHIRL AROUND...BUT SEE NOTHING!


DANGER...THERE IS A MONSTER HERE....
IT IS A DEVASTATING ICE-DRAGON

THE DANGER LEVEL IS 20 !!

WHAT DO YOU WANT TO DO? F


YOU MANAGE TO WOUND IT
*&%%$#$% $%# !!@ #$$#  #$@!  #$ $#$
WILL THIS BE A BATTLE TO THE DEATH?
HE STRIKES WILDLY, MADLY...........

THE MONSTER WOUNDS YOU!

THE DEVASTATING ICE-DRAGON DEFEATED YOU


ANDREW, YOUR STRENGTH IS 28
YOU HAVE $ 23
YOU ARE CARRYING THE MAGIC AMULET


******************************



YOU ARE IN THE GREAT HALL, AN L-SHAPED ROOM
THERE ARE TWO DOORS IN THIS ROOM
THE WOOD PANELS ARE WARPED AND FADED...


WHAT DO YOU WANT TO DO? M


                       *
                        *
                         *
                          *
                           *
                            *
                             *
ANDREW, YOUR STRENGTH IS 23
YOU HAVE $ 23
YOU ARE CARRYING THE MAGIC AMULET


******************************


YOV ARE IN THE REAR VESTIBULE
THERE ARE WINDOWS TO THE SOUTH FROM WHICH YOU
YOU CAN SEE THE ORNAMENTAL LAKE
```
From these "snapshots" you have probably gained a taste of how the original program has been greatly elaborated. I've tried to ensure-and this may be worth keeping in mind when you're working on your own programs-that any elaboration is not added purely for its own sake. Instead, you should decide whether or not to add further "frills" to a program purely in terms of whether or not they add to the player's experience of reality, excitement, and involvement when playing the game.
