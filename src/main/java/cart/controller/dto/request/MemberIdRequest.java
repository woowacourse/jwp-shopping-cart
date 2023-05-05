package cart.controller.dto.request;

public class MemberIdRequest {
    private Long id;

    public MemberIdRequest() {
    }

    public MemberIdRequest(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
