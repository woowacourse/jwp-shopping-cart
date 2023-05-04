package cart.domain.user;

public interface UserRepository {
    CartUser findByEmail(String email);
}
