package cart.domain.user;

public interface CartUserRepository {
    CartUser findByEmail(String email);

    Long save(CartUser cartUser);
}
