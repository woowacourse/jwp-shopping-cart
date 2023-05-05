package cart.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Member {

    private final Long id;
    private final String email;
    private final String password;
}
