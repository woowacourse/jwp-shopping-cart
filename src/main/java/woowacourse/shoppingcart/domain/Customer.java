package woowacourse.shoppingcart.domain;

public class Customer {

    private Long id;
    private String email;
    private String username;
    private String password;

    public Customer(String email, String username, String password) {
        this(null, email, username, password);
    }

    public Customer(Long id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
