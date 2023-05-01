package cart.dao.entity;

public class UserEntity {

    private final Long id;
    private final String email;
    private final String password;

    public UserEntity(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public UserEntity(final String email, final String password) {
        this(null, email, password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
