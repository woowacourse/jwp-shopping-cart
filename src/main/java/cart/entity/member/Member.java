package cart.entity.member;

public class Member {

    private final Long id;
    private final Email email;
    private final Password password;
    private final Role role;

    public Member(final Long id, final Email email, final Password password, final Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
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

    public Role getRole() {
        return role;
    }
}
