import java.util.LinkedList;

public class App {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String uri = "bolt://localhost:7687";
		String user = "neo4j";
		String password = "password";
		String databaseName = "neo4j2";
		String name;
		String pass;

	
	}

	public String signin(String uri, String user, String password, String databaseName) {
		try (EmbeddedNeo4j db = new EmbeddedNeo4j(uri, user, password))
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

	public String getMostrelatedUser(String uri, String user, String password, String databaseName, String name) {
		try (EmbeddedNeo4j db = new EmbeddedNeo4j(uri, user, password))
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

	public LinkedList<String> recomendation(String uri, String user, String password, String databaseName, String name) {
		try (EmbeddedNeo4j db = new EmbeddedNeo4j(uri, user, password)) {
			String mostRelatedUser = getMostrelatedUser(uri, user, password, databaseName, name);
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
				String rngenre = getuserGenre(uri, user, password, databaseName, name);
				seriesRecommended = getSeriesByGenre(uri, user, password, databaseName, rngenre);
				LinkedList<String> existingSeries = getSeriesByUser(uri, user, password, databaseName, name);
				for (String serie : existingSeries) {
					if (seriesRecommended.contains(serie)) {
						seriesRecommended.remove(serie);
					}
				}	
				return seriesRecommended;
			}
		}
		 catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getuserGenre(String uri, String user, String password, String databaseName, String name) {
		try (EmbeddedNeo4j db = new EmbeddedNeo4j(uri, user, password)) {
			LinkedList<String> genres = db.getGenresByUser(name, databaseName);
			String randomGenre = genres.get((int) (Math.random() * genres.size()));
			if (randomGenre == null) {
				LinkedList<String> allGenres = db.getGenres(databaseName);
				randomGenre = allGenres.get((int) (Math.random() * allGenres.size()));
			}
			return randomGenre;
		}
		 catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public LinkedList<String> getSeriesByGenre(String uri, String user, String password, String databaseName, String genre) {
		try (EmbeddedNeo4j db = new EmbeddedNeo4j(uri, user, password)) {
			LinkedList<String> series = db.getSeriesByGenre(genre, databaseName);
			return series;
		}
		 catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public LinkedList<String> getSeriesByUser(String uri, String user, String password, String databaseName, String name) {
		try (EmbeddedNeo4j db = new EmbeddedNeo4j(uri, user, password)) {
			LinkedList<String> series = db.getSeriesByUser(name, databaseName);
			return series;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
