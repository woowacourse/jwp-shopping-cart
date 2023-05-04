package cart.util;


public interface AuthorizationExtractor<T> {

    T extractHeader(String request);
}
