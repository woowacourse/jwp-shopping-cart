package cart.domain.user;

import java.util.Optional;

public class UserPrivacy {

    private final String name;
    private final String phoneNumber;

    public UserPrivacy(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getPhoneNumber() {
        return Optional.ofNullable(phoneNumber);
    }
}
