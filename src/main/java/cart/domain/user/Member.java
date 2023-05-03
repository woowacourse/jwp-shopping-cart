package cart.domain.user;

public final class Member {

    private final long id;
    private final String email;
    private final String password;

    public Member(final long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }
}
