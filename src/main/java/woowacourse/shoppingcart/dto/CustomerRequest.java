package woowacourse.shoppingcart.dto;

public class CustomerRequest {

    private String name;
    private String password;

    private CustomerRequest(){
    }

    public CustomerRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
