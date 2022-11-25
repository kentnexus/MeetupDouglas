function loadF(e) {
    let isadmin = e.dataset.isadmin;
    console.log(isadmin);
    if( isadmin === "true") {
        document.getElementById("joinBtn").value = "Joined";
        document.getElementById("joinBtn").disabled = true;
    }
}