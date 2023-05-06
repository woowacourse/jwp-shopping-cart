package cart.domain.user;

import java.util.Optional;

public class User {

    private final Long id;
    private final String email;
    private final String password;
    private final UserPrivacy userPrivacy;

    private User(Long id, String email, String password, UserPrivacy userPrivacy) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userPrivacy = userPrivacy;
    }

    public static User create(Long id, String email, String password, String name, String phoneNumber) {
        return new User(id, email, password, new UserPrivacy(name, phoneNumber));
    }

    public static User createToSave(String email, String password, String name, String phoneNumber) {
        return new User(null, email, password, new UserPrivacy(name, phoneNumber));
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public Optional<String> getName() {
        return this.userPrivacy.getName();
    }

    public Optional<String> getPhoneNumber() {
        return this.userPrivacy.getPhoneNumber();
    }
}
