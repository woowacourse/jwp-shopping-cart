package cart.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {
    private final String email;
    private final String password;
}
