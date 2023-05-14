function loadCurrentState() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            //get element by id = skal være id på vores selector i stedet for
            document.getElementById("Teams").innerHTML = this.responseText;
        }
    };
    xhttp.open("GET", "http://localhost:8001/team=" +document.getElementById("Liverpool").value , true); // post istedet for get
    xhttp.send();
}


function loadData() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            const dataField = document.getElementById("dataField");
            dataField.value = this.responseText;
            console.log("works")
        }
    };
    xhttp.open("GET", "http://localhost:8001/data", true); // post istedet for get
    xhttp.send();
}


function fetchDataFromDatabase() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:8001/data', true);
    xhr.onload = function() {
        if (this.status == 200) {
            var data = JSON.parse(this.responseText);
            // Pass the data to the function that creates the chart
            createChart(data);
        }
    };
    xhr.send();
}
function createChart(data) {
    // Load the Visualization API and the corechart package.
    google.charts.load('current', {'packages':['corechart']});

    // Set a callback to run when the Google Visualization API is loaded.
    google.charts.setOnLoadCallback(drawChart);

    // Callback that creates and populates a data table,
    // instantiates the chart, passes in the data and
    // draws it.
    function drawChart() {

        // Create the data table.
        const dataTable = new google.visualization.DataTable();
        dataTable.addColumn('string', 'Team');
        dataTable.addColumn('number', 'Wins');

        // Add data to the table
        data.forEach(function(team) {
            dataTable.addRow([teamName, points]);
        });

        // Set chart options
        const options = {
            'title': 'Team Wins',
            'width': 400,
            'height': 300
        };

        // Instantiate and draw the chart, passing in the data and options.
        const chart = new google.visualization.BarChart(document.getElementById('chart_div'));
        chart.draw(dataTable, options);
    }
}
