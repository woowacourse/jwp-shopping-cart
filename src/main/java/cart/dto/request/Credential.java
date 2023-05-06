package cart.dto.request;

//TODO 패키지 고민
public class Credential {

    private final Long id;
    private final String email;
    private final String password;

    public Credential(Long id, String email, String password) {
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
