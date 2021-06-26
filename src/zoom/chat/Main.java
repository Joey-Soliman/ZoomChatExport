package zoom.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;


public class Main {
    //Method 1

    private static HttpURLConnection connection;

    public static void main(String [] args) throws Exception {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        String authToken = "eyJhbGciOiJIUzUxMiIsInYiOiIyLjAiLCJraWQiOiI1YThhNGE2Ni1hMzRkLTRhNjEtYWJiYy1mYjkzMTQyZTgxZjcifQ.eyJ2ZXIiOjcsImF1aWQiOiIxNmRmODc4OWE5NTY5NTg3MDJmMmQxNTZmNTJhZTdkNyIsImNvZGUiOiJTMVJYbjU0YUhwX0k2NjdmVFlwU3hPcEIzOUtpczRtZmciLCJpc3MiOiJ6bTpjaWQ6aDJ1ZEJ5b1BUZDJEal9kV0F4dFpYZyIsImdubyI6MCwidHlwZSI6MCwidGlkIjowLCJhdWQiOiJodHRwczovL29hdXRoLnpvb20udXMiLCJ1aWQiOiJJNjY3ZlRZcFN4T3BCMzlLaXM0bWZnIiwibmJmIjoxNjI0Njc3NjY3LCJleHAiOjE2MjQ2ODEyNjcsImlhdCI6MTYyNDY3NzY2NywiYWlkIjoiMXNIRm9kTnZTcXl4aW9XSDlKOTROZyIsImp0aSI6ImExZDVlOTFhLWVjMWEtNDAxOS04NDNmLWFhZWI1MTQ3NjFkOCJ9.iUUc3bGDJSg-dyWbWN-PwPCTYhjvJlZMOH_vDO1L7TWQ7VgNv3TBJDpLLab2XRsLSoEUHOw_aEDkCBnnfAE0sQ";
        try {
            URL url = new URL("https://api.zoom.us/v2/chat/users/me/contacts?type=external");
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
            //System.out.println(responseContent.toString());
            ArrayList<String> emailList= JSON_Parser.parseOuter(responseContent.toString());
            Iterator iterator = emailList.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }
    }
}
