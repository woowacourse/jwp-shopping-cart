package cart.member.domain;

import lombok.Getter;

@Getter
public class Member {

    private final Long id;
    private final Email email;
    private final Password password;
    private final PhoneNumber phoneNumber;

    public Member(Long id, String email, String password, String phoneNumber) {
        this.id = id;
        this.email = new Email(email);
        this.password = new Password(password);
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }
}
