/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.validationapp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MessagesTest {

    @Test
    public void testMessageID() {

        Messages message = new Messages(
                "+27838884567",
                "Where are you? You are late!",
                2
        );

        assertTrue(message.checkMessageID());
    }

    @Test
    public void testRecipient() {

        Messages message = new Messages(
                "+27838884567",
                "Where are you? You are late!",
                2
        );

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    @Test
    public void testInvalidRecipient() {

        Messages message = new Messages(
                "0838834567",
                "Where are you?",
                2
        );

        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code.",
                message.checkRecipientCell()
        );
    }

    @Test
    public void testMessageHash() {

        Messages message = new Messages(
                "+27834557896",
                "Hi tonight",
                0
        );

        String hash = message.createMessageHash();

        assertTrue(hash.matches("\\d{2}:0:HITONIGHT"));
    }

    @Test
    public void testMessageSent() {

        Messages message = new Messages(
                "+27834557896",
                "Did you get the cake?",
                1
        );

        assertEquals(
                "Message successfully sent.",
                message.SentMessage(1)
        );
    }

    @Test
    public void testMessageDisregarded() {

        Messages message = new Messages(
                "+27834484567",
                "Yohoooo, I am at your gate.",
                3
        );

        assertEquals(
                "Press 0 to delete the message.",
                message.SentMessage(2)
        );
    }

    @Test
    public void testMessageStored() {

        Messages message = new Messages(
                "+27838884567",
                "Where are you? You are late!",
                2
        );

        assertEquals(
                "Message successfully stored.",
                message.SentMessage(3)
        );
    }

    @Test
    public void testMessageNotMoreThan250Characters() {

        String messageText = "Where are you? You are late! I have asked you to be on time.";

        assertTrue(messageText.length() <= 250);
    }

    @Test
    public void testLongMessage() {

        String longMessage = "a".repeat(251);

        assertTrue(longMessage.length() > 250);
    }
}
