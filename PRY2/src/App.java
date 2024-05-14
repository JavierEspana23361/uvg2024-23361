import java.util.LinkedList;

public class App {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "bolt://localhost:7687";
		String user = "neo4j";
		String password = "password";
		try (EmbeddedNeo4j db = new EmbeddedNeo4j(url, user, password))
        {
		 	LinkedList<String> genres = db.getGenres();
		 	
		 	for (int i = 0; i < genres.size(); i++) {
		 		System.out.println(genres.get(i));
		 	}
        	
        } catch (Exception e) {
			e.printStackTrace();
		}

		try (EmbeddedNeo4j db = new EmbeddedNeo4j(url, user, password))
		{
		 	LinkedList<String> series = db.getSeries();
		 	
		 	for (int i = 0; i < series.size(); i++) {
		 		System.out.println(series.get(i));
		 	}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (EmbeddedNeo4j db = new EmbeddedNeo4j(url, user, password))
		{
			String title = "Ben 10";
			int releaseYear = 2005;
			String tagline = "It's hero time!";
			String result = db.insertSeries(title, releaseYear, tagline);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (EmbeddedNeo4j db = new EmbeddedNeo4j(url, user, password))
		{
			String genre = "Action";
			String title = "Ben 10";
			String result = db.MatchGenretoSeries(genre, title);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}




	}

}
