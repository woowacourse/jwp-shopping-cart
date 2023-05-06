package cart.controller.dto;

import cart.service.dto.CustomerInfoDto;

public class CustomerResponse {

    private final long id;
    private final String email;
    private final String password;

    public CustomerResponse(final long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static CustomerResponse fromDto(final CustomerInfoDto customerInfoDto) {
        return new CustomerResponse(customerInfoDto.getId(), customerInfoDto.getEmail(), customerInfoDto.getPassword());
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
