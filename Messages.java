/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.validationapp;

import java.util.Random;

/**
 *
 * @author Student
 */
public class Messages {

    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;
    private String sender;

    private int messageNumber;

    public Messages(String recipient, String message, int messageNumber) {

        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.message = message;
        this.sender = "Developer";
        this.messageNumber = messageNumber;

        this.messageHash = createMessageHash();
    }

    // ==========================================
    // PART 2
    // ==========================================

    // Generate a 10-digit Message ID
    private String generateMessageID() {

        Random random = new Random();

        StringBuilder id = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }

        return id.toString();
    }

    // Check Message ID
    public boolean checkMessageID() {

        return messageID.length() == 10;
    }

    // Check recipient cellphone
    public String checkRecipientCell() {

        String cellPhoneRegex = "^\\+27\\d{9}$";

        if (recipient.matches(cellPhoneRegex)) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted "
                + "or does not contain an international code.";
    }

    // Create Message Hash
    public String createMessageHash() {

        String[] words = message.trim().split("\\s+");

        String firstWord = words[0];

        String lastWord = words[words.length - 1];

        String firstTwoDigits = messageID.substring(0, 2);

        return (firstTwoDigits
                + ":"
                + messageNumber
                + ":"
                + firstWord
                + lastWord).toUpperCase();
    }

    // Send / Disregard / Store
    public String SentMessage(int option) {

        switch (option) {

            case 1:
                return "Message successfully sent.";

            case 2:
                return "Message disregarded.";

            case 3:
                return "Message successfully stored.";

            default:
                return "Invalid option.";
        }
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getSender() {
        return sender;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    // Used when reading messages from JSON
    public void setMessageID(String messageID) {

        this.messageID = messageID;

        this.messageHash = createMessageHash();
    }

    @Override
    public String toString() {

        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nSender: " + sender
                + "\nRecipient: " + recipient
                + "\nMessage: " + message;
    }
}