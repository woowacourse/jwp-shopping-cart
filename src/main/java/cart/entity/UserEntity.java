package cart.entity;

public final class UserEntity {

    private final long id;
    private final String email;
    private final String password;

    public UserEntity(long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
