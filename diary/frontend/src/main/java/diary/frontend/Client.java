package diary.frontend;

import diary.json.EntryFromJSON;
import diary.json.EntryToJSON;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public final class Client {
    private static final Charset charset = StandardCharsets.UTF_8;
    private static final String url = "http://localhost:8080/api/diary/";
    private static final Boolean writeLocal = true;
    //This one determines wether we write in core/../resources or user.dir
    //User.dir defaults to diary/, which we use in the tests


    private static String inputStreamToString(InputStream inputStream) {
        String responseBody = "";
        try (Scanner scanner = new Scanner(inputStream, charset)) {
            responseBody += scanner.useDelimiter("\\A").next();
        }
        return responseBody.trim();
    }

    /**
     * Sends a get request to localhost with urlEnd at the end of the url
     * Gets called by getDiaries()
     * @param urlEnd String to append to url, the start of the filename
     * @return The string the server returns, null if there is an error
     */
    public static String sendGET(String urlEnd) {
        try {
            URLConnection connection = new URL(url + urlEnd).openConnection();
            connection.setRequestProperty("Accept-Charset", charset.toString());
            return inputStreamToString(connection.getInputStream());
        } catch (IOException e) {
            System.out.println("Couldn't connect to server with get, urlEnd: " + urlEnd);
        }
        return "";
    }

    /**
     * Retrieves all diaries from the server that start with startOfDiaryNames. 
     * Uses EntryFromJSON to read backend and EntryToJSON to write frontend.
     * @param startOfDiaryNames Diaries starting with this(without .json) gets retrieved
     */
    public static void getDiaries(String startOfDiaryNames) {
        //Get string json list of diaryNames
        String names = sendGET(startOfDiaryNames + "?getFileNames");
        if (names.equals("") || names.equals("[]")) {
            //No diaries found, return to controller
            return;
        }
        //Convert to actual list
        String[] diaryNames = names.substring(1, names.length() - 1).split(", ");

        //Get every diary
        for (String diaryName : diaryNames) {
            String diaryContent = sendGET(diaryName);
            try {
                //Write to local
                EntryToJSON.write(diaryName, diaryContent, writeLocal);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends a get request to localhost with urlEnd at the end of the url
     * @param urlEnd String to append to url, the start of the filename
     * @param content The content of the post request, the diary content
     */
    public static void sendPOST(String urlEnd, String content) {
        try {
            URLConnection connection = new URL(url + urlEnd).openConnection();
            connection.setDoOutput(true); // Triggers POST
            connection.setRequestProperty("Accept-Charset", charset.toString());
            
            try (OutputStream output = connection.getOutputStream()) {
                output.write(content.getBytes(charset));
            }
            connection.getInputStream();
        } catch (IOException e) {
            System.out.println("Couldn't connect to server with post");
        }

        return;
    }

    /**
     * Sends the diary named diaryName to be saved on server. 
     * Uses EntryFromJSON to read frontend and EntryToJSON to write backend. 
     * @param diaryName Diaries named this(without .json) gets sent
     */
    public static void postDiary(String diaryName) {
        String content;
        try {
            content = EntryFromJSON.readToString(diaryName, writeLocal);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (0 < content.length()) {
            sendPOST(diaryName, content);
        } else {
            System.out.println("Read diary had a length of 0");
        }
    }
}
