package cart.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberEntity {

    private final String email;
    private final String name;
    private final String phoneNumber;
    private final String password;

    public boolean isSamePassword(String password) {
        return this.password.equals(password);
    }
}
