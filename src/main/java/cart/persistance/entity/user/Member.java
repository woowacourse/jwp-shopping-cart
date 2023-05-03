package cart.persistance.entity.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public final class Member {

    @NotNull
    @Positive
    private final long id;

    @Email
    @NotBlank
    private final String email;

    @NotBlank
    private final String password;

    public Member(final long id, final String email, final String password) {
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

    public Long getId() {
        return id;
    }
}
