3 THE ADVENTURE IN ACTION
======
Before we look at our first Adventure listing in detail, spelling out each element of its development, I'll show you some "snapshots" of it in action. This should help you make sense of the various elements of the program as they are presented.

First the program asks your name, which it will use from time to time throughout the game:
```
WHAT IS YOUR NAME, EXPLORER? ANDREW
```
The computer then proceeds to tell you-as it will after each action you initiate-your current status:
```
ANDREW, YOUR STRENGTH IS 95
YOU HAVE $ 75
IT IS TOO DARK TO SEE ANYTHING
WHAT DO YOU WANT TO DO? I
```
The question "WHAT DO YOU WANT TO DO" is asked after every move. The answers the computer understands will be explained a little later under the heading "Vocabulary." Note here that our hero, Andrew, has entered "I," which stands for "inventory." This means he wants to add to his supplies of provisions.

The computer goes to the relevant subroutine where Andrew's money is shown ("YOU HAVE $75") and then the list of available products and their prices is shown:
```
PROVISIONS  & INVENTORY

YOU HAVE $ 75

YOU CAN BUY 1 - FLAMING TORCH ($15)
            2 - AXE ($10)
            3 - SWORD ($20)
            4 - FOOD ($2 PER UNIT)
            5 - MAGIC AMULET ($30)
            6 - SUIT OF ARMOR ($50)
            0 - TO CONTINUE ADVENTURE
ENTER NO. OF ITEM REQUIRED? 1
```
Andrew enters a "1," telling the computer he wants to buy a "flaming torch" (item 1 on the inventory menu). The torch is added to his possessions, his wealth is decreased by $15, the cost of the torch, and the menu option is shown again:
```
YOU CAN BUY 1 - FLAMING TORCH ($15)
            2 - AXE ($10)
            3 - SWORD ($20)
            4 - FOOD ($2 PER UNIT)
            5 - MAGIC AMULET ($30)
            6 - SUIT OF ARMOR ($50)
            0 - TO CONTINUE ADVENTURE
ENTER NO. OF ITEM REQUIRED? 4
HOW MANY UNITS OF FOOD? 20
```
This time, Andrew has chosen item 4 on the menu, signifying that he wants to buy food. Food is a very valuable possession. You start the game with a limited amount of strength, which is gradually consumed as the game goes on. If it drops to zero, you die, and the game ends. Fights against wild monsters also diminish your strength. Eating food replenishes it. You have to keep an eye on your current "strength rating" throughout the game, and always make sure you have food on hand to replenish your rating when needed.

After entering item 4 on the menu, Andrew is asked how much food he wants to buy, with each unit of food costing $2. After this purchase has been made (Andrew buys 20 units), he enters a 0 next time the menu is presented, indicating that he wishes to continue with the Adventure:
```
YOU HAVE $ 20

YOU CAN BUY 1 - FLAMING TORCH ($15)
            2 - AXE ($10)
            3 - SWORD ($20)
            4 - FOOD ($2 PER UNIT)
            5 - MAGIC AMULET ($30)
            6 - SUIT OF ARMOR ($50)
            0 - TO CONTINUE ADVENTURE
ENTER NO. OF ITEM REQUIRED? 0
```
Another status report is given, showing Andrew's strength and wealth, and the fact that his provisions sack holds 20 units of food. The location is described, and Andrew enters "E" (meaning he wants to move east) when asked what he wants to do next:
```
ANDREW, YOUR STRENGTH IS 90
YOU HAVE $ 20
YOUR PROVISIONS SACK HOLDS 20 UNITS OF FOOD

******************************

YOU ARE AT THE ENTRANCE TO A FORBIDDING-LOOKING STONE
CASTLE.  YOU ARE FACING EAST

WHAT DO YOU WANT TO DO? E
```
He is moved eastwards, and another status report is given. Notice that his strength rating has been reduced slightly:
```
ANDREW, YOUR STRENGTH IS 85
YOU HAVE $ 20
YOUR PROVISIONS SACK HOLDS 20 UNITS OF FOOD

******************************

YOU ARE IN THE HALLWAY
THERE IS A DOOR TO THE SOUTH
THROUGH WINDOWS TO THE NORTH YOU CAN SEE A
SECRET HERB GARDEN
THERE IS TREASURE HERE WORTH $ 100

WHAT DO YOU WANT TO DO? P
```
He sees that there is $100 worth of treasure here, so enters "P" (for "pick up the treasure") and when the status report comes back, the extra wealth has been added:
```
ANDREW, YOUR STRENGTH IS 80
YOU HAVE $ 120
YOUR PROVISIONS SACK HOLDS 20 UNITS OF FOOD

******************************

YOU ARE IN THE HALLWAY
THERE IS A DOOR TO THE SOUTH
THROUGH WINDOWS TO THE NORTH YOU CAN SEE A
SECRET HERB GARDEN

WHAT DO YOU WANT TO DO? S
```
Moving south (he entered "S"), Andrew finds himself in the Audience Chamber. But he is not alone! His first battle is only moments away:
```
THIS IS THE AUDIENCE CHAMBER
THERE IS A WINDOW TO THE WEST. BY LOOKING TO THE
RIGHT
THROUGH IT YOU CAN SEE THE ENTRANCE TO THE CASTLE.
DOORS LEAVE THIS ROOM TO THE NORTH, EAST AND SOUTH

DANGER...THERE IS A MONSTER HERE....

IT IS A DEVASTATING ICE-DRAGON

THE DANGER LEVEL IS 20 !!

WHAT DO YOU WANT TO DO? F
```
Andrew has entered "F" (for "fight") when asked what he wants to do. He could have entered "R" (for "run") if he felt cowardly. However, the computer would not necessarily have accepted this, and may have told him "NO, YOU MUST STAND AND FIGHT". However, Andrew has decided not to try and run, but instead to battle the Ice-Dragon:
```
* * * * * * * * * * * * * * * * * * * *
 - - - - - - - - - - - - - - - - - - -
* * * * * * * * * * * * * * * * * * * *
 - - - - - - - - - - - - - - - - - - -
* * * * * * * * * * * * * * * * * * * *
 - - - - - - - - - - - - - - - - - - -
* * * * * * * * * * * * * * * * * * * *
 - - - - - - - - - - - - - - - - - - -
* * * * * * * * * * * * * * * * * * * *
 - - - - - - - - - - - - - - - - - - -
* * * * * * * * * * * * * * * * * * * *
 - - - - - - - - - - - - - - - - - - -

YOU HAVE NO WEAPONS
YOU MUST FIGHT WITH BARE HANDS

YOU ATTACK

YOU MANAGE TO WOUND IT

THE MONSTER WOUNDS YOU!

YOU ATTACK

YOU MANAGE TO WOUND IT

THE MONSTER WOUNDS YOU!

YOU ATTACK

AND YOU MANAGED TO KILL THE DEVASTATING ICE-DRAGON
```
His strength is failing, so he decides he must eat, so enters a "C" (for "consume"):
```
ANDREW, YOUR STRENGTH IS 65
YOU HAVE $ 120
YOUR PROVISIONS SACK HOLDS 20 UNITS OF FOOD

WHAT DO YOU WANT TO DO? C

YOU HAVE 20 UNITS OF FOOD
HOW MANY DO YOU WANT TO EAT? 15

ANDREW, YOUR STRENGTH IS 125
YOU HAVE $120
YOUR PROVISIONS SACK HOLDS 5 UNITS OF FOOD
And so the Adventure unfolds:

THIS ROOM WAS USED AS THE CASTLE TREASURY IN
BY-GONE YEARS....
THERE ARE NO WINDOWS, JUST EXITS TO THE
NORTH AND TO THE EAST
THERE IS TREASURE HERE WORTH $ 159

WHAT DO YOU WANT TO DO? P

ANDREW, YOUR STRENGTH IS 120
YOU HAVE $ 279
YOUR PROVISIONS SACK HOLDS 5 UNITS OF FOOD

******************************

THERE ARE NO WINDOWS, JUST EXITS TO THE
NORTH AND TO THE EAST

WHAT DO YOU WANT TO DO? E

ANDREW, YOUR STRENGTH IS 115
YOU HAVE $ 279
YOUR PROVISIONS SACK HOLDS 5 UNITS OF FOOD

******************************

THIS IS THE SMALL ROOM OUTSIDE THE CASTLE
LIFT WHICH CAN BE ENTERED BY A DOOR TO THE NORTH
ANOTHER DOOR LEADS TO THE WEST. YOU CAN SEE
THE LAKE THROUGH THE SOUTHERN WINDOWS

WHAT DO YOU WANT TO DO? N

YOU HAVE ENTERED THE LIFT...
 IT SLOWLY DESCENDS...
 ```
These "snapshots" from the game indicate clearly how the game behaves when the program is up and running. I hope you are now straining at the bit to get your first Adventure up and running.
