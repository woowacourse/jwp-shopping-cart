package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UniqueUsernameRequest;
import woowacourse.shoppingcart.dto.request.UpdateMeRequest;
import woowacourse.shoppingcart.dto.request.UpdatePasswordRequest;
import woowacourse.shoppingcart.dto.response.GetMeResponse;
import woowacourse.shoppingcart.dto.response.UniqueUsernameResponse;
import woowacourse.shoppingcart.exception.NotFoundException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public Long signUp(SignUpRequest request) {
        Customer customer = new Customer(request.getUsername(),
                request.getPassword(), request.getNickname(), request.getAge());
        return customerDao.save(customer);
    }

    public GetMeResponse getMe(Long id) {
        return new GetMeResponse(findCustomer(id));
    }

    public UniqueUsernameResponse checkUniqueUsername(UniqueUsernameRequest request) {
        String username = request.getUsername();
        boolean isUnique =  customerDao.findByUserName(username).isEmpty();
        return new UniqueUsernameResponse(isUnique);
    }

    @Transactional
    public void updateMe(Long id, UpdateMeRequest request) {
        Customer customer = findCustomer(id);
        Customer updatedCustomer = new Customer(id, request.getUsername(),
                customer.getPassword(), request.getNickname(), request.getAge());
        customerDao.update(updatedCustomer);
    }

    @Transactional
    public void updatePassword(Long id, UpdatePasswordRequest request) {
        Customer customer = findCustomer(id);
        if (!customer.hasSamePassword(request.getOldPassword())) {
            throw new IllegalArgumentException("현재 비밀번호를 잘못 입력하였습니다.");
        }
        Customer updatedCustomer = new Customer(id, customer.getUsername(),
                request.getNewPassword(), customer.getNickname(), customer.getAge());
        customerDao.update(updatedCustomer);
    }

    @Transactional
    public void deleteMe(Long id) {
        Customer customer = findCustomer(id);
        customerDao.delete(customer);
    }

    private Customer findCustomer(Long id) {
        return customerDao.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다."));
    }
}
