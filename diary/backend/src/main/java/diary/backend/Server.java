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
    private static final String separator = "%%NEXT%%";

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
        requests++;
        //For testing with doPost results
        //response.getOutputStream().print(System.getProperty("user.dir"));

        
        String requestUrl = request.getRequestURI();
        String fileNameStart = requestUrl.substring(url.length());

        if (fileNameStart.trim().equals("ServerStatus")) {
            response.getOutputStream().println(requests + "\n\n" + lastPost);
            return;
        }
        
        // Run method that gets list of filenames that starts with fileNameStart
        List<String> fileNames = PersistanceUtil.getFilesStartingWith(fileNameStart, false);
        
        // Run method to get filename from filecontent
        String[] fileContents = new String[fileNames.size()];
        for (int i = 0; i < fileNames.size(); i++) {
            //Remove .json from the filenames
            fileNames.set(i, fileNames.get(i).substring(0, fileNames.get(i).length() - 5));
            fileContents[i] = EntryFromJSON.readToString(fileNames.get(i), false);
        }

        StringBuilder returnString = new StringBuilder();
        for (int i = 0; i < fileNames.size(); i++) {
            if (i != 0) { //Add separator except at the beginning
                returnString.append(separator);
            }
            returnString.append(fileNames.get(i));
            returnString.append(separator);
            returnString.append(fileContents[i]);
        }

        response.getOutputStream().println(returnString.toString());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        requests += 1000;


        String diaryContent = inputStreamToString(request.getInputStream());
        String requestUrl = request.getRequestURI();
        String fileName = requestUrl.substring(url.length());

        //For ServerStatus command
        lastPost = "Filename: " + fileName + "\nFile:\n" + diaryContent;

        // Run String to File method
        EntryToJSON.write(fileName, diaryContent, false);
    }
}
