package cart.user.domain;

public class Password {
    
    public static final String PASSWORD_NOT_HAVING_SPECIAL_CHARACTER_ERROR = "비밀번호는 특수문자를 포함해야 합니다.";
    public static final String PASSWORD_NOT_HAVING_ALPHABET_ERROR = "비밀번호는 영문자를 포함해야 합니다.";
    public static final String PASSWORD_NOT_HAVING_NUMBER_ERROR = "비밀번호는 숫자를 포함해야 합니다.";
    public static final String PASSWORD_OVER_MAX_LENGTH_ERROR = "비밀번호는 20자 이하여야 합니다.";
    public static final String PASSWORD_UNDER_MIN_LENGTH_ERROR = "비밀번호는 8자 이상이어야 합니다.";
    private final String value;
    
    public Password(final String value) {
        this.validate(value);
        this.value = value;
    }
    
    private void validate(final String password) {
        this.validateMinLength(password);
        this.validateMaxLength(password);
        this.validateHavingNumber(password);
        this.validateHavingAlphabet(password);
        this.validateHavingSpecialLetters(password);
    }
    
    private void validateHavingSpecialLetters(final String password) {
        if (!password.matches(".*[~!@#$%^&*()].*")) {
            throw new IllegalArgumentException(PASSWORD_NOT_HAVING_SPECIAL_CHARACTER_ERROR);
        }
    }
    
    private void validateHavingAlphabet(final String password) {
        if (!password.matches(".*[a-zA-Z].*")) {
            throw new IllegalArgumentException(PASSWORD_NOT_HAVING_ALPHABET_ERROR);
        }
    }
    
    private void validateHavingNumber(final String password) {
        if (!password.matches(".*[0-9].*")) {
            throw new IllegalArgumentException(PASSWORD_NOT_HAVING_NUMBER_ERROR);
        }
    }
    
    private void validateMaxLength(final String password) {
        if (password.length() > 20) {
            throw new IllegalArgumentException(PASSWORD_OVER_MAX_LENGTH_ERROR);
        }
    }
    
    private void validateMinLength(final String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException(PASSWORD_UNDER_MIN_LENGTH_ERROR);
        }
    }
    
    public String getValue() {
        return this.value;
    }
}
