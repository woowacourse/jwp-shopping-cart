package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dao.CustomerDao;
import woowacourse.auth.domain.user.Customer;
import woowacourse.auth.domain.user.Password;
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
                password.toEncrypted(), request.getNickname(), request.getAge());
        customerDao.save(customer);
    }

    @Transactional
    public void updateNicknameAndAge(Customer customer, UpdateMeRequest request) {
        String newNickname = request.getNickname();
        int newAge = request.getAge();
        Customer updatedCustomer = customer.updateNickname(newNickname).updateAge(newAge);
        customerDao.update(updatedCustomer);
    }

    @Transactional
    public void updatePassword(Customer customer, UpdatePasswordRequest request) {
        if (customer.hasDifferentPassword(request.getOldPassword())) {
            throw new InvalidRequestException(InvalidExceptionType.WRONG_PASSWORD);
        }
        Customer updatedCustomer = customer.updatePassword(request.getNewPassword());
        customerDao.update(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(Customer customer) {
        customerDao.delete(customer);
    }
}
