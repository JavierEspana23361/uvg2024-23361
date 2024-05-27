
import static spark.Spark.*;
import com.google.gson.Gson;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;

import java.util.LinkedList;

public class App {
    public static void main(String[] args) {
        String uri = "bolt://localhost:7687";
        String user = "neo4j";
        String password = "password";
        String databaseName = "neo4j2";
        EmbeddedNeo4j db = new EmbeddedNeo4j(uri, user, password);

        // Configurar las rutas para manejar las solicitudes del frontend
        port(8080);
        Gson gson = new Gson();

        // Ruta para manejar la solicitud de inicio de sesión
        post("/login", (req, res) -> {
            String username = req.queryParams("username");
            String pass = req.queryParams("password");
            boolean found = db.login(uri, user, password, username, pass, databaseName);
            if (found) {
                return "Usuario autenticado";
            } else {
                res.status(401);
                return "Usuario no encontrado";
            }
        });

        // Ruta para manejar la solicitud de recomendaciones
        get("/recommendations", (req, res) -> {
            String username = req.queryParams("username");
            LinkedList<String> recommendations = db.recomend(uri, user, password, username, databaseName);
            res.type("application/json");
            return gson.toJson(recommendations);
        });

        // Ruta para manejar la inserción de una serie
        post("/insertSeries", (req, res) -> {
            String title = req.queryParams("title");
            String releaseYear = req.queryParams("releaseYear");
            String tagline = req.queryParams("tagline");
            db.insertSeries(title, releaseYear, tagline, databaseName);
            return "Serie insertada correctamente";
        });

        // Ruta para manejar la conexión de una serie a un género
        post("/connectSeriesToGenre", (req, res) -> {
            String title = req.queryParams("title");
            String genre = req.queryParams("genre");
            db.CreateSeriesGenresConnection(title, genre, databaseName);
            return "Serie conectada al género correctamente";
        });

        // Ruta para manejar la adición de series a un usuario
        post("/addSeriesToUser", (req, res) -> {
            String username = req.queryParams("username");
            String title = req.queryParams("title");
            db.CreateUserSeriesConnection(username, title, databaseName);
            return "Serie añadida al usuario correctamente";
        });

        // Ruta para manejar la adición de un género a un usuario
        post("/addGenreToUser", (req, res) -> {
            String username = req.queryParams("username");
            String genre = req.queryParams("genre");
            db.CreateUserGenreConnection(username, genre, databaseName);
            return "Género añadido al usuario correctamente";
        });
    }
}
