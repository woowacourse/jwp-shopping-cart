package cart.auth;

import cart.auth.excpetion.AuthorizeException;
import org.springframework.util.ObjectUtils;

public class AuthHeader {

    private final String header;

    public AuthHeader(final String header) {
        validateEmpty(header);
        this.header = header;
    }

    private void validateEmpty(final String header) {
        if (ObjectUtils.isEmpty(header)) {
            throw new AuthorizeException("인증 정보가 없습니다.");
        }
    }

    public String toLowerCase() {
        return header.toLowerCase();
    }

    public String getHeader() {
        return header;
    }
}
