package cart.utils;

public class CaesarCipher {

    private static final int KEY = 3;
    private static final int LENGTH = 26;

    public static String encrypt(final String password) {
        final char[] letters = password.toCharArray();
        for (int position = 0; position < letters.length; position++) {
            final char letter = letters[position];
            if (letter >= 'a' && letter <= 'z') {
                letters[position] = (char) ((letter - 'a' + KEY) % LENGTH + 'a');
            }
            if (letter >= 'A' && letter <= 'Z') {
                letters[position] = (char) ((letter - 'A' + KEY) % LENGTH + 'A');
            }
        }
        return String.valueOf(letters);
    }

    public static String decrypt(final String encryptedPassword) {
        final char[] letters = encryptedPassword.toCharArray();
        for (int position = 0; position < letters.length; position++) {
            final char letter = letters[position];
            if (letter >= 'a' && letter <= 'z') {
                letters[position] = (char) ((letter - 'a' - KEY + LENGTH) % LENGTH + 'a');
            }
            if (letter >= 'A' && letter <= 'Z') {
                letters[position] = (char) ((letter - 'A' - KEY + LENGTH) % LENGTH + 'A');
            }
        }
        return String.valueOf(letters);
    }
}
