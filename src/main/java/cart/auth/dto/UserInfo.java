package cart.auth.dto;

import cart.auth.domain.Email;
import cart.auth.domain.Password;

public class UserInfo {
    
    private final Email email;
    private final Password password;
    
    public UserInfo(final Email email, final Password password) {
        this.email = email;
        this.password = password;
    }
    
    public static UserInfo of(final String email, final String password) {
        return new UserInfo(new Email(email), new Password(password));
    }
    
    public Email getEmail() {
        return this.email;
    }
    
    public Password getPassword() {
        return this.password;
    }
}
