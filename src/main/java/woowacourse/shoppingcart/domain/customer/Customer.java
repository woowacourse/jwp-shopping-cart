package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.support.Encryptor;

public class Customer {

    private static final long DEFAULT_ID = 0L;

    private Long id;
    private UserName name;
    private Password password;

    public Customer(Long id, String name, String password) {
        this.id = id;
        this.name = new UserName(name);
        this.password = Password.toPassword(password);
    }

    public Customer(String name, String password, Encryptor encryptor) {
        this.id = DEFAULT_ID;
        this.name = new UserName(name);
        this.password = Password.toPasswordWithEncrypt(password, encryptor);
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

    public void update(String name, String password, Encryptor encryptor) {
        this.name = new UserName(name);
        this.password = Password.toPasswordWithEncrypt(password, encryptor);
    }
}
