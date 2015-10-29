16 TAKING IT TO THE SPACE LANES
======
You now have one or two working Adventure programs. What you also have, although you may not have realized it, is a shell or framework within which you can create your own Adventure.

Rather than having to go to the trouble of starting completely from scratch, you can simply modify the program to create a totally original Adventure program of your own. The DATA statements which define the map, and the PRINT statements which describe the rooms, are the main areas which must be changed.

However, as I'm sure you are pleased to learn, the rest of the program, which makes up the mechanism which keeps the Adventure moving, can be left more or less the same.

The New Scenario
-----
To demonstrate just how very effective this "creation/modificaton" can be, I decided to take the first (not the elaborated version) WEREWOLVES AND WANDERER, and turn it into an Adventure out in space. The scenario is as follows. The intergalactic liner, The Isaac Asimov, met with an unexplained disaster when on its way, with over 1,000 passengers, to the terraformed planets of the Seuxarian System.

When out on your regular patrol of the space lanes, two centuries later, you come across the drifting hulk of The Isaac Asimov. You decide to explore, to see if you can determine what went wrong. However, no sooner have you entered the hold of the wrecked ship than your own ship, which you left tethered outside, explodes. You must find a Safety-Pod on board the Asimov, or die.

In this Adventure, you explore the wrecked ship, fight off androids and aliens, and face a few other problems which I will explain to you after you've played the game.

I will not let you see the map at this stage (and I hope you won't peek at it further on in the book), because by now you should be quite adept at deducing the map upon which the game is based.

There are several tricks and traps in this new Adventure, which make it relatively hard to solve, but you are sure to enjoy having a bigger problem to sink your teeth into.

Sample Run
-----
Here's part of a sample run of The Aftermath of the Asimovian Disaster:
```
WHAT IS YOUR NAME, SPACE HERO? ANDREW

CAPTAIN ANDREW, YOUR STRENGTH IS 101
YOU HAVE $ 59 IN SOLARIAN CREDITS
YOUR RESERVE TANKS HOLD 10 UNITS OF OXYGEN
IT IS TOO DARK TO SEE ANYTHING
THERE IS TREASURE HERE WORTH $ 75


WHAT DO YOU WANT TO DO? I

A SUPPLY ANDROID HAS ARRIVED

YOU HAVE $ 59 IN SOLARIAN CREDITS

YOU CAN BUY 1 - NUCLEONIC LIGHT ($15)
            2 - ION GUN ($10)
            3 - LASER ($20)
            4 - OXYGEN ($2 PER UNIT)
            5 - MATTER TRANSPORTER ($30)
            6 - COMBAT SUIT ($50)
            0 - TO CONTINUE EXPLORATION
ENTER NO. OF ITEM REQUIRED? 1


CAPTAIN ANDREW, YOUR STRENGTH IS 96
YOU HAVE $ 24 IN SOLARIAN CREDITS
YOUR RESERVE TANKS HOLD 10 UNITS OF OXYGEN
YOU ARE CARRYING A LASER


******************************


YOU ARE IN THE WRECKED HOLD OF A SPACESHIP
THE CAVERNOUS INTERIOR IS LITTERED WITH
FLOATING WRECKAGE, AS IF FROM SOME
TERRIBLE EXPLOSION EONS AGO......
THERE IS TREASURE HERE WORTH $ 75


******************************


YOU ARE IN THE CREW'S SLEEPING OUARTERS
MOST OF THE SLEEPING SHELLS ARE EMPTY
THE FEW REMAINING CREW STIR FITFULLY
IN THEIR ENDLESS, DREAMLESS SLEEP


DANGER...THERE IS DANGER HERE....

IT IS A SNIGGERING GREEN ALIEN

YOUR PERSONAL DANGER METER REGISTERS 20 !!


WHAT DO YOU WANT TO DO? F

*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*
*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*
*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*
*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*
*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*
*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*

YOU MUST FIGHT WITH YOUR LASER


SNIGGERING GREEN ALIEN ATTACKS

THE SNIGGERING GREEN ALIEN WOUNDS YOU!
YOU ATTACK

THE SNIGGERING GREEN ALIEN WOUNDS YOU!
SNIGGERING GREEN ALIEN ATTACKS

THE SNIGGERING GREEN ALIEN WOUNDS YOU!
YOU ATTACK

THE SNIGGERING GREEN ALIEN SERIOUSLY WOUNDS YOU

THIS IS THE SHIP'S HOSPITAL, WHITE AND STERILE.
A BUZZING SOUND, AND A STRANGE WARMTH COME FROM
THE SOUTH, WHILE A CHILL IS FELT TO THE NORTH
```
I'm sure you can see quite clearly how this program is related to WEREWOLVES AND WANDERER. However, despite its obvious derivation from the first program, you'll see that it provides a completely different set of challenges to the player, and evokes a whole new area of mental images.

The complete listing of the AFTERMATH OF THE ASIMOVIAN DISASTER is presented in the next chapter.
