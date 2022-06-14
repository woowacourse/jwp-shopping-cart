package woowacourse.auth.dto;

public class CustomerResponse {

    private Long id;
    private String email;
    private String name;
    private String phone;
    private String address;

    private CustomerResponse() {
    }

    public CustomerResponse(Long id, String email, String name, String phone, String address) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.address = address;
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

    public String getAddress() {
        return address;
    }
}
