package cart.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberResponse {

    private final Long id;
    private final String email;
    private final String password;

    public static MemberResponse of(final Long id, final String email, final String password) {
        return new MemberResponse(id, email, password);
    }
}
