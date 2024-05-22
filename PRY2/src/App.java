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
		String name;
		String pass;


		String nameUser = "Maria";

		try (EmbeddedNeo4j db = new EmbeddedNeo4j(url, user, password))
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
		}

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

	public String signin(String url, String user, String password, String databaseName) {
		try (EmbeddedNeo4j db = new EmbeddedNeo4j(url, user, password))
		{
			System.out.println("Ingrese su nombre de usuario: ");
			String name = System.console().readLine();
			System.out.println("Ingrese su contraseña: ");
			String pass = System.console().readLine();
			String result = db.CreateUsers(name, pass, databaseName);
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} 
		return null;
	}

	public String getMostrelatedUser(String url, String user, String password, String databaseName, String name) {
		try (EmbeddedNeo4j db = new EmbeddedNeo4j(url, user, password))
		{
			LinkedList<String> users = db.getallusers(databaseName);
			users.remove(name);
			int maxConnections = 0;
			String mostRelatedUser = "";

			for (String useri : users) {
				int connections = db.countConnectionsByUser(useri, databaseName);
				if (connections > maxConnections) {
					maxConnections = connections;
					mostRelatedUser = useri;
				}
			}
			System.out.println("Usuario con más relaciones: " + mostRelatedUser);
			return mostRelatedUser;	
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	public LinkedList<String> recomendation(String url, String user, String password, String databaseName, String name, String mostRelatedUser) {
		try (EmbeddedNeo4j db = new EmbeddedNeo4j(url, user, password))
		{
			LinkedList<String> series = db.getSeriesByUser(name, databaseName);
			LinkedList<String> seriesMostRelated = db.getSeriesByUser(mostRelatedUser, databaseName);
			LinkedList<String> seriesRecommended = new LinkedList<String>();
			for (String serie : seriesMostRelated) {
				if (!serie.contains(serie)) {
					seriesRecommended.add(serie);
				}
			}

			if (seriesRecommended.size() > 1) {
				return seriesRecommended;
			} else {
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		



}
