package cart.entity;

public class MemberEntity {
    private final Long id;
    private final String email;
    private final String password;

    public MemberEntity(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return id + email + password;
    }

    public String getPassword() {
        return password;
    }
}
