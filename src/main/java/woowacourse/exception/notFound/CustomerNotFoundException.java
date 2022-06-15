package woowacourse.exception.notFound;

public class CustomerNotFoundException extends NotFoundException {
    private static final String DEFAULT_MESSAGE = "요청하신 회원을 찾을 수 없습니다";

    public CustomerNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
