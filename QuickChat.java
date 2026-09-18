/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.validationapp;

/**
 *
 * @author Student
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class QuickChat {

    static Scanner scanner = new Scanner(System.in);

    // Arrays required by Part 3
    static Messages[] sentMessages;
    static Messages[] disregardedMessages;
    static Messages[] storedMessages;

    static String[] messageHashes;
    static String[] messageIDs;

    // Counters
    static int sentCount = 0;
    static int disregardedCount = 0;
    static int storedCount = 0;

    public static void main(String[] args) {

        // ==========================================
        // PART 1 - REGISTRATION
        // ==========================================

        System.out.println("=================================");
        System.out.println("       QUICKCHAT REGISTRATION");
        System.out.println("=================================");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter cell phone number: ");
        String phoneNumber = scanner.nextLine();

        UserValidation validation =
                new UserValidation(
                        username,
                        password,
                        phoneNumber
                );

        System.out.println();

        System.out.println(validation.getUserNameMessage());

        System.out.println(validation.getPasswordMessage());

        System.out.println(validation.getCellPhoneMessage());

        // Check registration
        if (!validation.checkUserName()
                || !validation.checkPassword()
                || !validation.checkCellPhoneNumber()) {

            System.out.println();
            System.out.println("Registration failed.");
            System.out.println(
                    "Please restart the application and enter valid details."
            );

            return;
        }

        System.out.println();
        System.out.println("Registration successful!");

        // ==========================================
        // PART 1 - LOGIN
        // ==========================================

        System.out.println();
        System.out.println("=================================");
        System.out.println("             LOGIN");
        System.out.println("=================================");

        System.out.print("Enter username: ");
        String loginUsername = scanner.nextLine();

        System.out.print("Enter password: ");
        String loginPassword = scanner.nextLine();

        boolean loggedIn =
                loginUsername.equals(username)
                && loginPassword.equals(password);

        if (!loggedIn) {

            System.out.println("Login failed.");
            System.out.println(
                    "Username or password is incorrect."
            );

            return;
        }

        System.out.println("Login successful!");

        // ==========================================
        // PART 2
        // ==========================================

        System.out.println();
        System.out.println("Welcome to QuickChat.");

        System.out.print(
                "How many messages would you like to enter? "
        );

        int numberOfMessages = scanner.nextInt();
        scanner.nextLine();

        // Create arrays based on user's number
        sentMessages = new Messages[numberOfMessages];

        disregardedMessages = new Messages[numberOfMessages];

        storedMessages = new Messages[numberOfMessages];

        messageHashes = new String[numberOfMessages];

        messageIDs = new String[numberOfMessages];

        int messagesEntered = 0;

        // ==========================================
        // MESSAGE ENTRY
        // ==========================================

        while (messagesEntered < numberOfMessages) {

            System.out.println();
            System.out.println("Message "
                    + (messagesEntered + 1)
                    + " of "
                    + numberOfMessages);

            System.out.print("Enter recipient: ");
            String recipient = scanner.nextLine();

            System.out.print("Enter message: ");
            String messageText = scanner.nextLine();

            // Check message length
            if (messageText.length() > 250) {

                System.out.println(
                        "Please enter a message of less than 250 characters."
                );

                continue;
            }

            if (messageText.isEmpty()) {

                System.out.println(
                        "Message cannot be empty."
                );

                continue;
            }

            // Create message
            Messages newMessage =
                    new Messages(
                            recipient,
                            messageText,
                            messagesEntered + 1
                    );

            // Validate recipient
            String recipientResult =
                    newMessage.checkRecipientCell();

            System.out.println(recipientResult);

            if (!recipientResult.contains("successfully")) {

                System.out.println(
                        "Message was not added."
                );

                continue;
            }

            System.out.println();
            System.out.println(
                    "Message ID: "
                    + newMessage.getMessageID()
            );

            System.out.println(
                    "Message Hash: "
                    + newMessage.getMessageHash()
            );

            System.out.println();
            System.out.println("Choose what to do:");
            System.out.println("1. Send Message");
            System.out.println("2. Disregard Message");
            System.out.println("3. Store Message");

            System.out.print("Enter option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    sentMessages[sentCount] =
                            newMessage;
                    messageHashes[messagesEntered] =
                            newMessage.getMessageHash();
                    messageIDs[messagesEntered] =
                            newMessage.getMessageID();
                    sentCount++;
                    System.out.println(
                            newMessage.SentMessage(1)
                    );
                }
                case 2 -> {
                    disregardedMessages[disregardedCount] =
                            newMessage;
                    disregardedCount++;
                    System.out.println(
                            newMessage.SentMessage(2)
                    );
                }
                case 3 -> {
                    storedMessages[storedCount] =
                            newMessage;
                    messageHashes[messagesEntered] =
                            newMessage.getMessageHash();
                    messageIDs[messagesEntered] =
                            newMessage.getMessageID();
                    storedCount++;
                    System.out.println(
                            newMessage.SentMessage(3)
                    );  saveStoredMessagesToJSON();
                }
                default -> {
                    System.out.println(
                            "Invalid option."
                    );
                    
                    continue;
                }
            }

            messagesEntered++;
        }

        // Load stored messages from JSON
        loadStoredMessagesFromJSON();

        // ==========================================
        // PART 2 MAIN MENU
        // ==========================================

        mainMenu();
    }

    // ==========================================
    // MAIN MENU
    // ==========================================

    public static void mainMenu() {

        int option;

        do {

            System.out.println();
            System.out.println("=================================");
            System.out.println("          QUICKCHAT MENU");
            System.out.println("=================================");

            System.out.println("1. Send Messages");

            System.out.println(
                    "2. Show recently sent messages"
            );

            System.out.println("3. Quit");

            System.out.println("4. Stored Messages");

            System.out.print("Choose an option: ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {

                case 1 -> System.out.println(
                            "The selected number of messages "
                            + "has already been entered."
                    );

                case 2 -> System.out.println(
                            "Coming Soon."
                    );

                case 3 -> System.out.println(
                            "Thank you for using QuickChat."
                    );

                case 4 -> storedMessagesMenu();

                default -> System.out.println(
                            "Invalid option. Please choose 1, 2, 3 or 4."
                    );
            }

        } while (option != 3);
    }

    // ==========================================
    // STORED MESSAGES MENU
    // ==========================================

    public static void storedMessagesMenu() {

        int option;

        do {

            System.out.println();
            System.out.println("=================================");
            System.out.println("       STORED MESSAGES MENU");
            System.out.println("=================================");

            System.out.println("1. Display sender and recipient");

            System.out.println(
                    "2. Display longest stored message"
            );

            System.out.println(
                    "3. Search for Message ID"
            );

            System.out.println(
                    "4. Search for messages by recipient"
            );

            System.out.println(
                    "5. Delete message using Message Hash"
            );

            System.out.println(
                    "6. Display full report"
            );

            System.out.println(
                    "7. Back to main menu"
            );

            System.out.print("Choose an option: ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {

                case 1 -> displaySenderRecipient();

                case 2 -> displayLongestMessage();

                case 3 -> searchMessageID();

                case 4 -> searchRecipient();

                case 5 -> deleteMessage();

                case 6 -> displayReport();

                case 7 -> System.out.println(
                            "Returning to main menu..."
                    );

                default -> System.out.println(
                            "Invalid option."
                    );
            }

        } while (option != 7);
    }

    // ==========================================
    // DISPLAY SENDER AND RECIPIENT
    // ==========================================

    public static void displaySenderRecipient() {

        if (storedCount == 0) {

            System.out.println(
                    "No stored messages."
            );

            return;
        }

        System.out.println();
        System.out.println("Stored Messages:");

        for (int i = 0; i < storedCount; i++) {

            if (storedMessages[i] != null) {

                System.out.println(
                        "Sender: "
                        + storedMessages[i].getSender()
                );

                System.out.println(
                        "Recipient: "
                        + storedMessages[i].getRecipient()
                );

                System.out.println(
                        "--------------------------"
                );
            }
        }
    }

    // ==========================================
    // DISPLAY LONGEST MESSAGE
    // ==========================================

    public static void displayLongestMessage() {

        if (storedCount == 0) {

            System.out.println(
                    "No stored messages."
            );

            return;
        }

        Messages longest =
                storedMessages[0];

        for (int i = 1; i < storedCount; i++) {

            if (storedMessages[i] != null
                    && storedMessages[i]
                            .getMessage()
                            .length()
                    > longest
                            .getMessage()
                            .length()) {

                longest = storedMessages[i];
            }
        }

        System.out.println();
        System.out.println(
                "Longest stored message:"
        );

        System.out.println(
                longest.getMessage()
        );
    }

    // ==========================================
    // SEARCH BY MESSAGE ID
    // ==========================================

    public static void searchMessageID() {

        System.out.print(
                "Enter Message ID: "
        );

        String searchID =
                scanner.nextLine();

        boolean found = false;

        for (int i = 0; i < storedCount; i++) {

            if (storedMessages[i] != null
                    && storedMessages[i]
                            .getMessageID()
                            .equals(searchID)) {

                System.out.println();

                System.out.println(
                        "Recipient: "
                        + storedMessages[i]
                                .getRecipient()
                );

                System.out.println(
                        "Message: "
                        + storedMessages[i]
                                .getMessage()
                );

                found = true;

                break;
            }
        }

        if (!found) {

            System.out.println(
                    "Message ID not found."
            );
        }
    }

    // ==========================================
    // SEARCH BY RECIPIENT
    // ==========================================

    public static void searchRecipient() {

        System.out.print(
                "Enter recipient: "
        );

        String searchRecipient =
                scanner.nextLine();

        boolean found = false;

        for (int i = 0; i < storedCount; i++) {

            if (storedMessages[i] != null
                    && storedMessages[i]
                            .getRecipient()
                            .equals(searchRecipient)) {

                System.out.println(
                        storedMessages[i]
                                .getMessage()
                );

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No messages found for this recipient."
            );
        }
    }

    // ==========================================
    // DELETE BY MESSAGE HASH
    // ==========================================

    public static void deleteMessage() {

        System.out.print(
                "Enter Message Hash: "
        );

        String searchHash =
                scanner.nextLine();

        boolean found = false;

        for (int i = 0; i < storedCount; i++) {

            if (storedMessages[i] != null
                    && storedMessages[i]
                            .getMessageHash()
                            .equals(searchHash)) {

                System.out.println(
                        "Message: \""
                        + storedMessages[i]
                                .getMessage()
                        + "\" successfully deleted."
                );

                // Shift messages to the left
                for (int j = i; j < storedCount - 1; j++) {

                    storedMessages[j] =
                            storedMessages[j + 1];
                }

                storedMessages[storedCount - 1] =
                        null;

                storedCount--;

                saveStoredMessagesToJSON();

                found = true;

                break;
            }
        }

        if (!found) {

            System.out.println(
                    "Message Hash not found."
            );
        }
    }

    // ==========================================
    // DISPLAY REPORT
    // ==========================================

    public static void displayReport() {

        System.out.println();
        System.out.println(
                "================================="
        );

        System.out.println(
                "          MESSAGE REPORT"
        );

        System.out.println(
                "================================="
        );

        if (storedCount == 0) {

            System.out.println(
                    "No stored messages."
            );

            return;
        }

        for (int i = 0; i < storedCount; i++) {

            if (storedMessages[i] != null) {

                System.out.println(
                        "Message ID: "
                        + storedMessages[i]
                                .getMessageID()
                );

                System.out.println(
                        "Message Hash: "
                        + storedMessages[i]
                                .getMessageHash()
                );

                System.out.println(
                        "Sender: "
                        + storedMessages[i]
                                .getSender()
                );

                System.out.println(
                        "Recipient: "
                        + storedMessages[i]
                                .getRecipient()
                );

                System.out.println(
                        "Message: "
                        + storedMessages[i]
                                .getMessage()
                );

                System.out.println(
                        "---------------------------------"
                );
            }
        }

        System.out.println(
                "Total stored messages: "
                + storedCount
        );
    }

    // ==========================================
    // SAVE STORED MESSAGES TO JSON
    // ==========================================

    public static void saveStoredMessagesToJSON() {

        try {

            FileWriter writer =
                    new FileWriter("messages.json");

            writer.write("[\n");

            for (int i = 0; i < storedCount; i++) {

                if (storedMessages[i] != null) {

                    writer.write("  {\n");

                    writer.write(
                            "    \"messageID\": \""
                            + escapeJSON(
                                    storedMessages[i]
                                            .getMessageID()
                            )
                            + "\",\n"
                    );

                    writer.write(
                            "    \"messageHash\": \""
                            + escapeJSON(
                                    storedMessages[i]
                                            .getMessageHash()
                            )
                            + "\",\n"
                    );

                    writer.write(
                            "    \"sender\": \""
                            + escapeJSON(
                                    storedMessages[i]
                                            .getSender()
                            )
                            + "\",\n"
                    );

                    writer.write(
                            "    \"recipient\": \""
                            + escapeJSON(
                                    storedMessages[i]
                                            .getRecipient()
                            )
                            + "\",\n"
                    );

                    writer.write(
                            "    \"message\": \""
                            + escapeJSON(
                                    storedMessages[i]
                                            .getMessage()
                            )
                            + "\"\n"
                    );

                    writer.write("  }");

                    if (i < storedCount - 1) {
                        writer.write(",");
                    }

                    writer.write("\n");
                }
            }

            writer.write("]\n");

            writer.close();

            System.out.println(
                    "Stored messages saved to messages.json."
            );

        } catch (IOException e) {

            System.out.println(
                    "Error saving messages: "
                    + e.getMessage()
            );
        }
    }

    // ==========================================
    // LOAD STORED MESSAGES FROM JSON
    // ==========================================

    public static void loadStoredMessagesFromJSON() {

        File file =
                new File("messages.json");

        if (!file.exists()) {
            return;
        }

        try {

            String json =
                    new String(
                            Files.readAllBytes(
                                    Paths.get("messages.json")
                            )
                    );

            if (json.trim().equals("[]")) {
                return;
            }

            String[] objects =
                    json.split("\\},\\s*\\{");

            int loadedCount = 0;

            for (String object : objects) {

                object = object
                        .replace("[", "")
                        .replace("]", "")
                        .replace("{", "")
                        .replace("}", "")
                        .trim();

                String messageID =
                        getJSONValue(
                                object,
                                "messageID"
                        );

                String messageHash =
                        getJSONValue(
                                object,
                                "messageHash"
                        );

                String recipient =
                        getJSONValue(
                                object,
                                "recipient"
                        );

                String messageText =
                        getJSONValue(
                                object,
                                "message"
                        );

                if (messageID != null
                        && recipient != null
                        && messageText != null) {

                    if (loadedCount >= storedMessages.length) {
                        break;
                    }

                    Messages loadedMessage =
                            new Messages(
                                    recipient,
                                    messageText,
                                    loadedCount + 1
                            );

                    loadedMessage.setMessageID(
                            messageID
                    );

                    storedMessages[loadedCount] =
                            loadedMessage;

                    messageHashes[loadedCount] =
                            loadedMessage.getMessageHash();

                    messageIDs[loadedCount] =
                            loadedMessage.getMessageID();

                    loadedCount++;
                }
            }

            storedCount = loadedCount;

        } catch (IOException e) {

            System.out.println(
                    "Error reading messages.json: "
                    + e.getMessage()
            );
        }
    }

    // ==========================================
    // GET VALUE FROM JSON
    // ==========================================

    public static String getJSONValue(
            String object,
            String key) {

        String search =
                "\"" + key + "\": \"";

        int start =
                object.indexOf(search);

        if (start == -1) {
            return null;
        }

        start += search.length();

        int end =
                object.indexOf("\"", start);

        if (end == -1) {
            return null;
        }

        return object.substring(
                start,
                end
        );
    }

    // ==========================================
    // ESCAPE JSON CHARACTERS
    // ==========================================

    public static String escapeJSON(String text) {

        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}