package cart.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberEntity {
    private final Long id;
    private final String email;
    private final String password;
}
