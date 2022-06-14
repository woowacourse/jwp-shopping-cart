package woowacourse.auth.specification;

import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.badrequest.DuplicateEmailException;
import woowacourse.shoppingcart.exception.badrequest.DuplicateUsernameException;
import woowacourse.shoppingcart.exception.badrequest.InvalidFieldException;
import woowacourse.shoppingcart.exception.notfound.InvalidCustomerException;

@Component
@RequiredArgsConstructor
public class CustomerSpecification {

    private final CustomerDao customerDao;

    private static final Pattern EMPTY_PATTERN = Pattern.compile("\\s");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("\\w+@\\w+.\\w+");

    public void validateForSave(Customer customer) {
        validatePassword(customer.getPassword());
        validateEmail(customer.getEmail());
        validateUsername(customer.getUsername());

        validateEmailDuplicate(customer.getEmail());
        validateUsernameDuplicate(customer.getUsername());
    }

    public void validateForUpdate(Customer customer) {
        validateUsernameDuplicate(customer.getUsername());
        validateUsername(customer.getUsername());
    }

    public void validateUsernameDuplicate(String username) {
        boolean existCustomerBySameUsername = customerDao.findByUsername(username).isPresent();


        if (existCustomerBySameUsername) {
            throw new DuplicateUsernameException();
        }
    }

    public void validateEmailDuplicate(String email) {
        boolean existCustomerBySameEmail = customerDao.findByEmail(email).isPresent();
        if (existCustomerBySameEmail) {
            throw new DuplicateEmailException();
        }
    }

    public void validateCustomerExists(long id) {
        customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
    }

    private void validateUsername(String username) {
        if (username == null) {
            throw new InvalidFieldException("username", "닉네임 1자 이상 10자 이하여야합니다.");
        }
        if (EMPTY_PATTERN.matcher(username).find()) {
            throw new InvalidFieldException("username", "닉네임에는 공백이 들어가면 안됩니다.");
        }
        int length = username.length();
        if (length < 1 || length > 10) {
            throw new InvalidFieldException("username", "닉네임 1자 이상 10자 이하여야합니다.");
        }
    }

    private void validateEmail(String email) {
        if (email == null) {
            throw new InvalidFieldException("email", "이메일은 8자 이상 50자 이하여야합니다.");
        }
        if (EMPTY_PATTERN.matcher(email).find()) {
            throw new InvalidFieldException("email", "이메일에는 공백이 들어가면 안됩니다.");
        }
        int length = email.length();
        if (length < 8 || length > 50) {
            throw new InvalidFieldException("email", "이메일은 8자 이상 50자 이하여야합니다.");
        }
        if (!EMAIL_PATTERN.matcher(email).find()) {
            throw new InvalidFieldException("email", "이메일 형식을 지켜야합니다.");
        }
    }

    public void validatePassword(String password) {
        if (password == null) {
            throw new InvalidFieldException("password", "비밀번호는 8자 이상 20자 이하여야합니다.");
        }
        if (EMPTY_PATTERN.matcher(password).find()) {
            throw new InvalidFieldException("password", "비밀번호에는 공백이 들어가면 안됩니다.");
        }
        int length = password.length();
        if (length < 8 || length > 20) {
            throw new InvalidFieldException("password", "비밀번호는 8자 이상 20자 이하여야합니다.");
        }
    }
}
