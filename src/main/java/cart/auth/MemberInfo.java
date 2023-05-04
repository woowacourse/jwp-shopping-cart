package cart.auth;

public class MemberInfo {
    private final Integer id;
    private final String email;

    public MemberInfo(final Integer id, final String email) {
        this.id = id;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
