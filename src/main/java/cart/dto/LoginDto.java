package cart.dto;

import cart.domain.Member;

public class LoginDto {

    private final Long memberId;
    private final String email;
    private final String password;

    public LoginDto(Long memberId, String email, String password) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
    }

    public LoginDto(String email, String password) {
        this(null, email, password);
    }

    public LoginDto(Member member) {
        this(member.getMemberId(), member.getEmail(), member.getPassword());
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

    public Member toEntity() {
        return new Member(email, password);
    }
}
