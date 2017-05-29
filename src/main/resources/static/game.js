$(function(){

    var nn = grabTheID();
    var nnn = paramObj(nn);
    $.getJSON("http://localhost:8080/api/game_view/" + nnn, function(data) {
        arr = data;

        createGrid("shipGrid", "");
        createGrid("salvoGrid", "alt");
        getBoats(arr, nnn);
        salvoData(nnn, arr);
    })


})

function paramObj(search) {
    var obj = {};
    var reg = /(?:[?&]([^?&#=]+)(?:=([^&#]*))?)(?:#.*)?/g;

    search.replace(reg, function(match, param, val) {
        obj[decodeURIComponent(param)] = val === undefined ? "" : decodeURIComponent(val);
    });
    obj = obj.gp;
    return obj;
}
    function grabTheID(){
        var URL = location.search;
        return URL;
    }



function createGrid(spot, nombre){
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

        $('#' + spot ).append(currentRow);
        for (var j = 0; j < rows; j++) {
                var currentSquare = $square.clone().attr("id", nombre + letras[j-1]+(i));
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
}

function salvoData(x, data){

    console.log(arr);
    if(arr.salvoes["0"]["0"].player  == x){
        for(j = 0; j < arr.salvoes["0"].length; j++){
            for(var i = 0; i < arr.salvoes["0"][j].locations.length; i++){
                var turnNr = arr.salvoes["0"][j].turn;
                var oppSalvos = arr.salvoes["1"][j].locations[i];
                printSalvo(arr.salvoes["0"][j].locations[i], turnNr);
                if($("#"+oppSalvos).hasClass("ship")){
                    $("#"+oppSalvos).attr('class', 'square hit');
                }
                else{
                    $("#"+oppSalvos).attr('class', 'square salvo');
                }
            }
        }
    }
    else{
        var test2 = arr.salvoes["1"]["0"].locations;
        for(j = 0; j < arr.salvoes["1"].length; j++){
            for(i = 0; i < arr.salvoes["1"][j].locations.length; i++){
                var turnNr = arr.salvoes["1"][j].turn;
                var oppSalvos = arr.salvoes["0"][j].locations[i];
                printSalvo(arr.salvoes["1"][j].locations[i], turnNr);
                if($("#"+oppSalvos).hasClass("ship")){
                    $("#"+oppSalvos).attr('class', 'square hit');
                }
                else{
                    $("#"+oppSalvos).attr('class', 'square salvo');
                }
            }
        }
    }
}

function printSalvo(square, turnNr){
    $('#alt' + square).addClass("salvo");
    $('#alt' + square).text(turnNr);
}

function printHit(square){
    $('#alt' + square).addClass("hit");
}

function getBoats(jason, nnn){
    var shipsLoc = arr.ships;
    for(i = 0; i < shipsLoc.length; i++){
        var allBoats = shipsLoc[i].locations;
        for(j = 0; j < allBoats.length; j++){
            var currentBoat = allBoats[j];
            createBoat(currentBoat);
        }
    }
    fillVS(nnn);
}
function createBoat(loc){
    $('#' + loc).addClass("ship");
}

function fillVS(x){
    var caja = $("#vs");
    var box1 = $("<h3/>");
    var box2 = $("<h3/>");
    var pl1Name = arr.gamePlayers[0].player.email;
    var pl2Name = arr.gamePlayers[1].player.email;

    box1.append(pl1Name);
    box2.append(pl2Name);

    caja.append(box1);
    caja.append(box2);

    if(arr.gamePlayers["0"].id == x){
        box1.append("(Viewer)");
    }
    else{
        box2.append("(Viewer)");
    }
}
