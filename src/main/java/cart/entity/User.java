package cart.entity;

import cart.entity.vo.Email;
import cart.entity.vo.Id;
import cart.entity.vo.Password;

import java.util.Objects;

public class User {
    private final Email email;
    private final Password password;

    public User(String email, String password) {
        this.email = new Email(email);
        this.password = new Password(password);
    }

    public String getEmail() {
        return email.value();
    }

    public String getPassword() {
        return password.value();
    }

    public boolean authorization(final String email, final String password){
        return this.email.equals(email) & this.password.equals(password);
    }
}
