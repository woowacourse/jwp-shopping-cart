package woowacourse.shoppingcart.entity;

import woowacourse.shoppingcart.domain.Age;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Nickname;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.domain.Username;

public class CustomerEntity {

    private final Long id;
    private final String username;
    private final String password;
    private final String nickname;
    private final int age;

    public CustomerEntity(Long id, String username, String password, String nickname, int age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }

    public Customer toDomain() {
        return new Customer(new Username(username), new Password(password), new Nickname(nickname), new Age(age));
    }
}
