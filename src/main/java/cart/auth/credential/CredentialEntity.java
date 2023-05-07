package cart.auth.credential;

public class CredentialEntity {

    private Long memberId;
    private String email;
    private String password;

    public CredentialEntity(Long memberId, String email, String password) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
    }

    public Long getMemberId() {
        return memberId;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
