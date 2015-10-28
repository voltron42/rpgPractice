1
GIVE ME MY BOW
Let's face it. Life can be pretty tame, sometimes. There don't seem to be many dragons waiting to be slain in my city, and chests heaped high with abandoned gold are in scarce supply. I can't remember the last time I met an Evil Magician down at the local supermarket, and it's been ages since I discussed battle tactics with sentient androids at the local tavern.

The hunger for excitement lies in all of us. The desire to take on the personalities of other, more vibrant, people-even for just an hour or so-is a common one. Although you can't conjure up devils and werebears, envoke the power of a Shield of Protection, or employ trolls to carry sacks of emeralds from the ruins of an abandoned castle, role-playing games allow you to do just that.

Adventure gaming has hit the big time. You've probably seen the claims that it is the "fastest growing game in the world." Whether that's true or not, it indicates that Adventure gaming is a leisure pursuit which satisfies the inner needs of many people.

You may well have taken part in Adventure role-playing games yourself.

But these real-life campaigns have one enormous disadvantage. You need people to play with and against. You need a referee (often called the Game Master, or Dungeon Master) to control the world and its artifacts and encounters. It is not always particularly easy to get all these other human beings together just when you decide you'd like to indulge in a little bit of Adventuring.

That's where the computer comes into its own.

Although computer Adventure games lack a little of the spontaneity of games played with live company, they can be remarkably unpredictable and exciting to play. The fact that the Hydra of 10 Heads you've just slain exists only within your computer's RAM seems in no way to diminish the relief you feel when it dies. The gems you find lying all over the place are no less "real" than those discovered in live-action Adventures.

How to Read This Book
I've written this book to show you just how easy it is to create computer Adventure games of your own. However, there is one problem, and I hope you'll be willing to work with me to solve this problem. It is pretty difficult to know where to begin explaining how a computer Adventure is structured. Many times I've discovered that understanding one particular programming concept depends on your already understanding a second, separate concept. I've done all I can to make sure that the introduction of these concepts follows a more or less logical order and that all new concepts are carefully explained. Unfortunately, because of the complexity of most Adventure game programs, from time to time this has been impossible. All I can do is ask you to proceed on trust. Explanations which are not blindingly clear the first time you read them should swiftly fall into place as you continue working through the book.

I have written this work with the ancient Chinese maxim-A PROGRAM IS WORTH A THOUSAND WORDS-always in mind. You can learn far more by entering a program, or program fragment, and then running it, than you can from chapter after chapter of explanation. Therefore, this book is program-oriented. It contains four major programs (plus variations), and the instruction part of the book is based on these programs. In fact, if you just want some Adventure programs to run, you can just enter and run the programs as they are, ignoring the lucid explanations which surround them.

However, as is obvious, if YOU do this you'll miss the whole point of the book. Proceed slowly, have your computer runnmg when you read, and enter each piece of code as you come to it, and you'll discover that in a very short while from now, you'll be creating Adventure programs of your own.

We'll begin with two quick looks at Adventure games in progress. Chapter 2 contains a version of the game WEREWOLVES AND WANDERER that you play by flipping two coins. If you haven't played an Adventure game before, this will give you a good idea of what to expect. Chapter 3 shows brief "snapshots" from the computerized version of WEREWOLVES AND WANDERER in action.

As I mentioned before, one of the most satisfying aspects of computer Adventuring, and therefore one of the most critical parts of Ad venture programs, is the design or discovery of the computer-stored "map" of the environment that the Adventure takes place in. Chapter 4 shows how your computer can keep track of a floor plan for a deserted castle, or a dark dungeon, or whatever environment you choose.

In the next nine chapters (chapters 5 through 13) we begin our step-by-step construction of WEREWOLVES AND WANDERER. From this point on in the book you should enter the lines of the programs as they are given. This will teach you a great deal about how an Adventure program is written.

Chapters 14 and 15 show a more elaborate version of this program, creating a less predictable (and therefore more exciting) Adventure. You will discover a number of key ideas you can use to add interest to your own programs.

Next we will turn our original WEREWOLVES AND WANDERER program into a totally different Adventure, THE ASIMOVIAN DISASTER. Both versions of WEREWOLVES AND WANDERER take place within a deserted castle. THE ASIMOVIAN DISASTER takes place in outer space, where you (playing the part of an intrepid space explorer) have come upon the wreck of the giant space liner, The Isaac Asimov. You become trapped within the wreckage, and while avoiding crazed androids and unfriendly aliens, have to work your way to the Life Pod launching area, to get aboard the final Life Pod and blast your way to safety. This will serve as an excellent illustration of how a basic Adventure program can easily be adapted to simulate the environment and situation of your choice.

Finally, we'll present two completely different Adventure programs: THE CITADEL OF PERSHU and CHATEAU GAILLARD. These programs introduce more sophisticated Adventure programming techniques.

Adapting the Programs for Your Computer
I wrote the programs in this book on an IBM PC, but since many of you own or use different kinds of microcomputers, I've deliberately used only those parts of BASIC that will run on most microcomputers without changes. (The listings in this book are also fully compatible with Microsoft BASIC and MSX BASIC.)

You'll find no PEEKs and POKEs, no use of graphic character sets, and no use of such commands as SOUND or PLAY. I've assumed you have access to READ and DATA, and that your screen is around 40 characters wide (and you'll find, if you have a computer like the VIC-80 with a screen which is not that wide, it is fairly easy to adapt the program output to fit, as much of it consists of PRINT statements which can be abbreviated to comply with your computer's needs).

Of course, you'll probably have to play with the display a little, in order to make it as effective as possible. I expect, by the way, that you may well want to modify and adapt the programs to make the most of your system, adding sound and color, plus your own system's graphics, wherever you can.

Some of the BASIC statements used in the programs may have to be changed slightly to run on your system.

For example, if I want to generate random integers in the range, say, 1 to 10, I use a command of the type A = INT (RND(1)*10) + 1. When I want a number less than one, I've sometimes used just A = RND. If your system doesn't use this kind of random number statement you may have to substitute either A = RND(10) or A = INT (RND(0)*10) + 1 or A = INT (RND(1)*10) + 1 (you may well need to precede this with LET). You probably know exactly how to do it right now, but if you're not sure, look up RND or "random numbers" in your system's manual.

Although much of the output within quotation marks in PRINT statements is in lower-case, all programs accept input in upper-case letters. If your system does not have lower-case letters, simply put the material in PRINT statements in upper-case. It's been put in lower-case just because I think it looks better, but it has nothing to do with the actual running of the program.

Most computers allow line lengths of 255 characters or more. Others, such as the Commodore 64 and the VIC 20, have a limit (four lines for the 64, two lines for the 20). If your computer has a limit, and using abbreviations for keywords will not get the line in, you can usually split the line at any colon. If, however, the long lines test a number of conditions, you should use some of the IF/THEN tests in the first line to jump over the second set of IF/THENs (in an additional second line) with a GOTO if they are not met.

Note that some BASICs, such as Atari BASIC, do not support a string within an INPUT statement. Replace this with a PRINT "string" followed by a separate INPUT.

Some BASICs do not READ array values directly, as in READ A(7) or READ A$(7). Replace these with lines in which the data read is set equal to a variable, which is then made equal to the array element, as in READ X:A(7) = X or READ X$:A$(7)= X$.

If your computer does not support IF/THEN/ELSE, split it into two separate IF/THEN statements. For CLS (to clear the screen) use PRINT "CLR" on the Commodore machines and Atari computers, HOME on Apples, and CALL CLEAR when working with TI Extended BASIC. If you do not have INKEY$ on your computer, use either INPUT A$ or GET A$ (except in TI Extended BASIC, where you can use CALL KEY).

String-handling can cause a few problems, so I've deliberately kept it to a minimum in the programs in this book. If your computer does not support the standard string-handling commands of LEFT$, MID$ and RIGHT$, consult your manual. In TI Extended BASIC SEG$(A$,2,3) replaces MID$(A$(2,3)), while A$(2 TO 5) should be used on Timex/Sinclair computers, and A$(2,5) when working in Atari BASIC.

Don't be intimidated by the length of this series of instructions. I've included these notes just to be sure that you can get the programs up and running as quickly as possible.

As you can see, certain microcomputers require special forms of the BASIC commands used in the programs in this book. The changes I've described above should cover most of the adaptations you'll have to make to get the programs running on your computer. You'll probably find, in fact, that when you get to entering the programs you'll automatically make the small changes needed to accommodate the special features of your system.

Please be sure to type the programs in carefully. Remember that the instructions you give to a computer must be exactly correct-or the machine won't be able to run your program. If you have trouble getting a program to run, first proofread your typed-in version against the listing given in this book.

Adapting or improving a program can be an excellent education in the mechanics of BASIC programming. The manual for the BASIC that comes with your computer is an indispensible tool. Check the manual to see what's wrong with any program lines that won't work-or that result in error messages. Make sure to use the exact form of the commands as given in the manual.

Talking to someone who has a lot of programming experience with the computer you use can be a tremendous help in solving any problems you might encounter. One of the wide range of books that explain introductory BASIC programming-including several devoted exclusively to the differences between the BASICs that work on different microcomputers-could also help.

It is impossible to predict exactly how much memory the programs will take on your system, because of the different ways memory is organized, and the working space the program requires differs from system to system.

The longer programs, especially THE CITADEL OF PERSHU and CHATEAU GAILLARD, take up a fair amount of memory: almost 20K for the first, and close to 25K for the second. If memory is in short supply on your system, see Chapter 19 for some hints on how to "compress" the amount of memory the programs require.
