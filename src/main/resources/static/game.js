$(function(){
    var rows = 11;
    var columns = 11;
    var $row = $("<div />", {
        class: 'row'
    });
    var $square = $("<div />", {
        class: 'square'
    });

    var letras = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];
    var numeros = [];
    var letrasToCoord = {
      "A": 1,
      "B": 2,
      "C": 3,
      "D": 4,
      "E": 5,
      "F": 6,
      "G": 7,
      "H": 8,
      "I": 9,
      "J": 10
    }

    /* for (var i = 0; i < letras.length; i++) {
      var obj = {};
      obj[letras[i]] = i;
      letrasToCoord.push(obj);
    }*/

    for (var i = 1; i < 11; i++) {
      numeros.push(i);
    }

    var tablero = [];

    //add columns to the the temp row object
    for (var i = 0; i < columns; i++) {
        var currentRow = $row.clone();
        if(i > 0){
            tablero.push([]);
        }
        $("#leGrid").append(currentRow);
        for (var j = 0; j < rows; j++) {
            var currentSquare = $square.clone().attr("id", letras[j-1]+(i));
            currentRow.append(currentSquare);
            if(i > 0 && j > 0){
                tablero[i-1].push(currentSquare);
            }
            if(i === 0 || j === 0){
                currentSquare.addClass("vacio");
            }
            if(i === 0 && j > 0){
                currentSquare.text(letras[j-1]);
            }
            if(i > 0 && j === 0){
                currentSquare.text(numeros[i-1]);
            }
        }
    }
    //tablero[0][2].text("barco");

    function grabTheID(){
        var URL = location.search;
        paramObj(URL);
        console.log(URL);
    }

    function paramObj(search) {
      var obj = {};
      var reg = /(?:[?&]([^?&#=]+)(?:=([^&#]*))?)(?:#.*)?/g;

      search.replace(reg, function(match, param, val) {
        obj[decodeURIComponent(param)] = val === undefined ? "" : decodeURIComponent(val);
      });
      obj = obj.gp;
      damnJSON(obj);
      console.log(obj);
    }

   function damnJSON(jason){
        $.getJSON("http://localhost:8080/api/game_view/" + jason, function(data) {
        console.log(data);
        var shipsLoc = data.ships;
        console.log(shipsLoc);
        for(i = 0; i < shipsLoc.length; i++){
            var allBoats = shipsLoc[i].locations;
            for(j = 0; j < allBoats.length; j++){
                var currentBoat = allBoats[j];
                createBoat(currentBoat);
            }
        }
        fillVS(data);
        })
   }

   grabTheID();

    function createBoat(loc){
        $('#' + loc).css("background-color", "black");
    }

    function fillVS(data){
        var caja = $("#vs");
        var box1 = $("<h3/>");
        var box2 = $("<h3/>");
        var pl1Name = data.gamePlayers[0].player.email;
        var pl2Name = data.gamePlayers[1].player.email;

        box1.append(pl1Name);
        box2.append(pl2Name);

        caja.append(box1);
        caja.append(box2);

        if(data.gamePlayers["0"].player.id % 2 != 0){
            box1.append("(Viewer)");
        }
        if(data.gamePlayers["0"].player.id % 2 == 0){
            box2.append("(Viewer)");
        }

    }



})

