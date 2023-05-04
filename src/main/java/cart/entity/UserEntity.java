package cart.entity;

public class UserEntity {
    private final int id;
    private final String email;

    private final String password;

    public UserEntity(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
