package cart.domain.user.service.dto;

import cart.domain.user.CartUser;

public class CartUserDto {
    private final String email;
    private final String password;

    private CartUserDto(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static CartUserDto from(final CartUser cartUser) {
        return new CartUserDto(
                cartUser.getUserEmail(),
                cartUser.getPassword()
        );
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
