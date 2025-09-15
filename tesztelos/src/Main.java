//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static boolean passwordChecker(String password) {
        if (password.length() < 8 || password.length() > 16) {
            return false;
        }

        String specialCharacters = "\"!@#$%^&*()-_=+[]{};:,.?/\"";
        String numbersText = "1234567890";
        Boolean specialChecker = false;
        Boolean upperCaseChecker = false;
        Boolean lowerCaseChecker = false;
        Boolean initChecker = false;

        for (int i = 0; i < password.length(); i++) {
            String selectedChar = String.valueOf(password.charAt(i));

            if (numbersText.indexOf(selectedChar) >= 0) {
                initChecker = true;
            } else if (specialCharacters.indexOf(selectedChar) >= 0) {
                specialChecker = true;
            } else if (selectedChar.equals(selectedChar.toUpperCase())) {
                upperCaseChecker = true;
            } else if (selectedChar.equals(selectedChar.toLowerCase())) {
                lowerCaseChecker = true;
            }
        }

        return specialChecker && upperCaseChecker && lowerCaseChecker && initChecker;
    }

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println(passwordChecker("Jelszo12."));
    }
}