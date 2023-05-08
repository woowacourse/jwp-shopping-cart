package cart.member.dto;

import cart.member.domain.Member;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class MemberResponse {
    private final Long id;
    private final String email;
    private final String password;
    
    public MemberResponse(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
    
    public static MemberResponse from(final Member member) {
        return new MemberResponse(member.getId(), member.getEmail().getEmail(), member.getPassword().getPassword());
    }
}
