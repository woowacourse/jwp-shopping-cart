package cart.entiy.user;

public class UserEntity {

    private final UserEntityId id;
    private final String email;
    private final String password;

    public UserEntity(final UserEntityId id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public UserEntityId getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
