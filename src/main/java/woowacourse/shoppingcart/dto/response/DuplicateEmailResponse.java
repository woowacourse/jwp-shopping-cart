package woowacourse.shoppingcart.dto.response;

public class DuplicateEmailResponse {

    private boolean isDuplicated;

    private DuplicateEmailResponse() {}

    public DuplicateEmailResponse(final boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
    }

    public boolean isDuplicated() {
        return isDuplicated;
    }
}
