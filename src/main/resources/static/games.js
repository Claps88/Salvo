$(function() {



    function loadData() {
        $.get("/api/games")
         .done(function(data) {
            data = data.score;
            data.sort(sortBig2Small);
            createTable(data);

         })
    }

    /*function createList(data){

        $listSelector = $("#theOL");
        $.each(data, function(key, value) {

        $listCreator = $("<li>" + "Created: " + value.created + ", Game ID: " + value.id + " , " + "</li>");

            $.each(value.gamePlayer, function(key2, value2){
                $listCreator.append(value2.player.email + "  ");
            })
            $listSelector.append($listCreator);
        });
    }*/

    function sortBig2Small(a, b) {
        if (a.totalScore < b.totalScore)
           return 1;
        if (a.totalScore > b.totalScore)
           return -1;
        return 0;
    }

    function createTable(data) {

        var tabla = $("#tabla");
        var thead = $("<thead/>");
        var tdoby = $("<tbody/>");
        var row = $("<tr/>");

        tabla.append(thead);
        tabla.append(tdoby);
        thead.append(row);
        var titulos = ["Player", "Total Score", "Wins", "Draws", "Losses"];

        $.each(titulos, function(i, value){
                var cell = $('<th/>').text(value);
                row.append(cell);
            });
        $.each(data, function(key, value){
            var row = getRow(value);
            tabla.append(row);
        });

    }

    function getRow(value) {
        var row = $('<tr/>');

        row.append('<td>' + value.email + '</td>');
        row.append('<td>' + value.totalScore + '</td>');
        row.append('<td>' + value.wins + '</td>');
        row.append('<td>' + value.draws + '</td>');
        row.append('<td>' + value.losses + '</td>');
        return row;
    }

    loadData();
});