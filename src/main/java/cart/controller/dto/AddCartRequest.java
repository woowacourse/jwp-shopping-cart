package cart.controller.dto;

public class AddCartRequest {

    private Long id;

    public AddCartRequest(final Long id) {
        this.id = id;
    }

    public AddCartRequest() {
    }

    public Long getId() {
        return id;
    }

}
