
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import static org.neo4j.driver.Values.parameters;
import java.util.LinkedList;
import java.util.List;

public class EmbeddedNeo4j implements AutoCloseable{

    private final Driver driver;

    public EmbeddedNeo4j( String url, String user, String password)
    {
        driver = GraphDatabase.driver( url, AuthTokens.basic( user, password ) );
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    public LinkedList<String> recomend(String uri, String name, String password, String username, String databaseName) {
        LinkedList<String> recommendations = new LinkedList<String>();
        try (EmbeddedNeo4j db = new EmbeddedNeo4j(uri, name, password)) {
            Boolean menu = true;
            while (menu) {
                System.out.println("\nSeleccione el tipo de recomendación que desea:");
                System.out.println("1. Recomendaciones para usted");
                System.out.println("2. Recomendaciones por tendencia");
                System.out.println("3. Recomendaciones de usuario más similar");
                System.out.println("4. Salir");
    
                int option = 0;
                try {
                    option = Integer.parseInt(System.console().readLine());
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número válido.");
                    continue;
                }
    
                switch (option) {
                    case 1:
                        recommendations = db.recommendationsForYou(username, databaseName);
                        int n = 1;
                        System.out.println("\nRecomendaciones para usted:");
                        for (String serie : recommendations) {
                            System.out.println(n + ". " + serie);
                            n++;
                        }
                        break;
                    case 2:
                        recommendations = db.recomendationsTrend(databaseName);
                        int i = 1;
                        System.out.println("\nRecomendaciones por tendencia:");
                        for (String serie : recommendations) {
                            System.out.println(i + ". " + serie);
                            i++;
                        }
                        break;
                    case 3:
                        recommendations = db.recommendationsFromMostSimilarUser(username, databaseName);
                        int j = 1;
                        System.out.println("\nRecomendaciones de usuario más similar:");
                        for (String serie : recommendations) {
                            System.out.println(j + ". " + serie);
                            j++;
                        }
                        break;
                    case 4:
                        System.out.println("Saliendo...");
                        menu = false;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recommendations;
    }
    
    /**
     * Retrieves a list of recommended series for a given user based on their preferences.
     *
     * @param name          the name of the user
     * @param databaseName  the name of the database to connect to
     * @return              a LinkedList of recommended series titles
     */
    public LinkedList<String> recommendationsForYou(String name, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName))) {
            LinkedList<String> series = session.readTransaction(new TransactionWork<LinkedList<String>>() {
                @Override
                public LinkedList<String> execute(Transaction tx) {
                    Result result = tx.run(
                        "MATCH (u:User {name: $name})-[:LE_GUSTA]->(g:Genero)<-[:PERTENECE_A]-(s:Series) " +
                        "WHERE NOT (u)-[:LE_GUSTA]->(s) " +
                        "RETURN s.title " +
                        "LIMIT 10",
                        parameters("name", name)
                    );
                    LinkedList<String> seriesList = new LinkedList<String>();
                    List<Record> records = result.list();
                    for (Record record : records) {
                        seriesList.add(record.get("s.title").asString());
                    }
                    return seriesList;
                }
            });
            return series;
        }
    }    

    /**
     * Retrieves a list of recommended series based on trending connections in the specified database.
     *
     * @param databaseName the name of the database to query
     * @return a LinkedList of recommended series titles
     */
    public LinkedList<String> recomendationsTrend(String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName))) {
            LinkedList<String> topSeries = session.readTransaction(new TransactionWork<LinkedList<String>>() {
                @Override
                public LinkedList<String> execute(Transaction tx) {
                    Result result = tx.run(
                        "MATCH (s:Series)<-[r:LE_GUSTA]-() " +
                        "RETURN s.title AS title, count(r) AS connections " +
                        "ORDER BY connections DESC " +
                        "LIMIT 10"
                    );
                    
                    LinkedList<String> seriesList = new LinkedList<>();
                    List<Record> records = result.list();
                    for (Record record : records) {
                        seriesList.add(record.get("title").asString());
                    }
                    return seriesList;
                }
            });
            return topSeries;
        }
    }

    /**
     * Retrieves a list of recommendations for a given user based on the most similar user in the database.
     * 
     * @param name          the name of the user for whom recommendations are being generated
     * @param databaseName  the name of the database containing user and series information
     * @return              a LinkedList of recommended series for the user
     */
    public LinkedList<String> recommendationsFromMostSimilarUser(String name, String databaseName) {
        String mostSimilarUser = findMostSimilarUser(name, databaseName);
        LinkedList<String> recommendations = new LinkedList<String>();
        if (mostSimilarUser != "No similar user found") {        
            LinkedList<String> seriesByUser = getSeriesByUser(name, databaseName);
            LinkedList<String> seriesByMostSimilarUser = getSeriesByUser(mostSimilarUser, databaseName);
        
            for (String serie : seriesByMostSimilarUser) {
                if (!seriesByUser.contains(serie)) {
                    recommendations.add(serie);
                }
            }
        }
        return recommendations;
    }

    /**
     * Logs in the user with the provided credentials.
     * 
     * @param uri the URI of the Neo4j database
     * @param name the name of the Neo4j database
     * @param password the password of the Neo4j database
     * @param username the username of the user to log in
     * @param pass the password of the user to log in
     * @param databaseName the name of the database to search for the user
     * @return true if the user is found and logged in successfully, false otherwise
     */
    public Boolean login(String uri, String name, String password, String username, String pass, String databaseName) {
        try (EmbeddedNeo4j db = new EmbeddedNeo4j(uri, name, password)) {
            return db.foundUser(username, pass, databaseName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Checks if a user with the given name and password exists in the specified database.
     * 
     * @param name          the name of the user to check
     * @param password      the password of the user to check
     * @param databaseName  the name of the database to search in
     * @return              true if a user with the given name and password is found, false otherwise
     */
    public Boolean foundUser(String name, String password, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName))) {
            Boolean result = session.readTransaction(new TransactionWork<Boolean>() {
                @Override
                public Boolean execute(Transaction tx) {
                    Result result = tx.run("MATCH (u:User {name: $name, password: $password}) RETURN u",
                            parameters("name", name, "password", password));
                    return !result.list().isEmpty();
                }
            });
            return result;
        }
    }

    /**
     * Signs in a user and creates a user profile in the Neo4j database.
     * 
     * @param uri           the URI of the Neo4j database
     * @param user          the username for authentication
     * @param password      the password for authentication
     * @param databaseName  the name of the database
     * @return              the result of the user creation process
     */
    public String signin(String uri, String user, String password, String databaseName) {
		try (EmbeddedNeo4j db = new EmbeddedNeo4j(uri, user, password)) {
			System.out.println("\nIngrese su nombre de usuario: ");
			String name = System.console().readLine();
			System.out.println("Ingrese su contraseña: ");
			String pass = System.console().readLine();

			Boolean success = db.CreateUsers(name, pass, databaseName);

            String result = null;

            if (success) {
                System.out.println("\nUsuario creado con éxito\n");

                System.out.println("Desea añadir géneros a su perfil?");
                System.out.println("1. Sí");
                System.out.println("2. No");

                int option = 0;
                
                try {
                    option = Integer.parseInt(System.console().readLine());
                } catch (Exception e) {
                    System.out.println("Ingrese un número.");
                }


                if (option == 1) {
                    result = db.BucleCreateUserGenreConnection(name, databaseName);
                }

                System.out.println("Desea añadir series a su perfil?");
                System.out.println("1. Sí");
                System.out.println("2. No");

                try {
                    option = Integer.parseInt(System.console().readLine());
                } catch (Exception e) {
                    System.out.println("Ingrese un número.");
                }

                if (option == 1) {
                    result = db.BucleCreateUserSeriesConnection(name, databaseName);
                }
                return result;
            } else {
                System.out.println("\nEl usuario ya existe.\n");
            }
		} catch (Exception e) {
			e.printStackTrace();
			
		} 
		return null;
	}

    /**
     * Creates a connection between a user and their preferred genre(s).
     * 
     * @param name          the name of the user
     * @param databaseName  the name of the database
     * @return              a message indicating that the genre preference has been added
     */
    public String BucleCreateUserGenreConnection(String name, String databaseName) {
        try ( Session session = driver.session(SessionConfig.forDatabase(databaseName)) ) {
            String result = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Boolean boolGenres = true;
                    while (boolGenres) {
                        System.out.println("Géneros");
                        LinkedList<String> genres = getGenres(databaseName);
                        int n = 1;
                        for (String genre : genres) {
                            System.out.println(n + ". " + genre);
                            n++;
                        }
                        System.out.println("");
                        System.out.println(n + ". Dejar de añadir géneros");
                        System.out.println("");

                        System.out.println("Seleccione el género que le gusta: ");
                        int genreOption = Integer.parseInt(System.console().readLine());

                        if (genreOption == n) {
                            boolGenres = false;
                        } else {
                            tx.run("MATCH (u:User {name: $name}), (g:Genero {nombre: $genre}) CREATE (u)-[:LE_GUSTA]->(g)",
                                    parameters("name", name, "genre", genres.get(genreOption - 1)));
                        }
                    }
                    return "Gusto de género añadido";
                }
            });
            return result;
        }
    }

    /**
     * Creates a connection between a user and a series.
     * 
     * @param name The name of the user.
     * @param databaseName The name of the database.
     * @return A string indicating the result of the operation.
     */
    public String BucleCreateUserSeriesConnection(String name, String databaseName) {
        try ( Session session = driver.session(SessionConfig.forDatabase(databaseName)) ) {
            String result = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Boolean boolSeries = true;
                    while (boolSeries) {
                        System.out.println("Series");
                        LinkedList<String> series = getSeries(databaseName);
                        int n = 1;
                        for (String serie : series) {
                            System.out.println(n + ". " + serie);
                            n++;
                        }
                        System.out.println("");
                        System.out.println(n + ". Dejar de añadir series");
                        System.out.println("");

                        System.out.println("Seleccione la serie que ha visto: ");
                        int serieOption = Integer.parseInt(System.console().readLine());

                        if (serieOption == n) {
                            boolSeries = false;
                        } else {
                            tx.run("MATCH (u:User {name: $name}), (s:Series {title: $title}) CREATE (u)-[:LE_GUSTA]->(s)",
                                    parameters("name", name, "title", series.get(serieOption - 1)));
                        }
                    }
                    return "Serie añadida";
                }
            });
            return result;
        }
    }

    /**
     * Creates a user with the specified name and password in the given database.
     * 
     * @param name          the name of the user
     * @param password      the password of the user
     * @param databaseName  the name of the database
     * @return              true if the user was created successfully, false if the user already exists
     *                      or an error occurred during the creation process
     */
    public Boolean CreateUsers(String name, String password, String databaseName) {
    	try ( Session session = driver.session(SessionConfig.forDatabase(databaseName)) )
        {
   		 
   		 Boolean result = session.writeTransaction( new TransactionWork<Boolean>()
   		 
            {
                @Override
                public Boolean execute( Transaction tx )
                {
                    // Verificar si el usuario ya existe
                    Result result = tx.run( "MATCH (u:User {name: $name}) RETURN u",
                            parameters("name", name));
                    if (result.list().isEmpty()) {
                        tx.run( "CREATE (u:User {name: $name, password: $password})",
                                parameters("name", name, "password", password));
                        return true;
                    } else {
                        return false;
                    }
                }
            }
   		 
   		 );
            
            return result;
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a connection between a user and a series in the Neo4j database.
     * If the connection does not already exist, it will be created.
     * 
     * @param name          the name of the user
     * @param title         the title of the series
     * @param databaseName  the name of the database
     * @return              a string indicating the result of the operation:
     *                      - "Serie añadida" if the connection was successfully created
     *                      - "Ya existe la conexión" if the connection already exists
     *                      - the error message if an exception occurs
     */
    public String CreateUserSeriesConnection(String name, String title, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName))) {
            String result = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result userSeriesConnectionResult = tx.run("MATCH (u:User {name: $name})-[:LE_GUSTA]->(s:Series {title: $title}) RETURN u, s",
                            parameters("name", name, "title", title));
                    if (userSeriesConnectionResult.list().isEmpty()) {
                        tx.run("MATCH (u:User {name: $name}), (s:Series {title: $title}) CREATE (u)-[:LE_GUSTA]->(s)",
                                parameters("name", name, "title", title));
                        return "Serie añadida";
                    } else {
                        return "Ya existe la conexión";
                    }
                }
            });
    
            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    /**
     * Creates a connection between a user and a genre in the specified database.
     * 
     * @param name          the name of the user
     * @param genre         the name of the genre
     * @param databaseName  the name of the database
     * @return              a string indicating the result of the operation
     */
    public String CreateUserGenreConnection(String name, String genre, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName))) {
            String result = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result userGenreConnectionResult = tx.run("MATCH (u:User {name: $name})-[:LE_GUSTA]->(g:Genero {nombre: $genre}) RETURN u, g",
                            parameters("name", name, "genre", genre));
                    if (userGenreConnectionResult.list().isEmpty()) {
                        tx.run("MATCH (u:User {name: $name}), (g:Genero {nombre: $genre}) CREATE (u)-[:LE_GUSTA]->(g)",
                                parameters("name", name, "genre", genre));
                        return "Gusto de género añadido";
                    } else {
                        return "Ya existe la conexión";
                    }
                }
            });
    
            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    /**
     * Retrieves a list of all users from the specified database.
     *
     * @param databaseName the name of the database to retrieve users from
     * @return a LinkedList containing the names of all users in the database
     */
    public LinkedList<String> getallusers(String databaseName){
        try ( Session session = driver.session(SessionConfig.forDatabase(databaseName)) ) {
            LinkedList<String> users = session.readTransaction( new TransactionWork<LinkedList<String>>()
            {
                @Override
                public LinkedList<String> execute( Transaction tx )
                {
                    Result result = tx.run( "MATCH (u:User) RETURN u.name");
                    LinkedList<String> usersList = new LinkedList<String>();
                    List<Record> records = result.list();
                    for (Record record : records) {
                        usersList.add(record.get("u.name").asString());
                    }
                    return usersList;
                }
            } );

            return users;
        }
    }

    /**
     * Retrieves a list of genres from the specified database.
     *
     * @param databaseName the name of the database to retrieve genres from
     * @return a LinkedList containing the names of the genres
     */
    public LinkedList<String> getGenres(String databaseName){
        try ( Session session = driver.session(SessionConfig.forDatabase(databaseName)) ) {
            LinkedList<String> genres = session.readTransaction( new TransactionWork<LinkedList<String>>()
            {
                @Override
                public LinkedList<String> execute( Transaction tx )
                {
                    Result result = tx.run( "MATCH (g:Genero) RETURN g.nombre");
                    LinkedList<String> genresList = new LinkedList<String>();
                    List<Record> records = result.list();
                    for (Record record : records) {
                        genresList.add(record.get("g.nombre").asString());
                    }
                    return genresList;
                }
            } );
            return genres;
        }
    }
    
    /**
     * Retrieves a list of series titles from the specified database.
     *
     * @param databaseName the name of the database to retrieve series from
     * @return a LinkedList containing the titles of the series
     */
    public LinkedList<String> getSeries(String databaseName){
        try ( Session session = driver.session(SessionConfig.forDatabase(databaseName)) ) {
            LinkedList<String> series = session.readTransaction( new TransactionWork<LinkedList<String>>()
            {
                @Override
                public LinkedList<String> execute( Transaction tx )
                {
                    Result result = tx.run( "MATCH (s:Series) RETURN s.title");
                    LinkedList<String> seriesList = new LinkedList<String>();
                    List<Record> records = result.list();
                    for (Record record : records) {
                        seriesList.add(record.get("s.title").asString());
                    }
                    return seriesList;
                }
            } );
            return series;
        }
    }

    /**
     * Inserts a series into the Neo4j database.
     *
     * @param title         the title of the series
     * @param releaseYear   the release year of the series
     * @param tagline       the tagline of the series
     * @param databaseName  the name of the Neo4j database
     * @return              a message indicating the success or failure of the insertion
     */
    public String insertSeries(String title, String releaseYear, String tagline, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName))) {
            session.writeTransaction(new TransactionWork<Void>() {
                @Override
                public Void execute(Transaction tx) {
                    tx.run("CREATE (s:Series {title: $title, released: $releaseYear, tagline: $tagline})",
                            parameters("title", title, "releaseYear", releaseYear, "tagline", tagline));
                    return null;
                }
            });
            return "Serie insertada correctamente.";
        } catch (Exception e) {
            return "Error al insertar la serie: " + e.getMessage();
        }
    }

    /**
     * Creates a connection between a series and a genre in the specified database.
     * If the connection already exists, returns "La relación ya existe".
     * If the connection is successfully created, returns "Género añadido a serie".
     *
     * @param title        the title of the series
     * @param genre        the name of the genre
     * @param databaseName the name of the database
     * @return a string indicating the result of the operation
     */
    public String CreateSeriesGenresConnection(String title, String genre, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName))) {
            String result = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result existingRelationResult = tx.run(
                            "MATCH (s:Series {title: $title})-[:PERTENECE_A]->(g:Genero {nombre: $genre}) RETURN count(*)",
                            parameters("title", title, "genre", genre)
                    );
                    if (existingRelationResult.hasNext() && existingRelationResult.next().get(0).asInt() > 0) {
                        return "La relación ya existe";
                    } else {
                        tx.run("MATCH (s:Series {title: $title}), (g:Genero {nombre: $genre}) CREATE (s)-[:PERTENECE_A]->(g)",
                                parameters("title", title, "genre", genre));
                        return "Género añadido a serie";
                    }
                }
            });
    
            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    /**
     * Matches a genre to a series and creates a relationship between them in the specified database.
     *
     * @param genre         the genre to match
     * @param title         the title of the series
     * @param databaseName  the name of the database
     * @return              a message indicating the success of the operation or an error message
     */
    public String MatchGenretoSeries(String genre, String title, String databaseName) {
    	try ( Session session = driver.session(SessionConfig.forDatabase(databaseName)) )
        {
   		 
   		 String result = session.writeTransaction( new TransactionWork<String>()
   		 
            {
                @Override
                public String execute( Transaction tx )
                {
                    tx.run( "MATCH (g:Genero {nombre:'"+ genre + "'}), (s:Serie {title:'"+ title + "'}) CREATE (s)-[:PERTENECE_A]->(g)");
                    
                    return "Genre matched to series";
                }
            }
   		 
   		 );
            
            return result;
        } catch (Exception e) {
        	return e.getMessage();
        }
    }

    /**
     * Matches a user to a series and creates a relationship between them in the specified database.
     * 
     * @param name The name of the user.
     * @param title The title of the series.
     * @param databaseName The name of the database.
     * @return A string representing the result of the operation.
     */
    public String MatchUserToSeries(String name, String title, String databaseName) {
    	try ( Session session = driver.session(SessionConfig.forDatabase(databaseName)) )
        {
   		 
   		 String result = session.writeTransaction( new TransactionWork<String>()
   		 
            {
                @Override
                public String execute( Transaction tx )
                {
                    tx.run( "MATCH (u:Usuario {nombre:'"+ name + "'}), (s:Serie {title:'"+ title + "'}) CREATE (u)-[:VIO]->(s)");
                    
                    return null;
                }
            }
   		 
   		 );
            
            return result;
        } catch (Exception e) {
        	return e.getMessage();
        }
    }

    /**
     * Matches a user to a genre in the specified database.
     * 
     * @param name          the name of the user
     * @param genre         the name of the genre
     * @param databaseName  the name of the database
     * @return              the result of the transaction, or an error message if an exception occurs
     */
    public String MatchUserToGenre(String name, String genre, String databaseName) {
    	try ( Session session = driver.session(SessionConfig.forDatabase(databaseName)) )
        {
   		 
   		 String result = session.writeTransaction( new TransactionWork<String>()
   		 
            {
                @Override
                public String execute( Transaction tx )
                {
                    tx.run( "MATCH (u:User {name:'"+ name + "'}), (g:Genero {nombre:'"+ genre + "'}) CREATE (u)-[:LE_GUSTA]->(g)");
                    
                    return null;
                }
            }
   		 
   		 );
            
            return result;
        } catch (Exception e) {
        	return e.getMessage();
        }
    }

    /**
     * Retrieves a list of series titles based on the specified genre and database name.
     *
     * @param genre         the genre of the series
     * @param databaseName  the name of the database
     * @return              a LinkedList containing the titles of the series
     */
    public LinkedList<String> getSeriesByGenre(String genre, String databaseName){
        try ( Session session = driver.session(SessionConfig.forDatabase(databaseName)) ) {
            LinkedList<String> series = session.readTransaction( new TransactionWork<LinkedList<String>>()
            {
                @Override
                public LinkedList<String> execute( Transaction tx )
                {
                    Result result = tx.run( "MATCH (g:Genero {nombre:'"+ genre + "'})-[:PERTENECE_A]->(s:Serie) RETURN s.title");
                    LinkedList<String> seriesList = new LinkedList<String>();
                    List<Record> records = result.list();
                    for (Record record : records) {
                        seriesList.add(record.get("s.title").asString());
                    }
                    return seriesList;
                }
            } );
            return series;
        }
    }

    /**
     * Retrieves a list of series titles that are liked by a specific user from the specified database.
     *
     * @param name          the name of the user
     * @param databaseName  the name of the database
     * @return              a LinkedList of series titles liked by the user
     */
    public LinkedList<String> getSeriesByUser(String name, String databaseName){
        try ( Session session = driver.session(SessionConfig.forDatabase(databaseName)) ) {
            LinkedList<String> series = session.readTransaction( new TransactionWork<LinkedList<String>>()
            {
                @Override
                public LinkedList<String> execute( Transaction tx )
                {
                    Result result = tx.run( "MATCH (u:User {name:'"+ name + "'})-[:LE_GUSTA]->(s:Series) RETURN s.title");
                    LinkedList<String> seriesList = new LinkedList<String>();
                    List<Record> records = result.list();
                    for (Record record : records) {
                        seriesList.add(record.get("s.title").asString());
                    }
                    return seriesList;
                }
            } );
            return series;
        }
    }

    /**
     * Retrieves the genres liked by a user from the specified database.
     *
     * @param name          the name of the user
     * @param databaseName  the name of the database
     * @return              a LinkedList containing the names of the genres liked by the user
     */
    public LinkedList<String> getGenresByUser(String name, String databaseName){
        try ( Session session = driver.session(SessionConfig.forDatabase(databaseName)) ) {
            LinkedList<String> genres = session.readTransaction( new TransactionWork<LinkedList<String>>()
            {
                @Override
                public LinkedList<String> execute( Transaction tx )
                {
                    Result result = tx.run( "MATCH (u:User {name:'"+ name + "'})-[:LE_GUSTA]->(g:Genero) RETURN g.nombre");
                    LinkedList<String> genresList = new LinkedList<String>();
                    List<Record> records = result.list();
                    for (Record record : records) {
                        genresList.add(record.get("g.nombre").asString());
                    }
                    return genresList;
                }
            } );
            return genres;
        }
    }

    /**
     * Counts the number of series liked by a user.
     *
     * @param name          the name of the user
     * @param databaseName  the name of the database
     * @return the number of series liked by the user
     */
    public int countSeriesByUser(String name, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName)) ) {
            return session.readTransaction(tx -> {
                Result result = tx.run("MATCH (u:User {name: $name})-[:LE_GUSTA]->(s:Series) RETURN count(s)", 
                                        parameters("name", name));
                return result.single().get(0).asInt();
            });
        }
    }
    
    /**
     * Counts the number of genres liked by a user.
     *
     * @param name          the name of the user
     * @param databaseName  the name of the database
     * @return the number of genres liked by the user
     */
    public int countGenresByUser(String name, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName)) ) {
            return session.readTransaction(tx -> {
                Result result = tx.run("MATCH (u:User {name: $name})-[:LE_GUSTA]->(g:Genero) RETURN count(g)", 
                                        parameters("name", name));
                return result.single().get(0).asInt();
            });
        }
    }
    
    /**
     * Counts the total number of connections (series and genres) for a given user in a specified database.
     *
     * @param name          the name of the user
     * @param databaseName  the name of the database
     * @return the total number of connections for the user
     */
    public int countConnectionsByUser(String name, String databaseName) {
        int series = countSeriesByUser(name, databaseName);
        int genres = countGenresByUser(name, databaseName);
        return series + genres;
    }

    /**
     * Compares the number of series liked by two users.
     *
     * @param name1         the name of the first user
     * @param name2         the name of the second user
     * @param databaseName  the name of the database to connect to
     * @return              the number of series liked by both users
     */
    public int compareSeriesByUser(String name1, String name2, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName)) ) {
            return session.readTransaction(tx -> {
                Result result = tx.run("MATCH (u1:User {name: $name1})-[:LE_GUSTA]->(s:Series)<-[:LE_GUSTA]-(u2:User {name: $name2}) RETURN count(s)", 
                                        parameters("name1", name1, "name2", name2));
                return result.single().get(0).asInt();
            });
        }
    }

    /**
     * Compares the number of genres liked by two users.
     *
     * @param name1         the name of the first user
     * @param name2         the name of the second user
     * @param databaseName  the name of the database to connect to
     * @return              the number of genres liked by both users
     */
    public int compareGenresByUser(String name1, String name2, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName)) ) {
            return session.readTransaction(tx -> {
                Result result = tx.run("MATCH (u1:User {name: $name1})-[:LE_GUSTA]->(g:Genero)<-[:LE_GUSTA]-(u2:User {name: $name2}) RETURN count(g)", 
                                        parameters("name1", name1, "name2", name2));
                return result.single().get(0).asInt();
            });
        }
    }

    /**
     * Compares the connections between two users based on their names and the specified database.
     * 
     * @param name1         the name of the first user
     * @param name2         the name of the second user
     * @param databaseName  the name of the database to compare the connections from
     * @return              the sum of the comparisons between the series and genres connections
     */
    public int compareConnectionsByUser(String name1, String name2, String databaseName) {
        int series = compareSeriesByUser(name1, name2, databaseName);
        int genres = compareGenresByUser(name1, name2, databaseName);
        return series + genres;
    }

    /**
     * Finds the most similar user to the given user based on series and genres.
     *
     * @param name          the name of the user
     * @param databaseName  the name of the database
     * @return              the name of the most similar user, or a message if no similar user is found
     */
    public String findMostSimilarUser(String name, String databaseName) {
        LinkedList<String> seriesByUser = getSeriesByUser(name, databaseName);
        LinkedList<String> genresByUser = getGenresByUser(name, databaseName);
        
        String mostSimilarUser = "";
        double highestJaccardIndex = 0.0;
    
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName))) {
            List<Record> users = session.readTransaction(new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute(Transaction tx) {
                    Result result = tx.run("MATCH (u:User) WHERE u.name <> $name RETURN u.name", 
                                           parameters("name", name));
                    return result.list(); // Consume the result within the transaction
                }
            });
    
            if (users.isEmpty()) {
                return "No other users found";
            }
    
            for (Record record : users) {
                String userName = record.get("u.name").asString();
                LinkedList<String> seriesByOtherUser = getSeriesByUser(userName, databaseName);
                LinkedList<String> genresByOtherUser = getGenresByUser(userName, databaseName);
    
                double jaccardIndex = calculateJaccardIndex(seriesByUser, seriesByOtherUser, genresByUser, genresByOtherUser);
                if (jaccardIndex > highestJaccardIndex) {
                    highestJaccardIndex = jaccardIndex;
                    mostSimilarUser = userName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return mostSimilarUser.isEmpty() ? "No similar user found" : mostSimilarUser;
    }   
    
    /**
     * Calculates the Jaccard Index between two series and two genres.
     *
     * @param series1 the first series
     * @param series2 the second series
     * @param genres1 the first genres
     * @param genres2 the second genres
     * @return the Jaccard Index between the series and genres
     */
    public double calculateJaccardIndex(LinkedList<String> series1, LinkedList<String> series2, LinkedList<String> genres1, LinkedList<String> genres2) {
        int seriesIntersection = 0;
        int seriesUnion = series1.size() + series2.size();
    
        for (String serie : series1) {
            if (series2.contains(serie)) {
                seriesIntersection++;
                seriesUnion--;
            }
        }
    
        int genresIntersection = 0;
        int genresUnion = genres1.size() + genres2.size();
    
        for (String genre : genres1) {
            if (genres2.contains(genre)) {
                genresIntersection++;
                genresUnion--;
            }
        }
    
        double jaccardIndex = (double) (seriesIntersection + genresIntersection) / (seriesUnion + genresUnion + seriesIntersection + genresIntersection);
        return jaccardIndex;
    }

    /**
     * Deletes the connection between a user and a series in the specified database.
     * 
     * @param name          the name of the user
     * @param title         the title of the series
     * @param databaseName  the name of the database
     * @return              a string indicating the result of the operation:
     *                      - "No existe la conexión" if the connection does not exist
     *                      - "Conexión eliminada" if the connection was successfully deleted
     *                      - the error message if an exception occurs
     */
    public String deleteSeriesUserConnection(String name, String title, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName))) {
            String result = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result userSeriesConnectionResult = tx.run("MATCH (u:User {name: $name})-[r:LE_GUSTA]->(s:Series {title: $title}) RETURN u, s, r",
                            parameters("name", name, "title", title));
                    if (userSeriesConnectionResult.list().isEmpty()) {
                        return "No existe la conexión";
                    } else {
                        tx.run("MATCH (u:User {name: $name})-[r:LE_GUSTA]->(s:Series {title: $title}) DELETE r",
                                parameters("name", name, "title", title));
                        return "Conexión eliminada";
                    }
                }
            });
    
            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Deletes the connection between a user and a genre in the specified database.
     * 
     * @param name          the name of the user
     * @param genre         the name of the genre
     * @param databaseName  the name of the database
     * @return              a string indicating the result of the operation
     */
    public String deleteGenreUserConnection(String name, String genre, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName))) {
            String result = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result userGenreConnectionResult = tx.run("MATCH (u:User {name: $name})-[r:LE_GUSTA]->(g:Genero {nombre: $genre}) RETURN u, g, r",
                            parameters("name", name, "genre", genre));
                    if (userGenreConnectionResult.list().isEmpty()) {
                        return "No existe la conexión";
                    } else {
                        tx.run("MATCH (u:User {name: $name})-[r:LE_GUSTA]->(g:Genero {nombre: $genre}) DELETE r",
                                parameters("name", name, "genre", genre));
                        return "Conexión eliminada";
                    }
                }
            });
    
            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Retrieves the connections of a user from the specified database and returns them as a formatted string.
     *
     * @param name          the name of the user
     * @param databaseName  the name of the database
     * @return              a string containing the user's connections, including series and genres
     */
    public String showConnectionsUser(String name, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName))) {
            String result = session.readTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result seriesResult = tx.run("MATCH (u:User {name: $name})-[:LE_GUSTA]->(s:Series) RETURN s.title",
                            parameters("name", name));
                    Result genresResult = tx.run("MATCH (u:User {name: $name})-[:LE_GUSTA]->(g:Genero) RETURN g.nombre",
                            parameters("name", name));
                    String series = "Series: \n";
                    String genres = "Géneros: \n";
                    List<Record> seriesRecords = seriesResult.list();
                    List<Record> genresRecords = genresResult.list();
                    for (Record record : seriesRecords) {
                        series += record.get("s.title").asString() + "\n";
                    }
                    for (Record record : genresRecords) {
                        genres += record.get("g.nombre").asString() + "\n";
                    }
                    return series + "\n" + genres;
                }
            });
            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
