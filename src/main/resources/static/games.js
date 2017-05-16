$(function() {

    function loadData() {
        $.get("/api/games")
         .done(function(data) {
            data = data.games;
            createList(data);
         })
    }


    function createList(data){

        $listSelector = $("#theOL");
        $.each(data, function(key, value) {

        $listCreator = $("<li>" + "Created: " + value.created + ", Game ID: " + value.id + " , " + "</li>");

            $.each(value.gamePlayer, function(key2, value2){
                $listCreator.append(value2.player.email + "  ");
            })
            $listSelector.append($listCreator);
        });
    }
    loadData();
});