package zoom.chat;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.time.LocalDate;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws Exception {
        // Clear file
        Export.clearFile();
        // Ask user how many days of messages they want
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many days back would you like?");
        Integer days = scanner.nextInt();
        String email;
        String message;
        ArrayList<String> messages = new ArrayList<>();
        // Set date to UTC time
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        // Get emails in contacts list
        ArrayList<String> emailList= JSON_Parser.parseOuter(Contacts.getContacts(), "contacts", "email", "", "", today);
        Iterator emailIterator = emailList.iterator();
        while (emailIterator.hasNext()) {
            email = (String) emailIterator.next();
            Export.exportString("Chats from: " + email);
            for (int i = days-1; i > -1; i-- ) {
                // Clears messages arraylist when moving on to next date
                messages.clear();
                LocalDate date = today.minusDays(i);
                Export.exportString("Date: " + date);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                // Get sender: message from messages
                ArrayList<String> messageList= JSON_Parser.parseOuter(Messages.getMessages(email, date), "messages", "message", "sender", email, date);
                Iterator messageIterator = messageList.iterator();
                while (messageIterator.hasNext()) {
                    message = (String) messageIterator.next();
                    messages.add(message);
                    //System.out.println(message);
                }
                Collections.reverse(messages);
                Export.exportArrayList(messages);
            }
            // Adds newline after final message to/from a user
            Export.exportString("");
        }
    }
}
