package cart.dto;

import cart.dao.entity.Users;

public class AuthUser {

    private final Long id;
    private final String email;
    private final String password;

    public AuthUser(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public AuthUser(final Users user) {
        this(user.getId(), user.getEmail(), user.getPassword());
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
