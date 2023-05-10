package cart.dto;

import cart.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponse {

    private final String email;
    private final String password;

    public static MemberResponse from(final MemberEntity memberEntity) {
        return new MemberResponse(memberEntity.getEmail(), memberEntity.getPassword());
    }
}
