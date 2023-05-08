package cart.entity;

import cart.entity.vo.Email;
import cart.entity.vo.Id;
import cart.entity.vo.Password;
import cart.exception.NoAuthorizationUserException;

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

    public boolean authorization(final String email, final String password) {
        if (!this.email.equals(email)) {
            throw new NoAuthorizationUserException("이메일이 틀렸습니다");
        }
        if (!this.password.equals(password)) {
            throw new NoAuthorizationUserException("패스워드가 틀렸습니다");
        }
        return true;
    }
}
