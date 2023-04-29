package cart.controller.dto;

public class SignInResponse {

    private final String basic;

    public SignInResponse(final String basic) {
        this.basic = basic;
    }

    public String getBasic() {
        return basic;
    }
}
