import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Alone {

	public static void main(String[] args) throws IOException,
			org.json.simple.parser.ParseException {

		FileReader reader = new FileReader("books.json");
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
		JSONArray books = (JSONArray) jsonObject.get("books");

		ArrayList<String> authors = new ArrayList<String>();//автори без повторень
		ArrayList<String> coauthors = new ArrayList<String>(); // список авторів, які видали у співавторстві
		ArrayList<String> authorsList = new ArrayList<String>();//загальний список авторів
		ArrayList<String> booksList = new ArrayList<String>();//загальний список книг

		Iterator<?> q = books.iterator();
		while (q.hasNext()) {
			JSONObject innerObj = (JSONObject) q.next();
			JSONArray author = (JSONArray) innerObj.get("authors");

			for (int k = 0; k < author.size(); k++) {
				authorsList.add((String) author.get(k));
				authors.add((String) author.get(k));
				booksList.add((String) innerObj.get("title_name"));
			}

			for (int n = 0; n < authors.size(); n++) {
				for (int l = n + 1; l < authors.size(); l++) {
					if (authors.get(n).equals(authors.get(l)))
						authors.remove(l);
				}
			}
			
			for (int n = 0; n < booksList.size(); n++) {
				for (int l = n + 1; l < booksList.size(); l++) {
					if (booksList.get(n).equals(booksList.get(l))) {
						coauthors.add(authorsList.get(n));
						coauthors.add(authorsList.get(l));
						booksList.remove(l);
						authorsList.remove(l);
					} else
						break;
				}
			}
		}

		System.out.println("Автори, які не видали жодної книги в співавторстві: ");
		
		for (String namAuth:authors) {
			int count = 0;
			for (String namCoauth:coauthors) {
				if (namAuth.equals(namCoauth))
					count++;}
			if (count == 0)
				System.out.println(namAuth);
		}
	}
}
