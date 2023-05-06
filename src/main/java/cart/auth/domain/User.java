package cart.auth.domain;

public final class User {
    
    private final long id;
    private final Email email;
    private final Password password;
    
    public User(final long id, final Email email, final Password password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
    
    public static User of(final long id, final String email, final String password) {
        return new User(id, new Email(email), new Password(password));
    }
    
    public long getId() {
        return this.id;
    }
    
    public Email getEmail() {
        return this.email;
    }
    
    public Password getPassword() {
        return this.password;
    }
}
