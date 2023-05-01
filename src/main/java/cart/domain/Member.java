package cart.domain;

//TODO: PASSWORD 타입 변경(DB 포함)
public class Member {

    private Long id;
    private final String email;
    private final String password;

    public Member(String email, String password) {
        this(null, email, password);
    }

    public Member(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
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
