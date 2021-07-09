package zoom.chat;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Authorization {

    private static HttpURLConnection connection;
    private static String accessToken = "";
    private static String refreshToken = "";

    public static void authorize() throws Exception {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        refreshToken = getRefreshToken();
        try {
            URL url = new URL("https://zoom.us/oauth/token?grant_type=refresh_token&refresh_token=" + refreshToken);
            connection = (HttpURLConnection) url.openConnection();

            //Request setup
            connection.setRequestProperty("Authorization","Basic aDJ1ZEJ5b1BUZDJEal9kV0F4dFpYZzp5VWdOVnM1RXlRbFdYQ3Y3UUlTaU9ucVYzVUZ0MmZPNQ==");
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            while((line=reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }
        accessToken = JSON_Parser.parseInner (responseContent.toString(), "access_token");
        writeRefreshToken(JSON_Parser.parseInner (responseContent.toString(), "refresh_token"));
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static String getRefreshToken() {
        try {
            File refreshTokenFile = new File("Refresh Token.txt");
            Scanner myReader = new Scanner(refreshTokenFile);
            refreshToken = myReader.nextLine();
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading refresh token from file.");
            e.printStackTrace();
        }
        return refreshToken;
    }

    public static void writeRefreshToken(String refreshToken) {
        try {
            FileWriter myWriter = new FileWriter("Refresh Token.txt");
            myWriter.write(refreshToken);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing refresh token to file.");
            e.printStackTrace();
        }
    }
}
