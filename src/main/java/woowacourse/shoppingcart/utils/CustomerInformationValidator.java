package woowacourse.shoppingcart.utils;

public class CustomerInformationValidator {


    private static final String EMAIL_FORMAT = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$";
    private static final String PASSWORD_FORMAT = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";
    private static final String PHONE_FORMAT = "^010-[0-9]{4}-[0-9]{4}$";
    private static final int MAXIMUM_NAME_LENGTH = 30;

    public static void validateEmail(String email) {
        if (email.isBlank() || !email.matches(EMAIL_FORMAT)) {
            throw new IllegalArgumentException("올바르지 않은 이메일 형식입니다.");
        }
    }

    public static void validatePassword(String password) {
        if (password.isBlank() || !password.matches(PASSWORD_FORMAT)) {
            throw new IllegalArgumentException("올바르지 않은 비밀번호 형식입니다.");
        }
    }

    public static void validateName(String name) {
        if (name.isBlank() || name.length() > MAXIMUM_NAME_LENGTH) {
            throw new IllegalArgumentException("올바르지 않은 이름 형식입니다.");
        }
    }

    public static void validatePhoneNumber(String phone) {
        if (phone.isBlank() || !phone.matches(PHONE_FORMAT)) {
            throw new IllegalArgumentException("올바르지 않은 전화번호 형식입니다.");
        }
    }

    public static void validateAddress(String address) {
        if (address.isBlank()) {
            throw new IllegalArgumentException("올바르지 않은 주소 형식입니다.");
        }
    }
}
