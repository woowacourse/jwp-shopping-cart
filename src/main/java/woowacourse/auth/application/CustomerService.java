package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dao.CustomerDao;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.domain.Password;
import woowacourse.auth.dto.request.SignUpRequest;
import woowacourse.auth.dto.request.UpdateMeRequest;
import woowacourse.auth.dto.request.UpdatePasswordRequest;
import woowacourse.auth.dto.response.UniqueUsernameResponse;
import woowacourse.common.exception.InvalidExceptionType;
import woowacourse.common.exception.InvalidRequestException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
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
    public void updateNicknameAndAge(Customer customer, UpdateMeRequest request) {
        Customer updatedCustomer = new Customer(customer.getUsername(),
                customer.getEncryptedPassword(), request.getNickname(), request.getAge());
        customerDao.updateByUsername(updatedCustomer);
    }

    @Transactional
    public void updatePassword(Customer customer, UpdatePasswordRequest request) {
        if (customer.hasDifferentPassword(request.getOldPassword())) {
            throw new InvalidRequestException(InvalidExceptionType.WRONG_PASSWORD);
        }
        Customer updatedCustomer = new Customer(customer.getUsername(),
                new Password(request.getNewPassword()), customer.getNickname(), customer.getAge());
        customerDao.updateByUsername(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(Customer customer) {
        customerDao.delete(customer);
    }
}
