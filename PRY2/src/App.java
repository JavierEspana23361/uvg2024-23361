import java.util.LinkedList;

public class App {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "bolt://localhost:7687";
		String user = "neo4j";
		String password = "password";
		String databaseName = "neo4j2";

		String nameUser = "Maria";

		/*try (EmbeddedNeo4j db = new EmbeddedNeo4j(url, user, password))
        {
		 	LinkedList<String> genres = db.getSeries(databaseName);
		 	
		 	for (int i = 0; i < genres.size(); i++) {
		 		System.out.println(genres.get(i));
		 	}
        	
        } catch (Exception e) {
			e.printStackTrace();
		}

		try (EmbeddedNeo4j db = new EmbeddedNeo4j(url, user, password))
		{
		 	LinkedList<String> series = db.getSeries(databaseName);
		 	
		 	for (int i = 0; i < series.size(); i++) {
		 		System.out.println(series.get(i));
		 	}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (EmbeddedNeo4j db = new EmbeddedNeo4j(url, user, password))
		{
		 	LinkedList<String> users = db.getUsers(databaseName);

			for (int i = 0; i < users.size(); i++) {
				System.out.println(users.get(i));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		try (EmbeddedNeo4j db = new EmbeddedNeo4j(url, user, password))
		{
			int count = db.countSeriesByUser(nameUser, databaseName);

			System.out.println("\nSeries de " + nameUser + ": " + count);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (EmbeddedNeo4j db = new EmbeddedNeo4j(url, user, password))
		{
			int count = db.countGenresByUser(nameUser, databaseName);

			System.out.println("\nGéneros de " + nameUser + ": " + count);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (EmbeddedNeo4j db = new EmbeddedNeo4j(url, user, password))
		{
			int count = db.countConnectionsByUser(nameUser, databaseName);

			System.out.println("\nConexiones de " + nameUser + ": " + count);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
