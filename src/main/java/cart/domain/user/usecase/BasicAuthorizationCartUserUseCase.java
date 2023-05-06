package cart.domain.user.usecase;

public interface BasicAuthorizationCartUserUseCase {
    void verifyCartUser(final String cartUserEmail, final String password);
}
