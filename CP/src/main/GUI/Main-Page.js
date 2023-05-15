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