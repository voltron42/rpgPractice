var gamefactory = (function(){
  var Dice = function(count, sides, adder) {
    this.roll = function() {
      var sum = adder;
      for (var x = 0; x < count; x++) {
        sum += Math.ceil(Math.random() * sides);
      }
      return sum
    }
  }
  var roomset = {};
  Object.keys(data.rooms).forEach(function(key){
    if (data.rooms[key].treasure) {
      var dice = eval(data.rooms[key].treasure)
      data.rooms[key].contents = dice.roll();
    }
    if (!(data.rooms[key].contents)) {
      roomset[key] = true
    }
  })
  delete roomset[data.init.enter]
  delete roomset[data.init.exit]
  rooms = Object.keys(roomset)
  var store = {}
  data.store.forEach(function(item) {
    store[item.name] = item
  })
  data.store = store
  Object.keys(data.monsters).forEach(function(key){
    rooms;
    var index = Math.floor(Math.random() * rooms.length)
    var room = rooms.splice(index, 1)
    data.rooms[room].contents = key
  })
  for (var x = 0; x<data.init.treasure.count; x++) {
    var index = Math.floor(Math.random() * rooms.length)
    var room = rooms.splice(index, 1)
    var dice = eval(data.init.treasure.treasure)
    var roll = dice.roll();
    data.rooms[room].contents = roll;
  }
  var player = {}
  Object.keys(data.init.stats).forEach(function(key){
    player[key] = data.init.stats[key]
  })
  player.room = data.init.enter;
  player.path = [];
  player.inventory = {}
  player.readout = function() {
    var readout = [];
    Object.keys(data.init.stats).forEach(function(key){
      readout.push(key + ": " + player[key])
    })
    if (Object.keys(player.inventory).length > 0) {
      readout.push("You Have:")
      Object.keys(player.inventory).forEach(function(key){
        var count = (player.inventory[key].count > 1)?count:""
        var equipt = (player.inventory[key].mustEquip)?((player.inventory[key].equip)?"EQUIPT":"NOT EQUIPT"):""
        var line = [key,count,equipt]
        readout.push(line.join("; "))
      })
    }
    return readout;
  }
  return {
    build:function(out, err) {
      var readout = []
      var errors = []
      var io = {
        in:function(text) {
          if (!Array.isArray(text)) {
            text = [text]
          }
          readout = readout.concat(text)
          var input = prompt(text.join("\n"))
          readout.push(input)
          out.innerHTML = readout.join("<br/>").toUpperCase()
          out.scrollTop = out.scrollHeight
          return input;
        },
        out:function(text) {
          if (!Array.isArray(text)) {
            text = [text]
          }
          readout = readout.concat(text,"")
          out.innerHTML = readout.join("<br/>").toUpperCase()
          out.scrollTop = out.scrollHeight
          alert(text.join("\n"))
        },
        err:function(text) {
          if (!Array.isArray(text)) {
            text = [text]
          }
          errors = errors.concat(text,"")
          err.innerHTML = errors.join("<br/>").toUpperCase()
          err.scrollTop = err.scrollHeight
          alert(text.join("\n"))
        }
      }
      var eat = function(self) {
        if (player.inventory.FOOD) {
          if (player.inventory.FOOD.count > 0) {
            player.inventory.FOOD.count -= 1
            eval(player.inventory.FOOD.effect)
          } else {
            delete player.inventory.FOOD
            io.out("YOU HAVE NO FOOD TO EAT")
          }
        } else {
          io.out("YOU HAVE NO FOOD TO EAT")
        }
      }
      var travelActions = {
        "MOVE":function(self) {
          var direction = inquireOfUser(
            "WHERE DO YOU WANT TO GO?",
            data.rooms[player.room].doors
          )
          player.path.push(player.room)
          player.room = data.rooms[player.room].doors[direction]
        },
        "BUY":function(self) {
          if (player.wealth <= 0) {
            io.out([
              "YOU DO NOT HAVE ANY GOLD",
              "YOU CANNOT BUY ANYTHING"
            ])
          } else {
            var itemname = inquireOfUser("WHAT DO YOU WANT TO BUY?", store)
            var storeitem = store[itemname]
            var playeritem = player.inventory[itemname]
            if (playeritem) {
              if (playeritem.limit && playeritem.count == playeritem.limit) {
                io.out("YOU CANNOT PURCHASE ANY MORE OF THIS ITEM")
              } else {
                player.wealth -= playeritem.cost
                playeritem.count++
              }
            } else {
              playeritem = {}
              Object.keys(storeitem).forEach(function(key) {
                playeritem[key] = storeitem[key]
              })
              playeritem.count = 1
              player.inventory[itemname] = playeritem
            }
          }
        },
        "EAT":eat,
        "EQUIP":function(self) {
          var equiptment = {};
          Object.keys(player.inventory).filter(function(item) {
            return player.inventory[item].mustEquip
          }).forEach(function(item) {
            equiptment[item] = true;
          })
          var item = inquireOfUser("WHAT DO YOU WANT TO EQUIP?", equiptment)
          Object.keys(equiptment).filter(function(otherItem) {
            return otherItem != item
          }).forEach(function(otherItem) {
            delete player.inventory[otherItem].equip
          })
          player.inventory[item].equip = true
        }
      }
      var combatActions = {
        "FIGHT":function(self,target) {
          /*
860 REM *************************
870 REM THE BATTLE
880 PRINT:PRINT
890 IF RND(1)>.5 THEN PRINT M$;" ATTACKS" ELSE
PRINT "YOU ATTACK"
900 GOSUB 3520
910 IF RND(1)>.5 THEN PRINT:PRINT "YOU MANAGE TO
WOUND IT":FF=INT(5*FF/6)
920 GOSUB 3520
930 IF RND(1)>.5 THEN PRINT:PRINT "THE MONSTER
WOUNDS YOU!":STRENGTH=STRENGTH-5
940 IF RND(1)>.35 THEN 890
950 IF RND(1)*16>FF THEN PRINT:PRINT "AND YOU
MANAGED TO KILL THE ";M$:MK=MK+1:GOTO 970
960 PRINT:PRINT "THE ";M$;" DEFEATED
YOU":STRENGTH=INT(STRENGTH/2)
970 A(RO,7)=0:GOSUB 3410:PRINT:PRINT:GOSUB
3520:RETURN
980 REM ******************
          */
          // TODO
        },
        "RUN":function(self,target) {
          if (Math.random() > .7) {
            io.out("NO, YOU MUST STAND AND FIGHT")
          } else {
            // TODO
          }
        },
        "EAT":eat
      }
      function inquireOfUser(question, options) {
        var list = "(" + Object.keys(options).join(",").toUpperCase() + ")"
        message = [question, list];
        while (true) {
          var answer = io.in(message)
          if (answer in options) {
            break
          }
        }
        return answer
      }
      return {
        play:function() {
          player.name = io.in("WHAT IS YOUR NAME, EXPLORER?")
          io.out(player.readout())
          while (player.room != data.init.exit) {
            window.scrollTo(0,document.body.scrollHeight);
            io.out(data.rooms[player.room].description)
            var contents = data.rooms[player.room].contents
            if (contents > 0) {
              io.out("YOU HAVE DISCOVERED " + contents + " PIECES OF GOLD")
              player.wealth += contents
              delete data.rooms[player.room].contents
              io.out(player.readout())
            } else if (contents < 0) {
              var monster = data.monsters[contents]
              io.out([
              	 "DANGER...THERE IS A MONSTER HERE",
              	 "IT IS A " + monster.name,
              	 "THE DANGER LEVEL IS " + monster.danger + "!!!"
              ])
              var concluded = false
              var target = {danger:monster.danger,name:monster.name}
              Object.keys(player.inventory).filter(function(itemname) {
                var item = player.inventory[itemname]
                return item.combatOnly && (!item.mustEquip || item.equip)
              }).forEach(function(itemname) {
                var item = player.inventory[itemname]
                eval(item.effect)
                target.danger = Math.floor(target.danger)
              })
              while (!concluded) {
                var combatAction = inquireOfUser("WHAT DO YOU WANT TO DO?", combatActions)
                concluded = combatActions[combatAction](player, target)
              }
              if (player.strength == 0) {
                break;
              }
            }
            var action = inquireOfUser("WHAT DO YOU WANT TO DO?", travelActions)
            travelActions[action]()
          }
          if (player.strength == 0) {
            io.out("YOU ARE DEAD.....")
          } else {
            io.out(data.rooms[player.room].description)
          }
        }
      }
    }
  }
})()