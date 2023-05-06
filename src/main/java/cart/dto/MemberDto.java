package cart.dto;

import cart.entity.MemberEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDto {
    private final long id;
    private final String email;
    private final String password;

    public static MemberDto fromEntity(MemberEntity entity) {
        return new MemberDto(entity.getId(), entity.getEmail(), entity.getPassword());
    }
}
