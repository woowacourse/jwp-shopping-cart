package cart.user.service.dto;

import cart.user.domain.CartUser;

public class CartUserResponseDto {
    private final String email;
    private final String password;

    private CartUserResponseDto(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static CartUserResponseDto from(final CartUser cartUser) {
        return new CartUserResponseDto(
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
