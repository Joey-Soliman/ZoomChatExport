package zoom.chat;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class JSON_Parser {

    public static String parseInner(String json, String type) throws Exception {
        // parsing file "JSONExample.json"
        String email = "";
        Object obj = new JSONParser().parse(json);

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        // get email
        email = (String) jo.get(type);
        return email;
    }
    public static ArrayList<String> parseOuter(String json, String type1, String type2) throws Exception {
        // parsing file "JSONExample.json"
        ArrayList<String> emailList = new ArrayList<String>();
        Object obj = new JSONParser().parse(json);

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        // get contacts array
        JSONArray jsonArray = (JSONArray) jo.get(type1);

        // get json object contact
        Iterator<JSONObject> iterator = jsonArray.iterator();
        while(iterator.hasNext()) {
            emailList.add(parseInner(iterator.next().toString(), type2));
        }
        return emailList;
    }

}
