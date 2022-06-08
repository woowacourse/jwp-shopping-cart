package woowacourse.exception;

public class DuplicatedCustomerEmailException extends CustomException {

    public DuplicatedCustomerEmailException() {
        super(Error.DUPLICATED_CUSTOMER_EMAIL);
    }
}
