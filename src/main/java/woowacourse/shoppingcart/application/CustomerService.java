package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.domain.Password;
import woowacourse.auth.domain.User;
import woowacourse.common.exception.InvalidExceptionType;
import woowacourse.common.exception.InvalidRequestException;
import woowacourse.common.exception.NotFoundException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UpdateMeRequest;
import woowacourse.shoppingcart.dto.request.UpdatePasswordRequest;
import woowacourse.shoppingcart.dto.response.GetMeResponse;
import woowacourse.shoppingcart.dto.response.UniqueUsernameResponse;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public GetMeResponse getCustomer(User user) {
        return new GetMeResponse(findCustomer(user));
    }

    public UniqueUsernameResponse checkUniqueUsername(String username) {
        boolean isUnique = customerDao.findByUserName(username).isEmpty();
        return new UniqueUsernameResponse(isUnique);
    }

    @Transactional
    public void createCustomer(SignUpRequest request) {
        Password password = new Password(request.getPassword());
        Customer customer = new Customer(request.getUsername(),
                password, request.getNickname(), request.getAge());
        customerDao.save(customer);
    }

    @Transactional
    public void updateNicknameAndAge(User user, UpdateMeRequest request) {
        Customer customer = findCustomer(user);
        Customer updatedCustomer = new Customer(customer.getUsername(),
                customer.getEncryptedPassword(), request.getNickname(), request.getAge());
        customerDao.updateByUsername(updatedCustomer);
    }

    @Transactional
    public void updatePassword(User user, UpdatePasswordRequest request) {
        Customer customer = findCustomer(user);
        if (user.hasDifferentPassword(request.getOldPassword())) {
            throw new InvalidRequestException(InvalidExceptionType.WRONG_PASSWORD);
        }
        Customer updatedCustomer = new Customer(user.getUsername(),
                new Password(request.getNewPassword()), customer.getNickname(), customer.getAge());
        customerDao.updateByUsername(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(User user) {
        Customer customer = findCustomer(user);
        customerDao.delete(customer);
    }

    private Customer findCustomer(User user) {
        return customerDao.findByUserName(user.getUsername())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다."));
    }
}
