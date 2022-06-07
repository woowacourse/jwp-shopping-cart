package woowacourse.shoppingcart.domain.customer;

public class Customer {
    private final Username userName;
    private final Password password;
    private final NickName nickName;
    private final Age age;

    public Customer(Username userName, Password password, NickName nickName, Age age) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.age = age;
    }

    public static Customer of(String userName, String password, String nickName, int age) {
        return new Customer(
                new Username(userName),
                new Password(password),
                new NickName(nickName),
                new Age(age)
        );
    }

    public void validatePassword(String password) {
        if (!this.password.hasSamePassword(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public Customer updatePassword(String newPassword) {
        return new Customer(userName, new Password(newPassword), nickName, age);
    }

    public Customer updateInfo(String nickName, int age) {
        return new Customer(userName, password, new NickName(nickName), new Age(age));
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
