package cart.domain.member;

import cart.domain.member.validator.EmailValidator;
import java.util.Objects;
import java.util.Optional;

public class Member {

    private final Long id;
    private final String email;
    private final String password;
    private final MemberPrivacy memberPrivacy;

    private Member(Long id, String email, String password, MemberPrivacy memberPrivacy) {
        EmailValidator emailValidator = new EmailValidator();
        emailValidator.validate(email);
        this.id = id;
        this.email = email;
        this.password = password;
        this.memberPrivacy = memberPrivacy;
    }

    public static Member create(Long id, String email, String password, String name, String phoneNumber) {
        return new Member(id, email, password, new MemberPrivacy(name, phoneNumber));
    }

    public static Member createToSave(String email, String password, String name, String phoneNumber) {
        return new Member(null, email, password, new MemberPrivacy(name, phoneNumber));
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public Optional<String> getName() {
        return this.memberPrivacy.getName();
    }

    public Optional<String> getPhoneNumber() {
        return this.memberPrivacy.getPhoneNumber();
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
