package com.Hbence.appointmentManagementAPI.service.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Pattern;

public class ValidatorCollection {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public static boolean emailChecker(String email) {
        if (email == null || email.length() > 100) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean passwordChecker(String password) {
        if (password.length() < 8 || password.length() > 16) {
            return false;
        }

        String specialCharacters = "\"!@#$%^&*()-_=+[]{};:,.?/\"";
        String numbersText = "1234567890";
        boolean specialChecker = false;
        boolean upperCaseChecker = false;
        boolean lowerCaseChecker = false;
        boolean initChecker = false;

        for (int i = 0; i < password.trim().length(); i++) {
            String selectedChar = String.valueOf(password.charAt(i));

            if (numbersText.contains(selectedChar)) {
                initChecker = true;
            } else if (specialCharacters.contains(selectedChar)) {
                specialChecker = true;
            } else if (selectedChar.equals(selectedChar.toUpperCase())) {
                upperCaseChecker = true;
            } else if (selectedChar.equals(selectedChar.toLowerCase())) {
                lowerCaseChecker = true;
            }
        }

        return specialChecker && upperCaseChecker && lowerCaseChecker && initChecker;
    }

    public static String generateVerificationCode() {
        String code = "";
        ArrayList<String> characters = new ArrayList<String>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));

        for (int i = 97; i <= 122; i++) {
            characters.add(String.valueOf((char) i));
        }

        while (code.length() != 10) {
            Random random = new Random();
            code += characters.get(random.nextInt(characters.size()));
        }

        return code;
    }

    public static Boolean phoneValidator(String phone){
        return true;
    }
}
