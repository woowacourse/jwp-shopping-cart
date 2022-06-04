package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.domain.Password;
import woowacourse.auth.domain.User;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UpdateMeRequest;
import woowacourse.shoppingcart.dto.request.UpdatePasswordRequest;
import woowacourse.shoppingcart.dto.response.GetMeResponse;
import woowacourse.shoppingcart.dto.response.UniqueUsernameResponse;
import woowacourse.exception.NotFoundException;

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
    public Long createCustomer(SignUpRequest request) {
        Password password = new Password(request.getPassword());
        Customer customer = new Customer(request.getUsername(),
                password, request.getNickname(), request.getAge());
        return customerDao.save(customer);
    }

    @Transactional
    public void updateNicknameAndAge(User user, UpdateMeRequest request) {
        Customer customer = findCustomer(user);
        if (!customer.hasSameUsername(request.getUsername())) {
            throw new IllegalArgumentException("아이디는 수정할 수 없습니다.");
        }
        Customer updatedCustomer = new Customer(customer.getUsername(),
                customer.getEncryptedPassword(), request.getNickname(), request.getAge());
        customerDao.updateByUsername(updatedCustomer);
    }

    @Transactional
    public void updatePassword(User user, UpdatePasswordRequest request) {
        Customer customer = findCustomer(user);
        if (user.hasDifferentPassword(request.getOldPassword())) {
            throw new IllegalArgumentException("현재 비밀번호를 잘못 입력하였습니다.");
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
