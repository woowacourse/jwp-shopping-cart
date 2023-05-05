package cart.config.infrastructure;

public interface AuthorizationExtractor<T> {
    String AUTHORIZATION = "Authorization";

    T extract(String request);
}
