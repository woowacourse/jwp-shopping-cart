package woowacourse.shoppingcart.application.dto.request;

public class CustomerIdentificationRequest {

    private Long id;

    private CustomerIdentificationRequest() {
    }

    public CustomerIdentificationRequest(final String id) {
        this.id = Long.valueOf(id);
    }

    public Long getId() {
        return id;
    }
}
