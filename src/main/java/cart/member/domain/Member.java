package cart.member.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Member {

    private final Long id;
    private final Email email;
    private final Password password;
    private final PhoneNumber phoneNumber;

    public Member(String email, String password, String phoneNumber) {
        this(null, email, password, phoneNumber);
    }

    public Member(Long id, String email, String password, String phoneNumber) {
        this.id = id;
        this.email = new Email(email);
        this.password = new Password(password);
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getAddress();
    }

    public String getPassword() {
        return password.getPassword();
    }

    public String getPhoneNumber() {
        return phoneNumber.getNumber();
    }
}
