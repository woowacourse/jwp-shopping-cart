package cart.dto.request;

//TODO 패키지 고민
public class Credential {

    private final Long memberId;
    private final String email;
    private final String password;

    public Credential(Long memberId, String email, String password) {
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
