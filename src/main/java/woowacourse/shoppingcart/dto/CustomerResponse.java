package woowacourse.shoppingcart.dto;

public class CustomerResponse {
    private Long id;
    private String userName;
    private String nickName;
    private String password;
    private int age;

    private CustomerResponse() {
    }

    public CustomerResponse(Long id, String userName, String nickName, String password, int age) {
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
        this.password = password;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }
}
