package cart.dto.response;

public class MemberResponse {

    private Long id;
    private String email;
    private String password;

    public MemberResponse() {
    }

    public MemberResponse(final Long id, final String email, final String password) {
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

    public String getPassword() {
        return password;
    }
}
