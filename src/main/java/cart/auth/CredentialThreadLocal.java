package cart.auth;

import org.springframework.stereotype.Component;

@Component
public class CredentialThreadLocal {

    private ThreadLocal<Credential> local = new ThreadLocal<>();

    public void set(Credential credential) {
        local.set(credential);
    }

    public Credential get() {
        return local.get();
    }

    public void clear() {
        local.remove();
    }
}
