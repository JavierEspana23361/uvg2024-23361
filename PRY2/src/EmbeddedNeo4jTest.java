import org.junit.Test;
import java.util.LinkedList;

public class EmbeddedNeo4jTest {
    @Test
    public void testGetGenres() {
        String uri = "bolt://localhost:7687";
		String user = "neo4j";
		String password = "password";
		String databaseName = "neo4j2";

        EmbeddedNeo4j db = new EmbeddedNeo4j(uri, user, password);

        db.getGenres(databaseName);
    }

    @Test
    public void testGetGenresByUser() {
        String uri = "bolt://localhost:7687";
		String user = "neo4j";
		String password = "password";
		String databaseName = "neo4j2";

        EmbeddedNeo4j db = new EmbeddedNeo4j(uri, user, password);

        LinkedList<String> genres = db.getGenresByUser("Esteban" ,databaseName);

        for (String genre : genres) {
            System.out.println(genre);
        }
    }

    @Test
    public void testGetSeries() {
        String uri = "bolt://localhost:7687";
		String user = "neo4j";
		String password = "password";
		String databaseName = "neo4j2";

        EmbeddedNeo4j db = new EmbeddedNeo4j(uri, user, password);

        db.getSeries(databaseName);
    }

    @Test
    public void testGetSeriesByGenre() {
        String uri = "bolt://localhost:7687";
		String user = "neo4j";
		String password = "password";
		String databaseName = "neo4j2";

        EmbeddedNeo4j db = new EmbeddedNeo4j(uri, user, password);

        LinkedList<String> series = db.getSeriesByGenre("Comedia", databaseName);

        for (String serie : series) {
            System.out.println(serie);
        }
    }

    @Test
    public void testGetSeriesByUser() {
        String uri = "bolt://localhost:7687";
		String user = "neo4j";
		String password = "password";
		String databaseName = "neo4j2";

        EmbeddedNeo4j db = new EmbeddedNeo4j(uri, user, password);

        LinkedList<String> series = db.getSeriesByUser("Esteban", databaseName);

        for (String serie : series) {
            System.out.println(serie);
        }
    }

    @Test
    public void testGetallusers() {
        String uri = "bolt://localhost:7687";
		String user = "neo4j";
		String password = "password";
		String databaseName = "neo4j2";

        EmbeddedNeo4j db = new EmbeddedNeo4j(uri, user, password);

        LinkedList<String> users = db.getallusers(databaseName);

        for (String username : users) {
            System.out.println(username);
        }
    }
}
