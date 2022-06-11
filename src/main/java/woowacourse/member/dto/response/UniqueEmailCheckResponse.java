package woowacourse.member.dto.response;

public class UniqueEmailCheckResponse {

    private boolean unique;

    public UniqueEmailCheckResponse() {
    }

    public UniqueEmailCheckResponse(boolean unique) {
        this.unique = unique;
    }

    public boolean isUnique() {
        return unique;
    }
}
