function toggleLoginForm() {
    var loginForm = document.getElementById("loginForm");
    if (loginForm.style.display === "none" || loginForm.style.display === "") {
        loginForm.style.display = "block";
        document.getElementById("welcome").style.display = "none";
    } else {
        loginForm.style.display = "none";
    }
}

function login() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username: username, password: password }),
    })
    .then(response => response.json())
    .then(data => {
        if (data.name) {
            document.getElementById("usernameDisplay").textContent = data.name;
            document.getElementById("loginForm").style.display = "none";
            document.getElementById("welcome").style.display = "block";
        } else {
            alert("Usuario o contraseña incorrectos");
        }
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}

function showRecommendations() {
    fetch('/recommendations')
    .then(response => response.json())
    .then(data => {
        let recommendationsDiv = document.getElementById("recommendations");
        recommendationsDiv.innerHTML = "<h3>Series recomendadas:</h3>";
        data.forEach(serie => {
            recommendationsDiv.innerHTML += `<p>${serie}</p>`;
        });
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}
