package cart.auth;

public class CredentialThreadLocal {

    private static ThreadLocal<Credential> credentialHolder = new ThreadLocal<>();

    public static void set(Credential credential) {
        credentialHolder.set(credential);
    }

    public static Credential get() {
        return credentialHolder.get();
    }

    public static void clear() {
        credentialHolder.remove();
    }
}
