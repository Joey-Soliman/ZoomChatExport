package zoom.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

public class Messages {
    private static HttpURLConnection connection;

    public static String getMessages(String email, LocalDate date) throws Exception {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        String authToken = "eyJhbGciOiJIUzUxMiIsInYiOiIyLjAiLCJraWQiOiJmNjRiYmE3ZC1iNjNmLTRmNGEtYjZlOC1mNmI4MDc4YWQ2MWQifQ.eyJ2ZXIiOjcsImF1aWQiOiIxNmRmODc4OWE5NTY5NTg3MDJmMmQxNTZmNTJhZTdkNyIsImNvZGUiOiJWOGpDeGZkM0xIX0k2NjdmVFlwU3hPcEIzOUtpczRtZmciLCJpc3MiOiJ6bTpjaWQ6aDJ1ZEJ5b1BUZDJEal9kV0F4dFpYZyIsImdubyI6MCwidHlwZSI6MCwidGlkIjowLCJhdWQiOiJodHRwczovL29hdXRoLnpvb20udXMiLCJ1aWQiOiJJNjY3ZlRZcFN4T3BCMzlLaXM0bWZnIiwibmJmIjoxNjI1MTgxMjc4LCJleHAiOjE2MjUxODQ4NzgsImlhdCI6MTYyNTE4MTI3OCwiYWlkIjoiMXNIRm9kTnZTcXl4aW9XSDlKOTROZyIsImp0aSI6IjU2Y2M5MTE5LTgyMDgtNDQxZi1iMjI5LTliM2EwYzUwMmY2ZiJ9.11wSt_zLUyZjwk7J96fxXe5T1tpt-VFOf4vvYW4Xk9yFWbXXdFNPeacXrxqAF9Xgpm7Yt2x22FTQKiymUJFBSQ";
        try {
            URL url = new URL("https://api.zoom.us/v2/chat/users/me/messages?to_contact=" + email + "&date=" + date + "&page_size=10");
            connection = (HttpURLConnection) url.openConnection();

            //Request setup
            connection.setRequestProperty("Authorization","Bearer " + authToken);
            connection.setRequestMethod("GET");
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
        return responseContent.toString();
    }
}
