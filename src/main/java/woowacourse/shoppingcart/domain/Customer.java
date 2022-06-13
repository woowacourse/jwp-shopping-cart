package woowacourse.shoppingcart.domain;

public class Customer {
    private final Long id;
    private final Email email;
    private final String name;
    private final Password password;

    public Customer(Long id, Email email, String name, Password password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public Customer(Email email, String name, Password password) {
        this(null, email, name, password);
    }

    public boolean isCorrectPassword(String inputPassword) {
        return this.password.isMatches(inputPassword);
    }

    public Customer changeName(String name) {
        return new Customer(this.id, this.email, name, this.password);
    }

    public Customer changePassword(Password password) {
        return new Customer(this.id, this.email, this.name, password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password.getPassword();
    }
}
