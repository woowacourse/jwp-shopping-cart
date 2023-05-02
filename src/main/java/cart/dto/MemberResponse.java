package cart.dto;

public class MemberResponse {

    private final Long id;
    private final String email;
    private final String password;

    private MemberResponse(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static MemberResponse of(final Long id, final String email, final String password) {
        return new MemberResponse(id, email, password);
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
