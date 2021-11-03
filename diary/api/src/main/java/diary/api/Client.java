package diary.api;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.Scanner;
// import java.net.HttpURLConnection;
import java.net.URL;

public final class Client {
    private static final String charset = "UTF-8";

    private static String inputStreamToString(InputStream inputStream){
        String responseBody = "";
        try (Scanner scanner = new Scanner(inputStream)) {
            responseBody += scanner.useDelimiter("\\A").next();
        }
        responseBody.trim();
        return responseBody;
    }

    public static String sendGET(String url){
        try{
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            return inputStreamToString(response);
            
        }catch(Exception e){}
        return "";
    }

    public static void sendPOST(String url, String content){
        try{
            URLConnection connection = new URL(url).openConnection();
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", charset);
            //connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
    
            try (OutputStream output = connection.getOutputStream()) {
                output.write(content.getBytes(charset));
            }

            //connection.connect();

            connection.getInputStream();


            // InputStream response = connection.getInputStream();
            // System.out.println(inputStreamToString(response));
            // System.out.println("-----------End of request");
            // ...
        }catch(Exception e){}
    }

    public static void main(String[] args) {
        sendPOST("http://localhost:8080/api/getDiary/MyDiary.json", "TESTCONTENT1235");
        System.out.println(sendGET("http://localhost:8080/api/getDiary/MyDiary.json"));
        



        // URL url = new URL("http://www.y.com/url");
        // InputStream is = url.openStream();
        // url.
        // try {
        // /* Now read the retrieved document from the stream. */
        //     System.out.print(is.read());
        // } finally {
        // is.close();
        // }
    }
}
