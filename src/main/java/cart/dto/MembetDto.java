package cart.dto;

import cart.domain.member.Member;

public class MembetDto {

    private final String email;
    private final String password;

    public MembetDto(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static MembetDto from(final Member member) {
        return new MembetDto(
                member.getEmail().email(),
                member.getPassword().password()
        );
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
