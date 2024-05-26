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
			System.out.println("Ingrese su nombre de usuario: ");
			String name = System.console().readLine();
			System.out.println("Ingrese su contraseña: ");
			String pass = System.console().readLine();
			String result = db.CreateUsers(name, pass, databaseName);

            System.out.println("Desea añadir géneros a su perfil?");
            System.out.println("1. Sí");
            System.out.println("2. No");
            int option = Integer.parseInt(System.console().readLine());

            if (option == 1) {
                result = db.BucleCreateUserGenreConnetion(name, pass, databaseName);
            }

            System.out.println("Desea añadir series a su perfil?");
            System.out.println("1. Sí");
            System.out.println("2. No");
            option = Integer.parseInt(System.console().readLine());

            if (option == 1) {
                result = db.BucleCreateUserSeriesConnetion(name, pass, databaseName);
            }
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} 
		return null;
	}

    public String BucleCreateUserGenreConnetion(String name, String password, String databaseName) {
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
                            tx.run("MATCH (u:User {name: $name, password: $password}), (g:Genero {nombre: $genre}) CREATE (u)-[:LE_GUSTA]->(g)",
                                    parameters("name", name, "password", password, "genre", genres.get(genreOption - 1)));
                        }
                    }
                    return "Gusto de género añadido";
                }
            });
            return result;
        }
    }

    public String BucleCreateUserSeriesConnetion(String name, String password, String databaseName) {
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
                            tx.run("MATCH (u:User {name: $name, password: $password}), (s:Series {title: $title}) CREATE (u)-[:LE_GUSTA]->(s)",
                                    parameters("name", name, "password", password, "title", series.get(serieOption - 1)));
                        }
                    }
                    return "Serie añadida";
                }
            });
            return result;
        }
    }

    public String CreateUsers(String name, String password, String databaseName) {
    	try ( Session session = driver.session(SessionConfig.forDatabase(databaseName)) )
        {
   		 
   		 String result = session.writeTransaction( new TransactionWork<String>()
   		 
            {
                @Override
                public String execute( Transaction tx )
                {
                    // Verificar si el usuario ya existe
                    Result result = tx.run( "MATCH (u:User {name: $name, password: $password}) RETURN u",
                            parameters("name", name, "password", password));
                    if (result.list().isEmpty()) {
                        tx.run( "CREATE (u:User {name: $name, password: $password})",
                                parameters("name", name, "password", password));
                        return "Usuario creado";
                    } else {
                        return "El usuario ya existe";
                    }
                }
            }
   		 
   		 );
            
            return result;
        } catch (Exception e) {
        	return e.getMessage();
        }
    }

    public String CreateUserSeriesConnection(String name, String password, String title, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName))) {
            String result = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result userSeriesResult = tx.run("MATCH (u:User {name: $name, password: $password}), (s:Series {title: $title}) RETURN u, s",
                            parameters("name", name, "password", password, "title", title));
                    if (userSeriesResult.list().isEmpty()) {
                        return "No existe el usuario o la serie";
                    } else {
                        Result userSeriesConnectionResult = tx.run("MATCH (u:User {name: $name, password: $password})-[:VIO]->(s:Series {title: $title}) RETURN u, s",
                                parameters("name", name, "password", password, "title", title));
                        if (userSeriesConnectionResult.list().isEmpty()) {
                            tx.run("MATCH (u:User {name: $name, password: $password}), (s:Series {title: $title}) CREATE (u)-[:VIO]->(s)",
                                    parameters("name", name, "password", password, "title", title));
                            return "Serie añadida";
                        } else {
                            return "Ya existe la conexión";
                        }
                    }
                }
            });
    
            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public String CreateUserGenreConnection(String name, String password, String genre, String databaseName) {
        try (Session session = driver.session(SessionConfig.forDatabase(databaseName))) {
            String result = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result userGenreResult = tx.run("MATCH (u:User {name: $name, password: $password}), (g:Genero {nombre: $genre}) RETURN u, g",
                            parameters("name", name, "password", password, "genre", genre));
                    if (userGenreResult.list().isEmpty()) {
                        return "No existe el usuario o el género";
                    } else {
                        Result userGenreConnectionResult = tx.run("MATCH (u:User {name: $name, password: $password})-[:LE_GUSTA]->(g:Genero {nombre: $genre}) RETURN u, g",
                                parameters("name", name, "password", password, "genre", genre));
                        if (userGenreConnectionResult.list().isEmpty()) {
                            tx.run("MATCH (u:User {name: $name, password: $password}), (g:Genero {nombre: $genre}) CREATE (u)-[:LE_GUSTA]->(g)",
                                    parameters("name", name, "password", password, "genre", genre));
                            return "Gusto de género añadido";
                        } else {
                            return "Ya existe la conexión";
                        }
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

    public String insertSeries(String title, int releaseYear, String tagline, String databaseName) {
    	try ( Session session = driver.session(SessionConfig.forDatabase(databaseName)) )
        {
   		 
   		 String result = session.writeTransaction( new TransactionWork<String>()
   		 
            {
                @Override
                public String execute( Transaction tx )
                {
                    tx.run( "CREATE (Test:Serie {title:'" + title + "', released:"+ releaseYear +", tagline:'"+ tagline +"'})");
                    
                    return null;
                }
            }
   		 
   		 );
            
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
                    Result result = tx.run( "MATCH (u:Usuario {nombre:'"+ name + "'})-[:VIO]->(s:Serie) RETURN s.title");
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

    public double calculateJaccardIndex(String name1, String name2, String databaseName) {
        int connections = compareConnectionsByUser(name1, name2, databaseName);
        
        LinkedList<String> series1 = getSeriesByUser(name1, databaseName);
        LinkedList<String> series2 = getSeriesByUser(name2, databaseName);

        LinkedList<String> genres1 = getGenresByUser(name1, databaseName);
        LinkedList<String> genres2 = getGenresByUser(name2, databaseName);

        int countSeries = series1.size() + series2.size();
        int countGenres = genres1.size() + genres2.size();

        for (String serie : series1) {
            if (series2.contains(serie)) {
                countSeries--;
            }
        }

        for (String genre : genres1) {
            if (genres2.contains(genre)) {
                countGenres--;
            }
        }

        return (double) connections / (countSeries + countGenres + connections);
    }
}
