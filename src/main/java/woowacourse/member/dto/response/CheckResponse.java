package woowacourse.member.dto.response;

public class CheckResponse {

    private boolean unique;

    public CheckResponse() {
    }

    public CheckResponse(boolean unique) {
        this.unique = unique;
    }

    public boolean isUnique() {
        return unique;
    }
}
