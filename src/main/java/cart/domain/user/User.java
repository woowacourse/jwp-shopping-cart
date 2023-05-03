package cart.domain.user;

public class User {
    private final Email email;
    private final Password password;
    private final Long id;

    public User(Email email, Password password, Long id) {
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public User(Email email, Password password) {
        this(email, password, null);
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getPassword() {
        return password.getPassword();
    }

    public Long getId() {
        return id;
    }
}
