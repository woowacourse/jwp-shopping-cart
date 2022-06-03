package woowacourse.shoppingcart.dto;

public class CheckDuplicationResponse {

    private Boolean duplicated;

    public CheckDuplicationResponse() {
    }

    public CheckDuplicationResponse(final boolean duplicated) {
        this.duplicated = duplicated;
    }

    public Boolean getDuplicated() {
        return duplicated;
    }
}
