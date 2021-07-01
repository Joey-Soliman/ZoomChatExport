package zoom.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.time.LocalDate;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many days back would you like?");
        Integer days = scanner.nextInt();
        String email;
        LocalDate today = LocalDate.now(ZoneId.of ("America/Los_Angeles"));
        ArrayList<String> emailList= JSON_Parser.parseOuter(Contacts.getContacts(), "contacts", "email");
        Iterator emailIter = emailList.iterator();
        while (emailIter.hasNext()) {
            email = (String) emailIter.next();
            System.out.println(email);
            for (int i = days-1; i > -1; i-- ) {
                LocalDate date = today.minusDays(i);
                System.out.println("Date: " + date);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                ArrayList<String> messageList= JSON_Parser.parseOuter(Messages.getMessages(email, date), "messages", "message");
                Iterator messageIter = messageList.iterator();
                while (messageIter.hasNext()) {
                    System.out.println(messageIter.next());
                }
            }
        }
    }
}
