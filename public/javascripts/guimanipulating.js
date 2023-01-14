// Rekursive Funktion zum Ersetzen der Smilies
function getSmilies(text) {
    temptext = text;
    if (temptext.match(":")) {
        temptext = temptext.replace(":P", "<span class='emoticon_tongue'></span>");
        temptext = temptext.replace(":)", "<span class='emoticon_smile'></span>");
        temptext = temptext.replace(":(", "<span class='emoticon_frown'></span>");
        temptext = temptext.replace(":o", "<span class='emoticon_gasp'></span>");
        temptext = temptext.replace(": ", " ");
        getSmilies(temptext);
    }
    return temptext;
}

// Funktion die den Text nach URLs Parsed
function isURL(text) {
    var regex = new RegExp("^(http:\\/\\/(www\\.)?|ftp:\\/\\/(www\\.)?|www\\.){1}([0-9A-Za-z-\\.@:%_\+~#=]+)+((\\.[a-zA-Z]{2,3})+)(/(.)*)?(\\?(.)*)?");
    if (regex.test(text)) {
        return true;
    } else {
        return false;
    }
}

$(document).ready(function() {
    $("#smiliesbutton").fadeIn();
    // Smilie Vorschau anzeigen
    $("#smiliesbutton").mouseover(function() {
        $("#smilies").fadeIn("slow");
        $("#smilies").delay(2000).fadeOut("slow");
    });
});

// Funktion die den Chat immer nach unten SCrollen lässt
function chatScrollDown() {
    $('.chat').animate({scrollTop: 100000000}, '50');
}

// Neue SpielReihe Generieren
function newTableRow() {
    $(".gameTable tr:last").after('<tr class="actualinput" style="display:none">\n\
                <td><input class="form-control" id="stadt" name="stadt" type="text" placeholder="Stadt"/><br></td>\n\
                <td><input class="form-control" id="land" name="land" type="text" placeholder="Land"/></td>\n\
                <td><input class="form-control" id="fluss" name="fluss" type="text" placeholder="Fluss"/></td>\n\
                <td><input class="form-control" id="name" name="name" type="text" placeholder="Name"/></td>\n\
                <td><input class="form-control" id="beruf" name="beruf" type="text" placeholder="Beruf"/></td>\n\
                <td><input class="form-control" id="pflanze" name="pflanze" type="text" placeholder="Pflanze"/></td>\n\
                <td class="success"></td>\n\
            </tr>').fadeIn("slow", function() {
        $(".gameTable tr:last").show("slow");
    });
}

//Ersetzt die Einagbe Felder durch die Ergebnisse des Users
function replaceEndTableRow() {
    $(".gameTable tr:last").replaceWith("<tr style='display:none'>\n\
                <td class='point-container'>" + $("#stadt").val() + "<span class='point-counter stadtpunkte'></span></td>\n\
                <td class='point-container'>" + $("#land").val() + "<span class='point-counter landpunkte'></span></td>\n\
                <td class='point-container'>" + $("#fluss").val() + "<span class='point-counter flusspunkte'></span></td>\n\
                <td class='point-container'>" + $("#name").val() + "<span class='point-counter namepunkte'></span></td>\n\
                <td class='point-container'>" + $("#beruf").val() + "<span class='point-counter berufpunkte'></span></td>\n\
                <td class='point-container'>" + $("#pflanze").val() + "<span class='point-counter pflanzepunkte'></span></td>\n\
                <td class='success rundenPunkte'></td>\n\
            </tr>'").fadeIn("slow", function() {
        $('.gameTable tr:last .point-counter').delay(1000).css({opacity: 1, top: '-10'});
    });
}
//Löscht die Letzte Zeile der Tabelle wenn ein neuer User Joined
function delEndRowTable() {
    $(".gameTable tr").remove(":last");
}

// Gibt die Punkte in der Tabelle aus
function updateRoundPoints(aktualUser, user, rundenpunkte, stadtpunkte, landpunkte, flusspunkte, namepunkte, berufpunkte, pflanzepunkte, gamepoints,userlistPOINTS,userlist){
    if (aktualUser === user){
        $(".gameTable tr:nth-last-child(2) .stadtpunkte").text(stadtpunkte);
        $(".gameTable tr:nth-last-child(2) .landpunkte").text(landpunkte);
        $(".gameTable tr:nth-last-child(2) .flusspunkte").text(flusspunkte);
        $(".gameTable tr:nth-last-child(2) .namepunkte").text(namepunkte);
        $(".gameTable tr:nth-last-child(2) .berufpunkte").text(berufpunkte);
        $(".gameTable tr:nth-last-child(2) .pflanzepunkte").text(pflanzepunkte);
        $(".gameTable tr:nth-last-child(2) .rundenPunkte").text(rundenpunkte);
        $(".gameTable tr:nth-last-child(2) .rundenPunkte").text(rundenpunkte);
        var us = "#"+aktualUser+" .userpoint-counter";            
        $(us).text(gamepoints);
        renderUserListPOINTS(userlist, userlistPOINTS);
    }
}

// Blendet die Punkte Zahl in der User-Liste aus und wieder ein  
function updateUserCounts() {
    $('.userpoint-counter').slideUp("slow");
    $('.userpoint-counter').slideDown("slow");
}
 
// Pastet die SpielerListe in die Gui
function renderUserListPOINTS(userList, userListPoints) {
    $("#usertable").html("");
    for (z in userList) {
        if (userListPoints[userList[z]] === undefined){
            points = 0;
        } else {
            points = userListPoints[userList[z]];
        }
        $("#usertable").append("<li id='" + userList[z] + "' class='point-container'>" + userList[z] + "<span class='userpoint-counter'>" + points + "</span></li>");
    }
}

// Blendet den Hinweis aus, dass noch nicht genug spieler eingeloggt sind
// Generiert die Spielfläche
function starting(){
    $("#notenoughusers").slideUp("fast");
    $('#top').slideDown("slow");
    $('.gameTable').slideDown("slow");
    $('#highscore').slideDown("slow");
    delEndRowTable();
    newTableRow();
    $('#endround').slideDown("slow");
}

// Handle für das 
$.fn.enterKey = function(fnc) {
    return this.each(function() {
        $(this).keypress(function(ev) {
            var keycode = (ev.keyCode ? ev.keyCode : ev.which);
            if (keycode == '13') {
                fnc.call(this, ev);
            }
        })
    })
}