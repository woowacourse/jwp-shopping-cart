package cart.dto.application;

import cart.dto.request.MemberRequest;
import java.util.Objects;

public class MemberDto {
    private final String username;
    private final String password;

    public MemberDto(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public MemberDto(final MemberRequest request) {
        this.username = request.getUsername();
        this.password = request.getPassword();
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
        final MemberDto memberDto = (MemberDto) o;
        return Objects.equals(username, memberDto.username) && Objects.equals(password,
                memberDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
