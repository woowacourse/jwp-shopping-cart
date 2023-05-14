package cart.auth;

public interface AuthorizationParser<T> {

    T parse(String requestHeader);
}
