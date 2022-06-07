package woowacourse.member.domain;

import woowacourse.member.infrastructure.PasswordEncoder;

public class Member {

    private Long id;
    private Email email;
    private Password password;
    private Name name;

    public Member(Long id, String email, String password, String name) {
        this.id = id;
        this.email = new Email(email);
        this.password = Password.fromEncoded(password);
        this.name = new Name(name);
    }

    public Member(String email, String password, String name, PasswordEncoder passwordEncoder) {
        this.email = new Email(email);
        this.password = Password.fromNotEncoded(password, passwordEncoder);
        this.name = new Name(name);
    }

    public boolean authenticate(final String password) {
        return this.password.authenticate(password);
    }

    public void updateName(final String name) {
        this.name.updateName(name);
    }

    public void updatePassword(final String oldPassword,
                               final String newPassword,
                               final PasswordEncoder passwordEncoder) {
        this.password.updatePassword(oldPassword, newPassword, passwordEncoder);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getPassword() {
        return password.getPassword();
    }

    public String getName() {
        return name.getName();
    }
}
