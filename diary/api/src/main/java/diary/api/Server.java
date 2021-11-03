package diary.api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

//import diary.core.Entry;

@WebServlet("/getDiary/*")
public class Server extends HttpServlet {
    private static final Charset charset = StandardCharsets.UTF_8;
    String lastPost = ""; //Testvariable to show permanence
    int requests = 0;
    
    private static String inputStreamToString(InputStream inputStream) {
        String responseBody = "";
        try (Scanner scanner = new Scanner(inputStream, charset)) {
            responseBody += scanner.useDelimiter("\\A").next();
            System.out.println(responseBody);
        }
        return responseBody.trim();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws IOException, ServletException {
        requests++;
        
        String requestUrl = request.getRequestURI();
        String name = requestUrl.substring("/api/getDiary/".length());
        name = "Requested Diary json file was: " + name;
        name += "\n\nTests for more lines\n\n" + requests 
            + "\n\nLast Post: " + lastPost;
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
            //That person wasn't found, so return an empty JSON object.
            response.getOutputStream().println("{}");
        }*/
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException {
        requests += 1000;
        lastPost = inputStreamToString(req.getInputStream());


        //InputStream is = req.getInputStream();
        //lastPost = inputStreamToString(is);
        //resp.getOutputStream().println("Response\n\nPost body: "+inputStreamToString(is));
        //out.println(inputStreamToString(is));
        
    }
}
