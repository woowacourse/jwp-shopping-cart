package woowacourse.shoppingcart.domain;

public class Customer {

    private Long id;
    private String name;
    private String password;

    public Customer() {
    }

    public Customer(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Customer(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void update(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
