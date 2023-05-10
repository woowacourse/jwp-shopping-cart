package cart.service.dto;

import cart.dao.entity.CustomerEntity;

public class CustomerInfoDto {

    private final long id;
    private final String email;
    private final String password;

    public CustomerInfoDto(final long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static CustomerInfoDto fromEntity(final CustomerEntity customerEntity) {
        return new CustomerInfoDto(customerEntity.getId(), customerEntity.getEmail(), customerEntity.getPassword());
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
