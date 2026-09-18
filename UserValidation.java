/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.validationapp;

/**
 *
 * @author Student
 */
public class UserValidation {

    private final String userName;
    private final String password;
    private final String cellPhoneNumber;

    public UserValidation(String userName, String password, String cellPhoneNumber) {
        this.userName = userName;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public boolean checkUserName() {
        return userName.length() <= 5 && userName.contains("_");
    }

    public boolean checkPassword() {
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;

        if (password.length() < 8) {
            return false;
        }

        for (int i = 0; i < password.length(); i++) {
            char currentCharacter = password.charAt(i);

            if (Character.isUpperCase(currentCharacter)) {
                hasCapital = true;
            }

            if (Character.isDigit(currentCharacter)) {
                hasNumber = true;
            }

            if (!Character.isLetterOrDigit(currentCharacter)) {
                hasSpecialCharacter = true;
            }
        }

        return hasCapital && hasNumber && hasSpecialCharacter;
    }

    public boolean checkCellPhoneNumber() {
        String cellPhoneRegex = "^\\+27\\d{9}$";
        return cellPhoneNumber.matches(cellPhoneRegex);
    }

    public String getUserNameMessage() {
        if (checkUserName()) {
            return "Username is successfully captured";
        }

        return "Username is not correctly formatted, please ensure that your username contains an underscore and is not more than 5 characters.";
    }

    public String getPasswordMessage() {
        if (checkPassword()) {
            return "Password is successfully captured";
        }

        return "Password is not correctly formatted, please ensure that password contains at least 8 characters, a capital letter, a number and a special character.";
    }

    public String getCellPhoneMessage() {
        if (checkCellPhoneNumber()) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }
}
