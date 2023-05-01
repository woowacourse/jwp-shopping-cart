package cart.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberEntity {

    private final int id;
    private final String email;
    private final String password;
    private final String name;
    private final String address;
    private final int age;
}
