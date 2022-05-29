package woowacourse.shoppingcart.domain.customer;

public class Customer {
    private final UserName userName;
    private final Password password;
    private final NickName nickName;
    private final Age age;

    public Customer(UserName userName, Password password, NickName nickName, Age age) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.age = age;
    }

    public static Customer of(String userName, String password, String nickName, int age) {
        return new Customer(
                new UserName(userName),
                new Password(password),
                new NickName(nickName),
                new Age(age)
        );
    }

    public String getUserName() {
        return userName.getUserName();
    }

    public String getPassword() {
        return password.getPassword();
    }

    public String getNickName() {
        return nickName.getNickName();
    }

    public int getAge() {
        return age.getAge();
    }
}
