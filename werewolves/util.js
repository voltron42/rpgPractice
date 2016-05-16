var util = (function(){
  var Dice = function(count, sides, adder) {
    this.roll = function() {
      var sum = adder;
      for (var x = 0; x < count; x++) {
        sum += Math.ceil(Math.random() * sides);
      }
      return sum
    }
  }
  function write(text, elem, logname, shouldAlert) {
    if (!Array.isArray(text)) {
      text = [text]
    }
    logs[logname] = logs[logname].concat(text,"")
    elem.innerHTML = logs[logname].join("<br/>").toUpperCase()
    elem.scrollTop = elem.scrollHeight
    if (shouldAlert) {
      alert(text.join("\n"))
    }
  }
  function initgame(data) {
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
      game:data,
      player:player
    }
  }
  var gameactionsfactory = function(state, io){
    return {
      EAT:function(self) {
        if (self.inventory.FOOD) {
          if (self.inventory.FOOD.count > 0) {
            self.inventory.FOOD.count -= 1
            eval(self.inventory.FOOD.effect)
          } else {
            delete self.inventory.FOOD
            io.out("YOU HAVE NO FOOD TO EAT")
          }
        } else {
          io.out("YOU HAVE NO FOOD TO EAT")
        }
      },
      FIGHT:function(self,target) {
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
              self.strength -= 5
              io.out(self.readout())
              return self.strength <= 0
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
        if (self.strength <= 0) {
          return true
        }
        if (Math.random() <= 0.35) {
          return true;
        }
      },
      RUN:function(self, target) {
        if (Math.random() > .7) {
          io.out("NO, YOU MUST STAND AND FIGHT")
        } else {
          self.room = self.path.pop()
          io.out("YOU RUN BACK TO THE ROOM YOU WERE IN LAST")
          io.out(state.game.rooms[self.room].description)
          return true
        }
      },
      INIT_COMBAT:function(self, target) {
        io.out([
          "DANGER...THERE IS A MONSTER HERE",
          "IT IS A " + target.name,
          "THE DANGER LEVEL IS " + target.danger + "!!!"
        ])
        var concluded = false
        var target = {danger:target.danger,name:target.name}
        Object.keys(self.inventory).filter(function(itemname) {
          var item = self.inventory[itemname]
          return item.combatOnly && (!item.mustEquip || item.equip)
        }).forEach(function(itemname) {
          var item = self.inventory[itemname]
          eval(item.effect)
          target.danger = Math.floor(target.danger)
        })
      },
      CONCLUDE_COMBAT:function(self,target) {
        if (Math.random() * 16 > target.danger) {
          io.out("AND YOU MANAGED TO DEFEAT THE " + target.name)
          self.victories = self.victories || 0
          self.victories++
          delete data.rooms[self.room].contents
        } else {
          io.out("THE " + target.name + " DEFEATED YOU")
          self.strength = Math.floor(self.strength / 2)
          delete state.game.rooms[self.room].contents
        }
      },
      ACQUIRE:function(self,target) {
        io.out("YOU HAVE DISCOVERED " + target + " PIECES OF GOLD")
        self.wealth += contents
        delete state.game.rooms[self.room].contents
        io.out(self.readout())
      },
      MOVE:function(direction) {
        state.player.path.push(state.player.room)
        state.player.room = state.game.rooms[state.player.room].doors[direction]
      },
      PURCHASE:function(itemname) {
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
          io.out(player.readout())
        }
      },
      LIST_EQUIPTMENT:function() {
        var equiptment = {};
        Object.keys(state.player.inventory).filter(function(item) {
          return state.player.inventory[item].mustEquip
        }).forEach(function(item) {
          equiptment[item] = true;
        })
        return equiptment
      },
      EQUIP_SELECTED:function(item) {
        Object.keys(equiptment).filter(function(otherItem) {
          return otherItem != item
        }).forEach(function(otherItem) {
          delete state.player.inventory[otherItem].equip
        })
        state.player.inventory[item].equip = true
      },
      CONCLUDE_GAME:function() {
        if (state.player.strength == 0) {
          io.out("YOU ARE DEAD.....")
        } else {
          io.out(state.game.rooms[player.room].description)
        }
      }
    }
  }
  return {
    initgame:initgame,
    write:write,
    gameactionsfactory:gameactionsfactory
  }
})()
