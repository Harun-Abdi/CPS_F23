function loadCurrentState() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("Liverpool").innerHTML = this.responseText;
        }
    };
    xhttp.open("GET", "http://localhost:8001/team=" +document.getElementById("Liverpool").value , true); // post istedet for get
    xhttp.send();
}