package diary.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//import diary.core.Entry;

@WebServlet("/getDiary/*")
public class Server extends HttpServlet  {
	String lastPost = "";//To show that the server stores the info sent in post and retrieves the info in get:)
	int requests = 0;
	
	private static String inputStreamToString(InputStream inputStream){
        String responseBody = "";
        try (Scanner scanner = new Scanner(inputStream)) {
            responseBody += scanner.useDelimiter("\\A").next();
            System.out.println(responseBody);
        }
        responseBody.trim();
        return responseBody;
    }

    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		requests++;
		
		String requestUrl = request.getRequestURI();
		String name = requestUrl.substring("/api/getDiary/".length());
        name = "Requested Diary json file was: " + name;
		name += "\n\nTests for more lines\n\n"+requests+"\n\nLast Post: "+lastPost;
        response.getOutputStream().println(name);
		
		/*Person person = DataStore.getInstance().getPerson(name);
		
		if(person != null){
			String json = "{\n";
			json += "\"name\": " + JSONObject.quote(person.getName()) + ",\n";
			json += "\"about\": " + JSONObject.quote(person.getAbout()) + ",\n";
			json += "\"birthYear\": " + person.getBirthYear() + "\n";
			json += "}";
			response.getOutputStream().println(json);
		}
		else{
			//That person wasn't found, so return an empty JSON object. We could also return an error.
			response.getOutputStream().println("{}");
		}*/
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		requests += 1000;
		lastPost = inputStreamToString(req.getInputStream());


		//InputStream is = req.getInputStream();
		//lastPost = inputStreamToString(is);
		//resp.getOutputStream().println("Response\n\nPost body: "+inputStreamToString(is));
		//out.println(inputStreamToString(is));
		
	}
}
