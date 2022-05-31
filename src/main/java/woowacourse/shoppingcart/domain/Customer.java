package woowacourse.shoppingcart.domain;

public class Customer {

    private Long id;
    private String loginId;
    private String username;
    private String password;

    public Customer(Long id, String loginId, String username, String password) {
        this.id = id;
        this.loginId = loginId;
        this.username = username;
        this.password = password;
    }

    public Customer(String loginId, String username, String password) {
        this.loginId = loginId;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getName() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
