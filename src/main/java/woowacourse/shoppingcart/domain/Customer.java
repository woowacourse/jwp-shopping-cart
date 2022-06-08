package woowacourse.shoppingcart.domain;

public class Customer {
    private Long id;
    private UserName userName;
    private Password password;

    public Customer() {
    }

    private Customer(Long id, UserName userName, Password password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public static Customer of(Long id, String userName, String password) {
        return new Customer(id, new UserName(userName), new Password(password));
    }

    public static Customer of(String name, String password){
        UserName userName = new UserName(name);
        userName.validateFormat();

        Password userPassword = new Password(password);
        userPassword.validateFormat();

        return new Customer(null, userName, userPassword);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return userName.getName();
    }

    public String getEncryptedPassword() {
        return password.encryptPassword();
    }
}
