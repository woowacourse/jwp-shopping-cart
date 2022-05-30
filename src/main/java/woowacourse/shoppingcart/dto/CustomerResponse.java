package woowacourse.shoppingcart.dto;

public class CustomerResponse {
    private Long id;
    private String userName;
    private String password;
    private String nickName;
    private int age;

    private CustomerResponse() {
    }

    public CustomerResponse(Long id, String userName, String password, String nickName, int age) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.age = age;
    }

    public CustomerResponse(String userName, String nickName, int age) {
        this(null, userName, null, nickName, age);
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public int getAge() {
        return age;
    }
}
