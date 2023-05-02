package cart.domain;

import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class User {

    private final Long id;

    @Email
    @NonNull
    private final String email;

    @NotBlank
    private final String password;

    public User(@NonNull String email, String password) {
        this(null, email, password);
    }

    public User(Long id, @NonNull String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
