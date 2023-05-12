function loadCurrentState() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            //get element by id = skal være id på vores selector i stedet for
            document.getElementById("Liverpool").innerHTML = this.responseText;
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

