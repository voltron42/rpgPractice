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
          if (input == null) {
            throw "io escaped"
          }
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
          var turns = {
            you:function() {
              io.out("YOU ATTACK")
              if (Math.random() > .5) {
                io.out("YOU MANAGE TO WOUND IT")
                target.danger = Math.floor(5 * target.danger / 6)
              } else {
                io.out("YOU MISS")
              }
            },
            him:function() {
              io.out("THE " + target.name + " ATTACKS")
              if (Math.random() > .5) {
                io.out("THR MONSTER WOUNDS YOU")
                player.strength -= 5
                io.out(player.readout())
                return player.strength <= 0
              } else {
                io.out("HE MISSED")
              }
            }
          }
          var initiative = []
          if (Math.random() > .5) {
            initiative.push("him")
            initiative.push("you")
          } else {
            initiative.push("him")
            initiative.push("you")
          }
          for (var x = 0; x < initiative.length; x++) {
            if (turns[initiative[x]]()) {
              break;
            }
          }
          if (player.strength <= 0) {
            return true
          }
          if (Math.random() <= 0.35) {
            return true;
          }
        },
        "RUN":function(self,target) {
          if (Math.random() > .7) {
            io.out("NO, YOU MUST STAND AND FIGHT")
          } else {
            player.room = player.path.pop()
            io.out("YOU RUN BACK TO THE ROOM YOU WERE IN LAST")
            io.out(data.rooms[player.room].description)
            return true
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
            return answer
          }
          answer = answer.toUpperCase()
          if (answer in options) {
            return answer
          }
          answer = answer.toLowerCase()
          if (answer in options) {
            return answer
          }
        }
      }
      return {
        play:function() {
          player.name = io.in("WHAT IS YOUR NAME, EXPLORER?")
          io.out(player.readout())
          while (player.room != data.init.exit) {
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
              if (Math.random() * 16 > target.danger) {
                io.out("AND YOU MANAGED TO DEFEAT THE " + target.name)
                player.victories = player.victories || 0
                player.victories++
                delete data.rooms[player.room].contents
              } else {
                io.out("THE " + target.name + " DEFEATED YOU")
                player.strength = Math.floor(player.strength / 2)
                delete data.rooms[player.room].contents
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
