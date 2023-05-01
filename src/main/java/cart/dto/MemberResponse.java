package cart.dto;

public class MemberResponse {
    private final Long id;
    private final String email;
    private final String name;
    private final String phone;

    public MemberResponse(Long id, String email, String name, String phone) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
