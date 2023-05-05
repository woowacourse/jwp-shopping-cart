package cart.entity;

public class Member {
    private final Long id;
    private final String email;
    private final String password;

    private Member(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static Member of(Long id, String email, String password) {
        return new Member(id, email, password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
