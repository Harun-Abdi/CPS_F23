/**
 * LoadCurrentState is a method that send a http request with the desired value from the selector in the HTML file
 */

function loadCurrentState() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {

    };
    xhttp.open("GET", "http://localhost:8001/team=" +document.getElementById("Teams").value , true); // post istedet for get
    xhttp.send();
}

/**
 * Loads the data into a textfield area
 */

//bar chart
function loadData() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            const dataField = document.getElementById("dataField");
            dataField.value = this.responseText;
            console.log("works")
            console.log(this.responseText)

            const rows = this.responseText.split("\n")
            const dataArray = [];

            for (let i = 0; i < rows.length; i++) {
                const row = rows[i];
                const values = row.split(",");

                if (values.length >= 5) {
                    const teamName = values[1]?.trim();
                    const points = parseInt(values[4]?.trim());

                    if (teamName && points) {
                        dataArray.push([teamName, points]);
                    }
                }
            }
            console.log(dataArray)
              // call the drawChart function with the dataArray
        }
    };
    xhttp.open("GET", "http://localhost:8001/data", true); // post istedet for get
    xhttp.send();
}

function drawChart(dataArray) {

    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Team');


    for (var i = 0; i < dataArray.length; i++) {
        data.addRow(dataArray[i]);
    }

    var options = {
        chart: {
            title: 'Premier League',
            subtitle: 'Season 2019',
        },
        bars: 'vertical' // Required for Material Bar Charts.
    };

    var chart = new google.charts.Bar(document.getElementById('barchart_material'));

    chart.draw(data, google.charts.Bar.convertOptions(options));
    drawChart(dataArray);
}
