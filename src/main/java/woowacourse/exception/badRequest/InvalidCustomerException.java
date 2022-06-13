package woowacourse.exception.badRequest;

public class InvalidCustomerException extends BadRequestException {
    public InvalidCustomerException() {
        this("존재하지 않는 유저입니다.");
    }

    public InvalidCustomerException(final String msg) {
        super(msg);
    }
}
