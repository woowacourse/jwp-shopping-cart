package cart.entity;

import javax.validation.constraints.Email;

public final class UserEntity {
    private final Long id;
    @Email
    private final String email;
    private final String password;

    public UserEntity(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
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
