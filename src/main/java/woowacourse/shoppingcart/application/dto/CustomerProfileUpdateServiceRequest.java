package woowacourse.shoppingcart.application.dto;

public class CustomerProfileUpdateServiceRequest {

    private final Long id;
    private final String name;

    public CustomerProfileUpdateServiceRequest(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
