package cart.controller.dto.response;

public class MemberResponse {

    private final int id;
    private final String email;
    private final String password;

    public MemberResponse(final int id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

}
