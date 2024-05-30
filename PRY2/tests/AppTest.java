import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class AppTest {

    @Test
    public void testMain() {
        // Redirect System.in and System.out for testing
        String input = "1\nusername\npassword\n1\n8\n3\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Run the main method
        App.main(new String[]{});

        // Verify the output
        String expectedOutput = "\nBienvenido username\n" +
                "Opciones: \n" +
                "1. Ver series recomendadas\n" +
                "2. Insertar serie\n" +
                "3. Unir serie a género\n" +
                "4. Añadir series a usuario\n" +
                "5. Añadir género a usuario\n" +
                "6. Eliminar serie de usuario\n" +
                "7. Eliminar género de usuario\n" +
                "8. Salir\n" +
                "Saliendo...\n";
        assertEquals(expectedOutput, out.toString());
    }

    @Test
    public void testRecomendations() {
        String uri = "bolt://localhost:7687";
        String user = "neo4j";
        String password = "password";
        String databaseName = "neo4j2";
        EmbeddedNeo4j db = new EmbeddedNeo4j(uri, user, password);

        // Create test data
        db.insertSeries("Series 1", "2022", "Tagline 1", databaseName);
        db.insertSeries("Series 2", "2023", "Tagline 2", databaseName);
        db.CreateUserSeriesConnection("username", "Series 1", databaseName);
        db.CreateUserSeriesConnection("username", "Series 2", databaseName);

        // Get recommendations
        LinkedList<String> recommendations = db.recomend(uri, user, password, "username", databaseName);

        // Verify the recommendations
        LinkedList<String> expectedRecommendations = new LinkedList<>();
        expectedRecommendations.add("Series 1");
        expectedRecommendations.add("Series 2");
        assertEquals(expectedRecommendations, recommendations);
    }
}