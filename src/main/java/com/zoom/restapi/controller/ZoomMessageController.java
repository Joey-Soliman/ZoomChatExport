package com.zoom.restapi.controller;

import com.zoom.restapi.chat.Authorization;
import com.zoom.restapi.chat.Contacts;
import com.zoom.restapi.chat.JSON_Parser;
import com.zoom.restapi.chat.Messages;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

@RestController
public class ZoomMessageController {
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public void defaultEndpoint() {
        return;
    }


    @RequestMapping(path = "/messages", method = RequestMethod.GET)
    public String getMessages(@RequestParam(name = "days") String length) throws Exception {
        System.out.println("Received messages endpoint. Days = " + length);
        int days = Integer.parseInt(length);
        String recap = "";
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
            recap += "Chats from: " + email + System.lineSeparator();
            for (int i = days - 1; i > -1; i--) {
                // Clears messages arraylist when moving on to next date
                messages.clear();
                LocalDate date = today.minusDays(i);
                recap += "Date: " + date + System.lineSeparator();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                // Get sender: message from messages
                ArrayList<String> messageList = JSON_Parser.parseOuter(Messages.getMessages(email, date), "messages", "message", "sender", email, date);
                Iterator messageIterator = messageList.iterator();
                while (messageIterator.hasNext()) {
                    message = (String) messageIterator.next();
                    messages.add(message);
                    System.out.println(message);
                }
                Collections.reverse(messages);
                Iterator messageIterator2 = messages.iterator();
                while (messageIterator2.hasNext()) {
                    message = (String) messageIterator2.next();
                    recap += message + System.lineSeparator();
                }
            }
        }
        return recap;
    }
}
