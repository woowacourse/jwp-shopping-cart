package cart.product.dto;

import cart.product.domain.Member;

public class ResponseMemberDto {

    private final String email;
    private final String password;

    private ResponseMemberDto(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static ResponseMemberDto create(final Member member) {
        return new ResponseMemberDto(member.getEmail().getValue(), member.getPassword().getValue());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
