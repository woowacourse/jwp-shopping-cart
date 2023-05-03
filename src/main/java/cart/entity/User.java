package cart.entity;

import cart.business.domain.ProductName;
import cart.business.domain.UserEmail;

public class User {

    private Integer id;
    private UserEmail email;
    private ProductName password;

    public User(UserEmail email) {
        this.email = email;
    }
}
