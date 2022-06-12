package woowacourse.common.dto;

import java.util.Objects;

public class RedirectErrorResponse {

    private String message;
    private final boolean redirect = true;

    public RedirectErrorResponse() {
    }

    public RedirectErrorResponse(String message) {
        this.message = message;
    }

    public RedirectErrorResponse(Exception e) {
        this.message = e.getMessage();
    }

    public String getMessage() {
        return message;
    }

    public boolean getRedirect() {
        return redirect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RedirectErrorResponse that = (RedirectErrorResponse) o;
        return redirect == that.redirect
                && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, redirect);
    }
}
