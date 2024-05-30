import java.util.LinkedList;
import java.util.Scanner;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import java.util.Collections;
import java.util.InputMismatchException;

/**
 * The main class of the application.
 * This class contains the main method that serves as the entry point of the program.
 * It provides a menu-driven interface for users to interact with the application.
 */
public class App {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String uri = "bolt://localhost:7687";
		String user = "neo4j";
		String password = "password";
		String databaseName = "neo4j2";
		EmbeddedNeo4j db = new EmbeddedNeo4j(uri, user, password);

		Boolean menu = true;
		Boolean menu2 = true;

		while (menu) {
			
			System.out.println("");
			System.out.println("Opciones: ");
			System.out.println("1. Iniciar sesión");
			System.out.println("2. Registrarse");
			System.out.println("3. Salir");

			int option = 0;
			
			try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("");
                System.out.println("Ingrese un número.");
                scanner.nextLine();
            }

			switch (option) {
				case 1:
					System.out.println("\nIngrese su nombre de usuario: ");
					String username = System.console().readLine();
					System.out.println("Ingrese su contraseña: ");
					String pass = System.console().readLine();
					Boolean found = db.login(uri, user, password, username, pass, databaseName);

					while (menu2) {
						if (found) {
							System.out.println("\nBienvenido " + username);
							
							System.out.println("Opciones: ");
							System.out.println("1. Ver series recomendadas");
							System.out.println("2. Insertar serie");
							System.out.println("3. Unir serie a género"); 
							System.out.println("4. Añadir series a usuario"); 
							System.out.println("5. Añadir género a usuario");
							System.out.println("6. Eliminar serie de usuario");
							System.out.println("7. Eliminar género de usuario");
							System.out.println("8. Mostrar series y géneros de usuario");
							System.out.println("9. Salir");
							
							int opcion = 0;
	
							try {
								opcion = Integer.parseInt(System.console().readLine());
							} catch (Exception e) {
								System.out.println("Ingrese un número.");
							}
	
							switch (opcion) {
								case 1: // Recomendación de series
									LinkedList<String> recomendations = db.recomend(uri, user, password, username, databaseName);
									System.out.println("Series recomendadas: ");
									if (recomendations.size() == 0) {
										System.out.println("No hay series recomendadas");
									} else {
										for (int i = 0; i < recomendations.size(); i++) 
										System.out.println(recomendations.get(i));
									}
									break;
								case 2: // Agregar una serie a la base de datos
									System.out.println("Ingrese el nombre de la serie: ");
									String title = System.console().readLine();
									System.out.println("Ingrese el año de la serie: ");
									String releaseYear = System.console().readLine();
									System.out.println("Ingrese la tagline de la serie: ");
									String tagline = System.console().readLine();
	
									db.insertSeries(title, releaseYear, tagline, databaseName);
									
									Boolean connectSeriesGenre = true;
	
									while (connectSeriesGenre) {
										int option2 = 0;
										System.out.println("¿Desea agregar un género a la serie?");
										System.out.println("1. Sí");
										System.out.println("2. No");
										try {
											option2 = Integer.parseInt(System.console().readLine());
										} catch (Exception e) {
											System.out.println("Ingrese un número.");
										}
	
										switch (option2) {
											case 1:
												System.out.println("Seleccione el género de la serie:");
												LinkedList<String> genres = db.getGenres(databaseName);
												for (int i = 0; i < genres.size(); i++) {
													System.out.println(i + 1 + ". " + genres.get(i));
												}
												int genreOption = 0;
												try {
													genreOption = Integer.parseInt(System.console().readLine());
												} catch (Exception e) {
													System.out.println("Ingrese un número.");
												}
	
												String genre = genres.get(genreOption - 1);
	
												db.CreateSeriesGenresConnection(title, genre, databaseName);
												break;
											case 2:
												connectSeriesGenre = false;
												break;
											case 0:
												continue;
											default:
												System.out.println("Opción no válida");
												break;
										}
									}
									break;
								case 3: // Unir serie a género
									System.out.println("Ingrese el nombre de la serie: ");
									String serie1 = System.console().readLine();
									System.out.println("Ingrese el género de la serie: ");
									LinkedList<String> genres = db.getGenres(databaseName);
									for (int i = 0; i < genres.size(); i++) {
										System.out.println(i + 1 + ". " + genres.get(i));
									}
									int genreOption = 0;
									try {
										genreOption = Integer.parseInt(System.console().readLine());
									} catch (Exception e) {
										System.out.println("Ingrese un número.");
									}
									String genre = genres.get(genreOption - 1);
									db.CreateSeriesGenresConnection(serie1, genre, databaseName);
									break;
								case 4: // Añadir series a usuario
									System.out.println("Seleccione la serie que desea agregar: ");
									LinkedList<String> series = db.getSeries(databaseName);
									for (int i = 0; i < series.size(); i++) {
										System.out.println(i + 1 + ". " + series.get(i));
									}
									int serieOption = 0;
									try {
										serieOption = Integer.parseInt(System.console().readLine());
									} catch (Exception e) {
										System.out.println("Ingrese un número.");
									}
									title = series.get(serieOption - 1);
									db.CreateUserSeriesConnection(username, title, databaseName);
									break;
								case 5: // Añadir género a usuario
									System.out.println("Seleccione el género que desea agregar: ");
									LinkedList<String> genres5 = db.getGenres(databaseName);
									for (int i = 0; i < genres5.size(); i++) {
										System.out.println(i + 1 + ". " + genres5.get(i));
									}
									int genreOption5 = 0;
									try {
										genreOption5 = Integer.parseInt(System.console().readLine());
									} catch (Exception e) {
										System.out.println("Ingrese un número.");
									}
									genre = genres5.get(genreOption5 - 1);
									db.CreateUserGenreConnection(username, genre, databaseName);
									break;
								case 6: // Eliminar serie de usuario
									System.out.println("Seleccione la serie que desea eliminar: ");
									LinkedList<String> series2 = db.getSeriesByUser(username, databaseName);
									for (int i = 0; i < series2.size(); i++) {
										System.out.println(i + 1 + ". " + series2.get(i));
									}
									int serieOption2 = 0;
									try {
										serieOption2 = Integer.parseInt(System.console().readLine());
									} catch (Exception e) {
										System.out.println("Ingrese un número.");
									}
									title = series2.get(serieOption2 - 1);
									db.deleteSeriesUserConnection(username, title, databaseName);
									break;
								case 7: // Eliminar género de usuario
									System.out.println("Seleccione el género que desea eliminar: ");
									LinkedList<String> genres2 = db.getGenresByUser(username, databaseName);
									for (int i = 0; i < genres2.size(); i++) {
										System.out.println(i + 1 + ". " + genres2.get(i));
									}
									int genreOption2 = 0;
									try {
										genreOption2 = Integer.parseInt(System.console().readLine());
									} catch (Exception e) {
										System.out.println("Ingrese un número.");
									}
									genre = genres2.get(genreOption2 - 1);
									db.deleteGenreUserConnection(username, genre, databaseName);
									break;
								case 8:
									String connections = db.showConnectionsUser(username, databaseName);
									System.out.println(connections);
									break;
								case 9:
									System.out.println("Saliendo...");
									menu2 = false;
									break;
								case 0:
									continue;		
								default:
									System.out.println("Opción no válida");
									break;
							}
						} else {
							System.out.println("\nUsuario no encontrado");
						}
					}
					break;
				case 2:
					db.signin(uri, user, password, databaseName);
					break;
				case 3:
					System.out.println("Saliendo...");
					menu = false;
					break;
				case 0:
					continue;
				default:
					System.out.println("Opción no válida");
					break;
			}
		}
	}
}