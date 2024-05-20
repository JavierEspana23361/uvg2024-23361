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

    public LinkedList<String> getUsers(String databaseName){
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

    public String insertSeries(String title, int releaseYear, String tagline) {
    	try ( Session session = driver.session() )
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

    public String MatchGenretoSeries(String genre, String title) {
    	try ( Session session = driver.session() )
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

    public String CreateUsers(String name, String age) {
    	try ( Session session = driver.session() )
        {
   		 
   		 String result = session.writeTransaction( new TransactionWork<String>()
   		 
            {
                @Override
                public String execute( Transaction tx )
                {
                    tx.run( "CREATE (u:Usuario {nombre:'" + name + "', edad:'"+ age +"'})");
                    
                    return null;
                }
            }
   		 
   		 );
            
            return result;
        } catch (Exception e) {
        	return e.getMessage();
        }
    }

    public String MatchUserToSeries(String name, String title) {
    	try ( Session session = driver.session() )
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

    public String MatchUserToGenre(String name, String genre) {
    	try ( Session session = driver.session() )
        {
   		 
   		 String result = session.writeTransaction( new TransactionWork<String>()
   		 
            {
                @Override
                public String execute( Transaction tx )
                {
                    tx.run( "MATCH (u:Usuario {nombre:'"+ name + "'}), (g:Genero {nombre:'"+ genre + "'}) CREATE (u)-[:VIO]->(g)");
                    
                    return null;
                }
            }
   		 
   		 );
            
            return result;
        } catch (Exception e) {
        	return e.getMessage();
        }
    }

    public LinkedList<String> getSeriesByGenre(String genre){
        try ( Session session = driver.session() ) {
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

    public LinkedList<String> getSeriesByUser(String name){
        try ( Session session = driver.session() ) {
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

    public LinkedList<String> getGenresByUser(String name){
        try ( Session session = driver.session() ) {
            LinkedList<String> genres = session.readTransaction( new TransactionWork<LinkedList<String>>()
            {
                @Override
                public LinkedList<String> execute( Transaction tx )
                {
                    Result result = tx.run( "MATCH (u:Usuario {nombre:'"+ name + "'})-[:VIO]->(g:Genero) RETURN g.nombre");
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
}
