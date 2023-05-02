package cart.entity;

public class MemberEntity {

    private final Long id;
    private final String email;
    private final String password;

    private MemberEntity(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static MemberEntity of(final Long id, final String email, final String password) {
        return new MemberEntity(id, email, password);
    }

    public static MemberEntity of(final String email, final String password) {
        return new MemberEntity(null, email, password);
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
