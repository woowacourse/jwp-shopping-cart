package cart.service.dto.member;

public class MemberSearchResponse {

    private Long id;
    private String email;
    private String password;

    private MemberSearchResponse() {
    }

    public MemberSearchResponse(final Long id, final String email, final String password) {
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
