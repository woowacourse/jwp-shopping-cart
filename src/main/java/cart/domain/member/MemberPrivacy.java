package cart.domain.member;

import java.util.Optional;

public class MemberPrivacy {

    private final String name;
    private final String phoneNumber;

    public MemberPrivacy(String name, String phoneNumber) {
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
