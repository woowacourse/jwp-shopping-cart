package cart.auth;

public interface AuthorizationParser<T> {

    String AUTHORIZATION = "Authorization";

    T parse(String requestHeader);
}
