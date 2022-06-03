package woowacourse.shoppingcart.infra.dao.entity;

public class CustomerEntity {
    private final Long id;
    private final String email;
    private final String name;
    private final String password;

    public CustomerEntity(Long id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }
}
