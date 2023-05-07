package cart.dto.request;

import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MemberRequest {

    @NotBlank
    @Email
    private final String username;
    @NotBlank
    private final String password;

    public MemberRequest(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MemberRequest that = (MemberRequest) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
