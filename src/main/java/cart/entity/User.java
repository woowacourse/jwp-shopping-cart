package cart.entity;

import cart.entity.vo.Email;
import cart.entity.vo.Id;
import cart.entity.vo.Password;

public class User {
    private final Id id;
    private final Email email;
    private final Password password;

    public User(Long id, String email, String password) {
        this.id = new Id(id);
        this.email = new Email(email);
        this.password = new Password(password);
    }

    public Long getId() {
        return id.value();
    }

    public String getEmail() {
        return email.value();
    }

    public String getPassword() {
        return password.value();
    }
}
