package cart.entity.member;

public class Member {
    private final Long id;
    private final String email;
    private final String password;

    public Member(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Member(final String email, final String password) {
        this(null, email, password);
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
