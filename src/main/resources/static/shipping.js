$(function() {
    createGrid();

    $('.table tbody tr').click(function(event) {
      if (event.target.type !== 'radio') {
        $(':radio', this).trigger('click');
      }
    });

    $("#shipsP").click(function(){
        function postShip(array){

            $.post({
              url: "api/games/players/gpid/ships",
              data: JSON.stringify(array),//This would be a constr example{ "shipType": "destroyer", "location": ["A1", "B1", "C1" }
              contentType: "application/json"
            })
            .done(function (response, status, jqXHR) {
              alert( "Ships added: " + response );
            })
            .fail(function (jqXHR, status, httpError) {
              alert("Failed to add ships: " + textStatus + " " + httpError);
            })
        }
    })

    //Cuando el cursor entra en el square
  /*  $(".square").hover(function(){
        $(this).css("background-color", "pink");
        var $id = $(this).attr("id");
        if($("#horiz").is("checked")){

        }
        //console.log($(this).attr("id"));

        //Cuando el cursor sale del square
    }, function(){
        $(this).css("background-color", "transparent");
    })*/
})

//Funcion para crear el grid
function createGrid(){
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

        $('#shipPlace').append(currentRow);
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
}

function addShip(x){

    var shipT = ships.shipType.x;
    var currentShipL;
    for(i = 0; i < ships.length; i++){
        if(ships[i].shipType == shipT){
            currentShipL = ships[i].length;
        }
    }

    //if($("#horiz").is("checked")){}

}

var ships = [{
		"shipType": "Carrier",
		"length": 5,
		"shipLocations": []
	}, {
		"shipType": "Battleship",
		"length": 4,
		"shipLocations": []
	}, {
		"shipType": "Submarine",
		"length": 3,
		"shipLocations": []
	}, {
		"shipType": "Destroyer",
		"length": 3,
		"shipLocations": []
	}, {
		"shipType": "PatrolBoat",
		"length": 2,
		"shipLocations": []
}];

var alphab = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];

$(document).on('click', '.boat', function() {
    var index = $(this).attr('data-index');
    var boatRow = $(this);
    var boatLength = ships[index].length;
    $(document).on('click', '.square', function(){
        var squareId = $(this).attr('id');
        var loopedNum;
        var currentLocs;
        var number = parseInt(squareId.substring(1,2));
        if(squareId.length == 3){
            var number = parseInt(squareId.substring(1,3));
        }
        console.log(number);
        var letter = squareId.substring(0,1);
        console.log(letter);
        //Each boat will only paint maximum once
        if(ships[index].shipLocations.length < ships[index].length){
            if($("#vert").is(':checked')){
                if(boatLength + number <= 11 && !collapseV(letter + number, boatLength, ships)){
                    for( i = 0; i < boatLength; i++){
                            loopedNum = number + i;
                            console.log(loopedNum);
                            $('#' + letter + loopedNum).addClass("hasShip")
                            ships[index].shipLocations.push(letter + loopedNum);
                            console.log(ships[index].shipLocations);
                    }
                }
            }

            else{
                var letterInd = alphab.indexOf(letter);
                if(boatLength + letterInd < 11 && !collapseH(letter + number, boatLength, ships)){
                    console.log(letterInd);
                    for( i = 0; i < boatLength; i++){
                        $("#" + alphab[letterInd + i] + number).addClass("hasShip");
                        ships[index].shipLocations.push(alphab[letterInd + i] + number);
                        console.log(ships[index].shipLocations);
                    }
                }
            }
        }
        //Removes the row from the table if the boat is set
        if(ships[index].shipLocations.length== ships[index].length){
           boatRow.remove();
        }
    })

})

function collapseV(coord, shipLength, array){
    console.log(arguments);
    for(var k = 0; k < shipLength; k++){
        for( var i = 0; i < array.length; i++){
            for( var j = 0; j < array[i].shipLocations.length; j++){
                if(array[i].shipLocations[j] === coord){
                    return true;
                }
            }
        }
        coord = coord[0] + (parseInt(coord[1]) + 1);
    }
    return false;
}

function collapseH(coord, shipLength, array){
    console.log(arguments);
    for(var k = 0; k < shipLength; k++){
        for( var i = 0; i < array.length; i++){
            for( var j = 0; j < array[i].shipLocations.length; j++){
                if(array[i].shipLocations[j] === coord){
                    return true;
                }
            }
        }
        coord = alphab[alphab.indexOf(coord[0]) + 1] + coord[1];
    }
    return false;
}
