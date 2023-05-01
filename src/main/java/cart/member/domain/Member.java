package cart.member.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Member {
    private final Long id;
    private final Email email;
    private final Password password;
    
    public Member(final String email, final String password) {
        this(null, email, password);
    }
    
    public Member(final Long id, final String email, final String password) {
        this(id, new Email(email), new Password(password));
    }
    
    private Member(final Long id, final Email email, final Password password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
