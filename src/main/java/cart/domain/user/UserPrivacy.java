package cart.domain.user;

import static java.lang.System.lineSeparator;

public class UserPrivacy {

    private static final String DEFAULT_VALUE = "저장되지 않음.";

    private final String name;
    private final String phoneNumber;

    private UserPrivacy(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static UserPrivacy create(String name, String phoneNumber) {
        validateName(name);
        return new UserPrivacy(name, phoneNumber);
    }

    private static void validateName(String name) {
        if (name.equals(DEFAULT_VALUE)) {
            throw new IllegalArgumentException("저장하지 않은 경우의 이름과 동일합니다. 다른 이름을 입력해주세요." + lineSeparator() +
                    "입력된 이름: " + DEFAULT_VALUE);
        }
    }


    public static UserPrivacy createWithoutName(String phoneNumber) {
        return new UserPrivacy(DEFAULT_VALUE, phoneNumber);
    }

    public static UserPrivacy createWithoutPhoneNumber(String name) {
        return new UserPrivacy(name, DEFAULT_VALUE);
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
