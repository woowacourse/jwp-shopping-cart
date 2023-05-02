package cart.controller.dto.response;

import cart.entity.MemberEntity;

public class MemberResponse {

    private final String email;
    private final String password;

    private MemberResponse(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static MemberResponse of(MemberEntity entity) {
        return new MemberResponse(entity.getEmail(), entity.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
