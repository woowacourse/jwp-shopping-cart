package woowacourse.shoppingcart.domain;

public class Customer {

    private static final long DEFAULT_ID = 0L;
    private Long id;
    private UserName name;
    private Password password;

    public Customer() {
    }

    public Customer(Long id, String name, String password) {
        this.id = id;
        this.name = new UserName(name);
        this.password = new Password(password);
    }

    public Customer(String name, String password) {
        this(DEFAULT_ID, name, password);
    }

    public Long getId() {
        return id;
    }

    public UserName getName() {
        return name;
    }

    public Password getPassword() {
        return password;
    }

    public void update(String name, String password) {
        this.name = new UserName(name);
        this.password = new Password(password);
    }
}
