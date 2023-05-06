package cart.dto.cart;

import cart.domain.member.Email;

import java.util.Objects;

public class UserDto {

    private final Long id;
    private final Email email;

    public UserDto(Long id, String email) {
        this.id = id;
        this.email = new Email(email);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getEmail();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(email, userDto.email);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", email=" + email +
                '}';
    }
}
