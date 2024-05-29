function toggleLoginForm() {
    var loginForm = document.getElementById("loginForm");
    if (loginForm.style.display === "none" || loginForm.style.display === "") {
        loginForm.style.display = "block";
        document.getElementById("welcome").style.display = "none";
    } else {
        loginForm.style.display = "none";
    }
}

function toggleRegisterForm() {
    var registerPopup = document.getElementById("registerPopup");
    if (registerPopup.style.display === "none" || registerPopup.style.display === "") {
        registerPopup.style.display = "block";
        document.getElementById("loginForm").style.display = "none";
        document.getElementById("welcome").style.display = "none";
    } else {
        registerPopup.style.display = "none";
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
    .then(response => {
        if (!response.ok) {
            throw new Error('Usuario o contraseña incorrectos');
        }
        return response.json();
    })
    .then(data => {
        document.getElementById("usernameDisplay").textContent = data.username;
        document.getElementById("loginForm").style.display = "none";
        document.getElementById("welcome").style.display = "block";
        // Redireccionar a menu.html después de iniciar sesión
        window.location.href = "menu.html";
    })
    .catch((error) => {
        alert(error.message);
        console.error('Error:', error);
    });
}

function register() {
    let username = document.getElementById("regUsername").value;
    let password = document.getElementById("regPassword").value;

    fetch('/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username: username, password: password }),
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error en el registro. Inténtelo de nuevo.');
        }
        return response.json();
    })
    .then(data => {
        alert("Registro exitoso. Inicie sesión para continuar.");
        toggleRegisterForm(); // Ocultar el popup de registro
    })
    .catch((error) => {
        alert(error.message);
        console.error('Error:', error);
    });
}

function showMenu() {
    // Redirigir a menu.html después de iniciar sesión
    window.location.href = "menu.html";
}

function showRecommendations() {
    fetch('/recommendations')
    .then(response => response.json())
    .then(data => {
        let recommendationsDiv = document.getElementById("recommendations");
        recommendationsDiv.style.display = "block";
        recommendationsDiv.innerHTML = "<h3>Series recomendadas:</h3>";
        data.forEach(serie => {
            recommendationsDiv.innerHTML += `<p>${serie.title}: ${serie.description}</p>`;
        });
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}

function insertSeries() {
    let title = prompt("Ingrese el título de la serie:");
    let description = prompt("Ingrese la descripción de la serie:");
    
    fetch('/insert_series', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ title: title, description: description }),
    })
    .then(response => response.json())
    .then(data => {
        alert(data.message);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}

function connectSeriesToGenre() {
    let seriesId = prompt("Ingrese el ID de la serie:");
    let genre = prompt("Ingrese el género:");
    
    fetch('/connect_series_to_genre', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ seriesId: seriesId, genre: genre }),
    })
    .then(response => response.json())
    .then(data => {
        alert(data.message);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}

function addSeriesToUser() {
    let userId = prompt("Ingrese el ID del usuario:");
    let seriesId = prompt("Ingrese el ID de la serie:");
    
    fetch('/add_series_to_user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ userId: userId, seriesId: seriesId }),
    })
    .then(response => response.json())
    .then(data => {
        alert(data.message);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}

function addGenreToUser() {
    let userId = prompt("Ingrese el ID del usuario:");
    let genre = prompt("Ingrese el género:");
    
    fetch('/add_genre_to_user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ userId: userId, genre: genre }),
    })
    .then(response => response.json())
    .then(data => {
        alert(data.message);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}
