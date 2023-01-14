/* Laden der Regeln mit Ajax */
$(document).ready(function() {
    /*Regeln per Ajax nachladen*/
    $.ajax({
        url: "/rules",
        dataType: "text",
        success: function(daten) {
            $("#rules").html(daten);
        }
    });
    
    /*Higscore per Ajax laden*/
    function highScoreAjax() {
        $.ajax({
            url: "/highscore",
            dataType: "json",
            success: function(daten) {
                for (r in daten) {
                    $('.highscoretable tr:last').after('<tr class="active">\n\\n\
                        <td>' + daten[r].position + '</td>\n\
                        <td>' + daten[r].name + '</td>\n\
                        <td>' + daten[r].score + '</td>\n\
                        <td>' + daten[r].date + '</td>\n\
                    </tr>');
                }
            }
        });
    }
    
    /*Kontaktseite per Ajax laden*/
    $.ajax({
        url: "/contact",
        dataType: "text",
        success: function(daten) {
            $(".contact").html(daten);
        }
    });
    
    /*Impressum per Ajax laden*/
    $.ajax({
        url: "/impressum",
        dataType: "text",
        success: function(daten) {
            $(".impressum").html(daten);
        }
    });

    /* Lässt die Rules einsliden */
    $(".rulesclick").click(function() {
        $("#rules").slideToggle(1500);
        $('html,body').animate({
            scrollTop: $("#rules").offset().top}, 2000 , function (){location.hash = $("#rules");
        });
    });

    /* Anzeigen des Tell A Friend Forms */
    $("#tellafriend").click(function() {
        $(".tellafriend").slideToggle(600);
    });

    /*Anzeigen des Highscores*/
    $("#showhighscorebutton").click(function() {
        $("#highscoreout").html("<h2>HighScore</h2> <br> <table class='table table-condensed highscoretable'> <tr class='success'> <th>Position</th> <th>User</th> <th>Punkte</th> <th>Datum</th> </tr> <tr></tr> </table>");
        highScoreAjax();
        $("#highscoreout").slideToggle(600);
        $('html,body').animate({
            scrollTop: $("#highscoreout").offset().top}, 2000 , function (){location.hash = $("#highscoreout");
        });
    });
    
    /* Contaktseite anzeigen*/
    $("#contactclick").click(function(){
        $(".contact").slideToggle(600);
        $('html,body').animate({
            scrollTop: $(".contact").offset().top}, 2000 , function (){location.hash = $(".contact");
        });
    });
    
    /* Impressum anzeigen*/
    $("#impressumclick").click(function(){
        $(".impressum").slideToggle(600);
        $('html,body').animate({
            scrollTop: $(".impressum").offset().top}, 2000 , function (){location.hash = $(".impressum");
        });
    });
    
    /*Scroll up Button*/
    $(".back-top").hide();
    $(function () { 
         $(window).scroll(function () {
             if ($(this).scrollTop() > 200) {
                 $('.back-top').fadeIn("slow");
             } else {
                 $('.back-top').fadeOut("slow");
             }
         });
     });
});

/*
 * löscht das Formular und gibt aus dass alles klar ist
 */
function cleanForm(){
    $(".contact").slideToggle(600);
    $("#contactlastname").val("");
    $("#contactfirstname").val("");
    $("#contactemail").val("");
    $("#contactemailsubject").val("");
    $("#contacttext").val("");
    alert("Vielen Dank wir haben Ihre Nachricht erhalten");
}