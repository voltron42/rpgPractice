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
          out.innerHTML += text + "<br/>"
          var input = prompt(text)
          out.innerHTML += input + "<br/>"
          return input;
        },
        out:function(text) {
          out.innerHTML += text + "<br/>"
          alert(text)
        },
        err:function(text) {
          err.innerHTML += text + "<br/>"
          alert(text)
        }
      }
      return {
        play:function() {
          console.log(data);
          player.name = io.in("WHAT IS YOUR NAME, EXPLORER?")
          console.log(player);
        }
      }
    }
  }
})()
