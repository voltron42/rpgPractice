var gamefactory = (function(game){
  var state = util.initgame(game)
  return {
    build:function(out, err, menu) {
      var logs = {
        readout:[],
        errors:[]
      }
      var io = {
        in:function(text) {
          util.write(text, out, "readout", true)
          var input = prompt(text.join("\n"))
          if (input == null) {
            throw "io escaped"
          }
          util.write(input, out, "readout")
          return input;
        },
        out:function(text) {
          util.write(text, out, "readout", true)
        },
        err:function(text) {
          util.write(text, err, "errors", true)
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
          if (player.wealth <= 0) {
            io.out([
              "YOU DO NOT HAVE ANY GOLD",
              "YOU CANNOT BUY ANYTHING"
            ])
          } else {
            var itemname = inquireOfUser("WHAT DO YOU WANT TO BUY?", store)
            gameactions.PURCHASE(itemname)
          }
        },
        "EAT":gameactions.EAT,
        "EQUIP":function(self) {
          var equiptment = gameactions.LIST_EQUIPTMENT();
          var item = inquireOfUser("WHAT DO YOU WANT TO EQUIP?", equiptment)
          gameactions.EQUIP_SELECTED(item)
        }
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
              gameactions.INIT_COMBAT(state.player, monster)
              while (!concluded) {
                var combatAction = inquireOfUser("WHAT DO YOU WANT TO DO?", combatActions)
                concluded = combatActions[combatAction](state.player, target)
              }
              gameactions.CONCLUDE_COMBAT(state.player, monster)
              if (state.player.strength == 0) {
                break;
              }
            }
            var action = inquireOfUser("WHAT DO YOU WANT TO DO?", travelActions)
            travelActions[action]()
          }
          gameactions.CONCLUDE_GAME()
        },
        resolve:function() {
          menu.
        }
      }
    }
  }
})(were)
