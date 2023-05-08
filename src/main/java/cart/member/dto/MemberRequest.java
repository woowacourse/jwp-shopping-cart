package cart.member.dto;

import lombok.*;

@Getter
@ToString
@RequiredArgsConstructor
public class MemberRequest {
    private final Long id;
    private final String email;
    private final String password;
}
