package zoom.chat;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class JSON_Parser {

    public static String parseInner(String json, String type) throws Exception {
        // parsing file "JSONExample.json"
        String innerList = "";
        Object obj = new JSONParser().parse(json);

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        // get email
        innerList = (String) jo.get(type);
        return innerList;
    }
    public static ArrayList<String> parseOuter(String json, String type1, String type2, String email, LocalDate date) throws Exception {
        // check if access token still good
        if (json == "401.Contacts") {
            json = Contacts.getContacts();
        }
        if (json == "401.Messages") {
            json = Messages.getMessages(email, date);
        }
        // parsing file "JSONExample.json"
        ArrayList<String> outerList = new ArrayList<String>();
        Object obj = new JSONParser().parse(json);

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        // get contacts array
        JSONArray jsonArray = (JSONArray) jo.get(type1);

        // get json object contact
        Iterator<JSONObject> iterator = jsonArray.iterator();
        while(iterator.hasNext()) {
            outerList.add(parseInner(iterator.next().toString(), type2));
        }
        return outerList;
    }

}
