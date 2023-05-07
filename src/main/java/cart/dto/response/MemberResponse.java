package cart.dto.response;

import cart.dto.application.MemberDto;
import java.util.Objects;

public class MemberResponse {

    private final String username;
    private final String password;

    public MemberResponse(final MemberDto member) {
        this.username = member.getUsername();
        this.password = member.getPassword();
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
        final MemberResponse that = (MemberResponse) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
