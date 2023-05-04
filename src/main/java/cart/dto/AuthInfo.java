package cart.dto;

public class AuthInfo {
    private final String email;
    private final String paasword;

    public AuthInfo(String email, String paasword) {
        this.email = email;
        this.paasword = paasword;
    }

    public String getEmail() {
        return email;
    }

    public String getPaasword() {
        return paasword;
    }
}
