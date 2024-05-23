import java.util.LinkedList;
import java.util.Scanner;
import java.util.Collections;
public class App {
	Scanner scanner = new Scanner(System.in);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String uri = "bolt://localhost:7687";
		String user = "neo4j";
		String password = "password";
		String databaseName = "neo4j2";
		String name;

		System.out.println("Opciones: ");
		System.out.println("1. Iniciar sesión");
		System.out.println("2. Registrarse");
		int option = scanner.nextInt();
		if (option == 1) {
			name = login(uri, user, password, databaseName);
			if (name == null) {
				System.out.println("Usuario o contraseña incorrectos");
				System.out.println("¿Desea registrarse? (s/n)");
				String register = scanner.nextLine();
				if (register.equals("s")) {
					App app = new App();
					app.signin(uri, user, password, databaseName);
					name = login(uri, user, password, databaseName);
				}
			}
		} else {
			App app = new App();
			app.signin(uri, user, password, databaseName);
			name = login(uri, user, password, databaseName);
		}

		if (name != null) {
			System.out.println("Bienvenido " + name);
			App app = new App();
			
			System.out.println("Opciones: ");
			System.out.println("1. Ver series recomendadas"); //obtiene una linkdelist de series
			System.out.println("2. Insertar serie"); //obtiene una linkdelist de series
			System.out.println("3. Unir serie a género"); //no obtiene nada, solo hace la relación
			System.out.println("4. Añadir series a usuario"); //no obtiene nada, solo hace la relación
			System.out.println("5. Añadir género a usuario"); //no obtiene nada, solo hace la relación
			int opcion = scanner.nextInt();
			if (opcion == 1) {
				LinkedList<String> series = app.recomendation(uri, user, password, databaseName, name); // Lista de series recomendadas
				System.out.println("Series recomendadas: ");
				for (String serie : series) {
					System.out.println(serie);
				}
			} 
			else if (opcion == 2) {
				System.out.println("Ingrese el nombre de la serie: ");
				String serie = scanner.nextLine();
				app.insertSerie(uri, user, password, databaseName, serie);
			} 
			else if (opcion == 3) {
				System.out.println("Ingrese el nombre de la serie: ");
				String serie = scanner.nextLine();
				System.out.println("Ingrese el género de la serie: ");
				String genre = scanner.nextLine();
				app.insertGenre(uri, user, password, databaseName, serie, genre);
			} 
			else if (opcion == 4) {
				System.out.println("Ingrese el nombre de la serie: ");
				String serie = scanner.nextLine();
				app.insertSerieToUser(uri, user, password, databaseName, name, serie);
			} 
			else if (opcion == 5) {
				System.out.println("Ingrese el género: ");
				String genre = scanner.nextLine();
				app.insertGenreToUser(uri, user, password, databaseName, name, genre);
			}
		}
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
				Collections.shuffle(seriesRecommended);
				seriesRecommended.subList(3, seriesRecommended.size()).clear();
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
	
	public static String login(String uri, String user, String password, String databaseName) {
		try (EmbeddedNeo4j db = new EmbeddedNeo4j(uri, user, password)) {
			System.out.println("Ingrese su nombre de usuario: ");
			String name = System.console().readLine();
			System.out.println("Ingrese su contraseña: ");
			String pass = System.console().readLine();
			String pword = db.getUsersPassword(name, databaseName);
			if (pword.equals(pass)) {
				return name;
			} else {
				return null;
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
}
