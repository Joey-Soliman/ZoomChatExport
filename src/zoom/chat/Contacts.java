package zoom.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class Contacts {
    private static HttpURLConnection connection;

    public static String getContacts() throws Exception {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        String accessToken = Authorization.getAccessToken();
        try {
            URL url = new URL("https://api.zoom.us/v2/chat/users/me/contacts?type=external");
            connection = (HttpURLConnection) url.openConnection();

            //Request setup
            connection.setRequestProperty("Authorization","Bearer " + accessToken);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            if (status == 401) {
                Authorization.authorize();
                System.out.println("401.Contacts");
                return "401.Contacts";
            }
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
        return responseContent.toString();
    }
}
