package cart.entity.member;

public enum Role {

    ADMIN("admin"),
    USER("user");

    private final String role;

    Role(final String role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }

}
