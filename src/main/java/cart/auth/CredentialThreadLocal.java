package cart.auth;

import org.springframework.stereotype.Component;

@Component
public class CredentialThreadLocal {

    final ThreadLocal<Credential> credentialThreadLocal = new ThreadLocal<>();

    public void set(final Credential credential) {
        credentialThreadLocal.set(credential);
    }

    public Credential get() {
        return credentialThreadLocal.get();
    }

    public void clear() {
        credentialThreadLocal.remove();
    }
}
