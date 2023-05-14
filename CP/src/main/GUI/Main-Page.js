
function loadCurrentState() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {

    };
    xhttp.open("GET", "http://localhost:8001/team=" +document.getElementById("Teams").value , true); // post istedet for get
    xhttp.send();
}

function loadData() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            const dataField = document.getElementById("dataField");
            dataField.value = this.responseText;
            console.log("works")
            console.log(this.responseText)

        }
    };
    xhttp.open("GET", "http://localhost:8001/data", true); // post istedet for get
    xhttp.send();
}

        function drawChart() {
        var data = google.visualization.arrayToDataTable([
        ['Team', 'Points'],
        ['2014', 1000, 400, 200],
        ['2015', 1170, 460, 250],
        ['2016', 660, 1120, 300],
        ['2017', 1030],
            []
        ]);

        var options = {
        chart: {
        title: 'Company Performance',
        subtitle: 'Premier League - Season 2019',
    },
        bars: 'vertical' // Required for Material Bar Charts.
    };

        var chart = new google.charts.Bar(document.getElementById('barchart_material'));

        chart.draw(data, google.charts.Bar.convertOptions(options));
    }

