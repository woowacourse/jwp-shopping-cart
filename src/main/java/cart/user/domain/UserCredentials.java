package cart.user.domain;

public final class UserCredentials {
    
    private final Email email;
    private final Password password;
    
    public UserCredentials(final Email email, final Password password) {
        this.email = email;
        this.password = password;
    }
    
    public Email getEmail() {
        return this.email;
    }
    
    public Password getPassword() {
        return this.password;
    }
}
