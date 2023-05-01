package cart.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Member {

    private final String email;
    private final String password;
    private final String name;
    private final String address;
    private final int age;
}
