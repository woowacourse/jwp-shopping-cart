package cart.member.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Password {
    private final String password;
    
    public Password(final String password) {
        this.password = password;
    }
}
