
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

    public Boolean login(String uri, String name, String password, String username, String pass, String databaseName) {
        try (EmbeddedNeo4j db = new EmbeddedNeo4j(uri, name, password)) {
            return db.foundUser(username, pass, databaseName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

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

    public String insertSeries(String title, String releaseYear, String tagline, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName))) {
            session.writeTransaction(new TransactionWork<Void>() {
                @Override
                public Void execute(Transaction tx) {
                    tx.run("CREATE (s:Serie {title: $title, released: $releaseYear, tagline: $tagline})",
                            parameters("title", title, "releaseYear", releaseYear, "tagline", tagline));
                    return null;
                }
            });
            return "Serie insertada correctamente.";
        } catch (Exception e) {
            return "Error al insertar la serie: " + e.getMessage();
        }
    }

    public String CreateSeriesGenresConnection(String title, String genre, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName))) {
            String result = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result existingRelationResult = tx.run(
                            "MATCH (s:Serie {title: $title})-[:PERTENECE_A]->(g:Genero {nombre: $genre}) RETURN count(*)",
                            parameters("title", title, "genre", genre)
                    );
                    if (existingRelationResult.hasNext() && existingRelationResult.next().get(0).asInt() > 0) {
                        return "La relación ya existe";
                    } else {
                        tx.run("MATCH (s:Serie {title: $title}), (g:Genero {nombre: $genre}) CREATE (s)-[:PERTENECE_A]->(g)",
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

    public LinkedList<String> getGenresByUser(String name, String databaseName){
        try ( Session session = driver.session(SessionConfig.forDatabase(databaseName)) ) {
            LinkedList<String> genres = session.readTransaction( new TransactionWork<LinkedList<String>>()
            {
                @Override
                public LinkedList<String> execute( Transaction tx )
                {
                    Result result = tx.run( "MATCH (u:User {name:'"+ name + "'})-[:LE_GUSTA]->(g:Genero) RETURN g.name");
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

    public int countSeriesByUser(String name, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName)) ) {
            return session.readTransaction(tx -> {
                Result result = tx.run("MATCH (u:User {name: $name})-[:LE_GUSTA]->(s:Series) RETURN count(s)", 
                                        parameters("name", name));
                return result.single().get(0).asInt();
            });
        }
    }
    
    public int countGenresByUser(String name, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName)) ) {
            return session.readTransaction(tx -> {
                Result result = tx.run("MATCH (u:User {name: $name})-[:LE_GUSTA]->(g:Genero) RETURN count(g)", 
                                        parameters("name", name));
                return result.single().get(0).asInt();
            });
        }
    }
    
    public int countConnectionsByUser(String name, String databaseName) {
        int series = countSeriesByUser(name, databaseName);
        int genres = countGenresByUser(name, databaseName);
        return series + genres;
    }

    public int compareSeriesByUser(String name1, String name2, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName)) ) {
            return session.readTransaction(tx -> {
                Result result = tx.run("MATCH (u1:User {name: $name1})-[:LE_GUSTA]->(s:Series)<-[:LE_GUSTA]-(u2:User {name: $name2}) RETURN count(s)", 
                                        parameters("name1", name1, "name2", name2));
                return result.single().get(0).asInt();
            });
        }
    }

    public int compareGenresByUser(String name1, String name2, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName)) ) {
            return session.readTransaction(tx -> {
                Result result = tx.run("MATCH (u1:User {name: $name1})-[:LE_GUSTA]->(g:Genero)<-[:LE_GUSTA]-(u2:User {name: $name2}) RETURN count(g)", 
                                        parameters("name1", name1, "name2", name2));
                return result.single().get(0).asInt();
            });
        }
    }

    public int compareConnectionsByUser(String name1, String name2, String databaseName) {
        int series = compareSeriesByUser(name1, name2, databaseName);
        int genres = compareGenresByUser(name1, name2, databaseName);
        return series + genres;
    }

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
}
