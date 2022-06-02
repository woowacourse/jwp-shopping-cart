package woowacourse.auth.dto.response;

public class EmailUniqueCheckResponse {

    private boolean unique;

    public EmailUniqueCheckResponse() {
    }

    public EmailUniqueCheckResponse(boolean unique) {
        this.unique = unique;
    }

    public boolean isUnique() {
        return unique;
    }
}
