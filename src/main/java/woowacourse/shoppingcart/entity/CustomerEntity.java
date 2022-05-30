package woowacourse.shoppingcart.entity;

import java.util.Objects;

public class CustomerEntity {

    private final Long id;
    private final String username;
    private final String password;
    private final String nickname;
    private final int age ;

    public CustomerEntity(Long id,
                          String username,
                          String password,
                          String nickname,
                          int age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
    }

    public CustomerEntity(String username, String password, String nickname, int age) {
        this(null, username, password, nickname, age);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerEntity that = (CustomerEntity) o;
        return age == that.age
                && Objects.equals(id, that.id)
                && Objects.equals(username, that.username)
                && Objects.equals(password, that.password)
                && Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, nickname, age);
    }
}
