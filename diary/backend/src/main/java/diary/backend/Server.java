package diary.backend;

import diary.json.EntryFromJSON;
import diary.json.EntryToJSON;
import diary.json.PersistanceUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Server extends HttpServlet {
    private static final Charset charset = StandardCharsets.UTF_8;
    String lastPost = ""; //Testvariable to show permanence
    int requests = 0;
    private static final String url = "/api/diary/";

    private static String inputStreamToString(InputStream inputStream) {
        String responseBody = "";
        try (Scanner scanner = new Scanner(inputStream, charset)) {
            responseBody += scanner.useDelimiter("\\A").next();
        }
        return responseBody.trim();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws IOException, ServletException {
        //For testing with doPost results
        requests++;

        String requestUrl = request.getRequestURI();
        String fileName = requestUrl.substring(url.length());
        String queryString = request.getQueryString();

        if (queryString != null && queryString.equals("getFileNames")) {
            //Requesting diarynames that start with fileName
            List<String> fileNames = PersistanceUtil.getFilesStartingWith(fileName, false);
            for (int i = 0; i < fileNames.size(); i++) {
                fileNames.set(i, fileNames.get(i).substring(0, 
                    fileNames.get(i).length() - 5));
            }
            response.getOutputStream().println(fileNames.toString());
        } else if (fileName.trim().equals("ServerStatus")) {
            //Requesting serverStatus info
            response.getOutputStream().println(requests + "\n\n" + lastPost);
        } else {
            //Requesting content of fileName
            String fileContents = EntryFromJSON.readToString(fileName, false);
            response.getOutputStream().println(fileContents);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        
        String diaryContent = inputStreamToString(request.getInputStream());
        String requestUrl = request.getRequestURI();
        String fileName = requestUrl.substring(url.length());

        //For ServerStatus command
        requests += 1000;
        lastPost = "Filename: " + fileName + "\nFile:\n" + diaryContent;

        // Run String to File method
        EntryToJSON.write(fileName, diaryContent, false);
    }
}
