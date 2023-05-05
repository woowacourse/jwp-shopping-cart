package cart.domain.entity;

public class Member {

    private final Long id;
    private final String email;
    private final String password;

    private Member(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static Member of(final Long id, final String email, final String password) {
        return new Member(id, email, password);
    }

    public static Member of(final String email, final String password) {
        return new Member(null, email, password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
