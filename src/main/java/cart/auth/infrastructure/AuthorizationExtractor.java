package cart.auth.infrastructure;

public interface AuthorizationExtractor<T> {
    T extract(final String header);
}
