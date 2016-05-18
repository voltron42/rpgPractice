var gamefactory = (function(game){
  var state = util.initgame(game)
  return {
    build:function(out, err, menu, optionfactory) {
      var logs = {
        readout:[],
        errors:[]
      }
      writeout = util.writer(out, logs, "readout")
      writeerr = util.writer(err, logs, "errors")
      var io = {
        in:function(text) {
          writeout(text)
          var input = prompt(text.join("\n"))
          if (input == null) {
            throw "io escaped"
          }
          writeout(input)
          return input;
        },
        out:function(text) {
          writeout(text, true)
        },
        err:function(text) {
          writeerr(text, true)
        }
      }
      var gameactions = util.gameactionsfactory(state, io)
      var travelActions = {
        "MOVE":function(self) {
          var direction = inquireOfUser(
            "WHERE DO YOU WANT TO GO?",
            state.game.rooms[state.player.room].doors
          )
          gameactions.MOVE(direction)
        },
        "BUY":function(self) {
          if (self.wealth <= 0) {
            io.out([
              "YOU DO NOT HAVE ANY GOLD",
              "YOU CANNOT BUY ANYTHING"
            ])
          } else {
            io.out(state.player.readout())
            io.out(state.game.storelisting())
            var itemname = inquireOfUser("WHAT DO YOU WANT TO BUY?", state.game.store)
            gameactions.PURCHASE(itemname)
          }
        },
        "EAT":gameactions.EAT,
        "EQUIP":function(self) {
          state.player.equiptment = gameactions.LIST_EQUIPTMENT();
          if (Object.keys(equiptment).length == 0) {
            io.out("YOU HAVE NOTHING TO EQUIP")
          } else {
            var item = inquireOfUser("WHAT DO YOU WANT TO EQUIP?", equiptment)
            gameactions.EQUIP_SELECTED(equiptment,item)
          }
        },
        "STATUS":gameactions.STATUS
      }
      var combatActions = {
        "FIGHT":gameactions.FIGHT,
        "RUN":gameactions.RUN,
        "EAT":gameactions.EAT
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
      function populateSelector(selector, optionfactory, question, options) {
        var option = optionfactory();
        option.text = question;
        option.value = "";
        selector.add(option);
        Object.keys(options).forEach(function(key) {
          var option = optionfactory();
          option.text = key;
          option.value = key;
          selector.add(option);
        })
        selector.enabled
      }
      function retrieveValue(selector) {
        var selected = selector.value
        if (selected.length > 0) {
          selector.disabled = true
          while (selector.length > 0) {
            selector.remove(0)
          }
          return selected
        }
      }
      var travelEvents = {
        "MOVE":function(self) {
          state.state = "MOVE"
          populateSelector(
            menu,
            optionfactory,
            "WHERE DO YOU WANT TO GO?",
            state.game.rooms[state.player.room].doors
          )
        },
        "BUY":function(self) {
if (self.wealth <= 0) {
            io.out([
              "YOU DO NOT HAVE ANY GOLD",
              "YOU CANNOT BUY ANYTHING"
            ])
delete state.state
events.MAIN()
          } else {
            io.out(state.player.readout())
            io.out(state.game.storelisting())
state.state = "PURCHASE"
            populateSelector("WHAT DO YOU WANT TO BUY?", state.game.store)
        },
        "EAT":gameactions.EAT,
        "EQUIP":function(self) {
state.player.equiptment = gameactions.LIST_EQUIPTMENT();
          if (Object.keys(equiptment).length == 0) {
            io.out("YOU HAVE NOTHING TO EQUIP")
          } else {
            populateSelector("WHAT DO YOU WANT TO EQUIP?", equiptment)
            state.state = "EQUIP"
          }
        },
        "STATUS":gameactions.STATUS
      }
      var events = {
        INIT:function() {
          io.out(state.player.readout())
          events.MAIN()
        },
        MAIN:function() {
          io.out(state.game.rooms[state.player.room].description)
          if (state.player.strength == 0) {
            io.out("YOU ARE DEAD.....")
          } else if (state.player.room != state.game.init.exit) {
            var contents = state.game.rooms[state.player.room].contents
            if (contents < 0) {
              var monster = state.game.monsters[contents]
              var target = gameactions.INIT_COMBAT(state.player, monster)
              state.state = "COMBAT"
              populateSelector(menu, optionfactory, "WHAT DO YOU WANT TO DO?", combatActions)
            } else {
              if (contents > 0) {
                gameactions.ACQUIRE(state.player, contents)
              }
              state.state = "TRAVEL"
              populateSelector(menu, optionfactory, "WHAT DO YOU WANT TO DO?", travelEvents)
            }
          }
        },
        TRAVEL:function(opt) {
          travelEvents[opt](state.player)
        },
        MOVE:function(opt) {
          delete state.state
          gameactions.MOVE(direction)
          events.MAIN()
        },
        COMBAT:function(opt) {

        },
        PURCHASE:function(opt) {
          delete state.state
          gameactions.PURCHASE(opt)
          events.MAIN()
        },
        EQUIP:function(opt) {
          delete state.state
          gameactions.EQUIP_SELECTED(state.player.equiptment,item)
          delete state.player.equiptment
        }
      };
      return {
        play:function() {
          io.out(state.player.readout())
          while (state.player.room != state.game.init.exit) {
            io.out(state.game.rooms[state.player.room].description)
            var contents = state.game.rooms[state.player.room].contents
            if (contents > 0) {
              gameactions.ACQUIRE(state.player, contents)
            } else if (contents < 0) {
              var monster = state.game.monsters[contents]
              var target = gameactions.INIT_COMBAT(state.player, monster)
              var concluded = false
              var runaway = false
              while (!concluded) {
                try {
                  var combatAction = inquireOfUser("WHAT DO YOU WANT TO DO?", combatActions)
                  concluded = combatActions[combatAction](state.player, target)
                } catch (e) {
                  if (e == "RUN") {
                    runaway = true
                    break;
                  }
                  throw e;
                }
              }
              if (!runaway) {
                gameactions.CONCLUDE_COMBAT(state.player, monster)
              }
              if (state.player.strength == 0) {
                break;
              }
            }
            var action = inquireOfUser("WHAT DO YOU WANT TO DO?", travelActions)
            travelActions[action](state.player)
          }
          gameactions.CONCLUDE_GAME()
        },
        resolve:function() {
          var value = retrieveValue(menu);
          if (value) {
            events[state.state](menu.value)
          }
        }
      }
    }
  }
})(were)
