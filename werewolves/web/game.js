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
  return {
    build:function(out, err) {
      var io = {
        in:function(text) {
          if (Array.isArray(text)) {
            text = text.join("<br/>")
          }
          out.innerHTML += text + "<br/><br/>"
          var input = prompt(text.split("<br/>").join("\n"))
          out.innerHTML += input + "<br/><br/>"
          out.scrollTop = out.scrollHeight
          return input;
        },
        out:function(text) {
          if (Array.isArray(text)) {
            text = text.join("<br/>")
          }
          out.innerHTML += text + "<br/><br/>"
          out.scrollTop = out.scrollHeight
          alert(text.split("<br/>").join("\n"))
        },
        err:function(text) {
          if (Array.isArray(text)) {
            text = text.join("<br/>")
          }
          err.innerHTML += text + "<br/><br/>"
          err.scrollTop = err.scrollHeight
          alert(text.split("<br/>").join("\n"))
        }
      }
      return {
        play:function() {
          console.log(data);
          player.name = io.in("WHAT IS YOUR NAME, EXPLORER?")
          console.log(player);
          while (player.room != data.init.exit) {
            window.scrollTo(0,document.body.scrollHeight);
            io.out(data.rooms[player.room].description)
            var list = "(" + Object.keys(data.rooms[player.room].doors).join(",").toUpperCase() + ")"
            var message = "WHERE DO YOU WANT TO GO? " + list
            console.log(message);
            while (true) {
              var direction = io.in(message)
              direction = direction.toLowerCase()
              if (direction in data.rooms[player.room].doors) {
                player.path.push(player.room)
                player.room = data.rooms[player.room].doors[direction]
                break
              }
            }
          }
          io.out(data.rooms[player.room].description)
        }
      }
    }
  }
})()
