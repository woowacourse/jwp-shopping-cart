package woowacourse.shoppingcart.dto;

public class CustomerIdRequest {

    private Long id;

    private CustomerIdRequest() {
    }

    public CustomerIdRequest(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
