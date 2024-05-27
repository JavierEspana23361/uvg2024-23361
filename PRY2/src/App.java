import java.util.LinkedList;
import java.util.Scanner;

import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;

import java.util.Collections;
import java.util.InputMismatchException;

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
		EmbeddedNeo4j db = new EmbeddedNeo4j(uri, user, password);

		Boolean menu = true;

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

					if (found) {
						System.out.println("\nBienvenido " + username);
						App app = new App();
						
						System.out.println("Opciones: ");
						System.out.println("1. Ver series recomendadas");
						System.out.println("2. Insertar serie");
						System.out.println("3. Unir serie a género"); 
						System.out.println("4. Añadir series a usuario"); 
						System.out.println("5. Añadir género a usuario");
						System.out.println("6. Salir");
						
						int opcion = 0;

						try {
							opcion = Integer.parseInt(System.console().readLine());
						} catch (Exception e) {
							System.out.println("Ingrese un número.");
						}

						switch (opcion) {
							case 1: // Recomendación de series
								db.recomend(uri, username, password, username, databaseName);
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
											System.out.println("Ingrese el género: ");
											String genre = System.console().readLine();
											db.insertGenre(uri, user, password, databaseName, title, genre);
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
								String genre = System.console().readLine();
								db.insertGenre(uri, user, password, databaseName, serie1, genre);
								break;
							case 4: // Añadir series a usuario
								System.out.println("Ingrese el nombre de la serie: ");
								String serie2 = System.console().readLine();
								db.insertSerieToUser(uri, user, password, databaseName, username, serie2);
								break;
							case 5: // Añadir género a usuario
								System.out.println("Ingrese el género: ");
								String genre2 = System.console().readLine();
								db.insertGenreToUser(uri, user, password, databaseName, username, genre2);
								break;
							case 6:
								System.out.println("Sesión cerrada");
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
