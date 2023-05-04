package cart.web.config.auth;

public interface AuthCredentialDecoder<T> {

    T decodeCredential(String credential);
}
