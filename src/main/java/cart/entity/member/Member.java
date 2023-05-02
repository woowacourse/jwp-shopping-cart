package cart.entity.member;

public class Member {
    private final long id;
    private final String email;
    private final String password;

    public Member(final long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
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
