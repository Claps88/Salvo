$(function() {



    function loadData() {
        $.get("/api/games")
         .done(function(data) {
            data = data;
            datag = data.games;
            datas = data.score;
            datas.sort(sortBig2Small);
            createTable(datas);
            createList(data);
         })
    }


    function createList(data){

        $listSelector = $("#theOL");
        console.log(data);
        //Is this working??
        if(data.currentPlayer != null){
            var cpId = data.currentPlayer.id;
            console.log(data.games);
            for(i = 0; i < data.games.length; i++){
                var userId1 = data.games[i].gamePlayer[0].player.id;
                if(data.games[i].gamePlayer[1] != null){
                    var userId2 = data.games[i].gamePlayer[1].player.id;
                }
                else{
                var userId2 = null;
                }
                if( cpId == userId1 || cpId == userId2){
                    if(cpId == userId1){
                        $but = $("<button class='page' id='" + data.games[i].gamePlayer[0].id + "'>Go to game</button>");
                        $listCreator = $("<li>" + "Created: " + data.games[i].created + ", Game ID: " + data.games[i].id + " , " + "</li>");
                        $.each(data.games[i].gamePlayer, function(key2, value2){
                            $listCreator.append(value2.player.email + "  ");
                        })
                        $listCreator.append($but);
                        $listSelector.append($listCreator);
                    }
                    if( cpId == userId2){
                        $but = $("<button class='page' id='" + data.games[i].gamePlayer[1].id + "'>Go to game</button>");
                        $listCreator = $("<li>" + "Created: " + data.games[i].created + ", Game ID: " + data.games[i].id + " , " + "</li>");
                        $.each(data.games[i].gamePlayer, function(key2, value2){
                            $listCreator.append(value2.player.email + "  ");
                        })
                        $listCreator.append($but);
                        $listSelector.append($listCreator);
                    }
                }
                else if(data.games[i].gamePlayer[0] && data.games[i].gamePlayer[1] != null){
                    $button = $("<button class='join' id='" + data.games[i].gamePlayer[0].id + "'>View Game</button>");
                    $listCreator = $("<li>" + "Created: " + data.games[i].created + ", Game ID: " + data.games[i].id + " , " + "</li>");
                    $.each(data.games[i].gamePlayer, function(key2, value2){
                        $listCreator.append(value2.player.email + "  ");
                    })
                    $listCreator.append($button);
                    $listSelector.append($listCreator);
                }
                else{
                    $button = $("<button class='join' id='" + data.games[i].gamePlayer[0].id + "' data-gameid =' " + data.games[i].id + "'>Join Game</button>");
                    $listCreator = $("<li>" + "Created: " + data.games[i].created + ", Game ID: " + data.games[i].id + " , " + "</li>");
                    $.each(data.games[i].gamePlayer, function(key2, value2){
                        $listCreator.append(value2.player.email + "  ");
                    })
                    $listCreator.append($button);
                    $listSelector.append($listCreator);
                }
            }
        }
    }

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
    //Button events
    //Login
    $("#loginBTN").on("click", logIn);
    //Logout
    $("#logoutBTN").on("click", logOut);
    //Sign Up
    $("#signUp").on("click", signUp)

    function logIn(){

        var namec = namein.value;
        var pasw = passw.value;

        var constr = {userName: namec, password: pasw};

        $.post("/api/login", constr)
            .done(function(data) {
                console.log("logged in!");
                alert("Logged in!");
                $("#login-form").addClass("hidden");
                $("#logout-form").removeClass("hidden");
                location.reload();
            })
            .fail(function() {
               alert("Check your username and password");
            });
    }

    function logOut(){

        $.post("/api/logout").done(function() {
            alert("Logged out!");
            console.log("logged out");
            $("#login-form").removeClass("hidden");
            $("#logout-form").addClass("hidden");
        })
        location.reload();
    }

    function signUp(){
        var namec = namein.value;
        var pasw = passw.value;
        var constr = {userName: namec, password: pasw};

        $.post("/api/players", constr)
                .done(function() {
                alert("Signed Up!")
                console.log("Signed up!");
                logIn();
                  })
                .fail(function() {
                 if(namec == ""){
                 alert("You must use a name")
                 }
                 else{
                 alert("Name is already taken")
                 }
                 })

    }

    function checkIfLogin(){

        $.get("api/games")
            .done(function(data){
                if(data.currentPlayer != null){
                    $("#login-form").addClass("hidden");
                    $("#logout-form").removeClass("hidden");
                    $("#userLogged").append(data.currentPlayer.userName);
                    $("#userLogged").removeClass("hidden");
                    $("#createGames").removeClass("hidden");
                }
            })
    }

    //Start of Event Listeners

    //Event Listeners for "Go to Game" buttons
    $(document).on('click', '.page', function() {
        var linkBtn = $(this).attr("id");
        window.location = "http://localhost:8080/game.html?gp=" + linkBtn;
    })

    //Event Listener to "Create Game"
    $("#createGames").on("click", createGame);

    //Event Listener to "join game"
    $(document).on("click", ".join", function() {
        var idBtn = $(this).attr("data-gameid");
        $.post("/api/game/" + idBtn + "/players")
            .done(function(data){
                location.href = "game.html?gp=" + data.newGpId
            })
    })


    function createGame(){

        $.post("api/game")
            .done(function(data){
              //  location.reload();
                location.href = "game.html?gp=" + data.gamePlayerId;
            })
    }


    checkIfLogin();
    loadData();
});