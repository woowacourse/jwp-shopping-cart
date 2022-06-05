package woowacourse.auth.dto.response;

public class EmailUniqueCheckResponse {

    private boolean unique;

    private EmailUniqueCheckResponse() {
    }

    public EmailUniqueCheckResponse(boolean unique) {
        this.unique = unique;
    }

    public boolean isUnique() {
        return unique;
    }
}
