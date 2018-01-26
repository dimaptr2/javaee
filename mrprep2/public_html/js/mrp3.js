// Here are basic functions for all pages
/*
  LLC, Meat Processing Plant, Pavlovskaya Sloboda
  12/12/2017
*/

// This is a wrapper of all local jquery functions
$( function () {

    let currentDate = new Date();
    let currentYear = currentDate.getFullYear();
    let currentMonth = currentDate.getMonth() + 1;

    // Build criteria for periods
    let periods = [];
    periods[0] = currentMonth + '-' + currentYear;
    let valYear = currentYear;
    let valMonth = currentMonth;

    for (let i = 1; i < 6; i++) {
        valMonth++;
        if (valMonth > 12) {
            valMonth = 1;
            valYear++;
        }
        periods[i] = valMonth + '-' + valYear;
    }

    // Set initial values

    $("#fromPer").ready(function () {
       $("#fromPer").val(currentMonth + '-' + currentYear);
    });

    $("#matnrLow").ready(function () {
        $("#matnrLow").val('10000001');
    });

    $("#matnrHigh").ready(function () {
        $("#matnrHigh").val('19999999');
    });

    $("#progressbar").ready(function () {
        setProgressBar(0);
    });

    $("#tabhead").ready(function () {
        let elem = $("#tabhead");
        for (let i = 0; i < periods.length; i++) {
            elem.append('<th>' + periods[i] + '</th>');
        }
    });

    // Events for the buttons in this page


    // Click on the button so called "Run"
    // $("#Run").click(function (event) {
    //
    //     let low = $("#matnrLow").val();
    //     let high = $("#matnrHigh").val();
    //     let purGroup = $("#purGroup").val();
    //     let place = $("#place").val();
    //
    //     // Assemble all parameters into JSON
    //     // POST callback for the queries
    //     $.post('/mrp2',
    //         {"matnrLow": low, "matnrHigh": high, "purGroup": purGroup, "place": place},
    //         function (data) {
    //
    //         }
    //     );
    //
    // });



});