package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Age;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Nickname;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.domain.Username;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UniqueUsernameRequest;
import woowacourse.shoppingcart.dto.request.UpdateMeRequest;
import woowacourse.shoppingcart.dto.request.UpdatePasswordRequest;
import woowacourse.shoppingcart.dto.response.GetMeResponse;
import woowacourse.shoppingcart.dto.response.UniqueUsernameResponse;
import woowacourse.shoppingcart.entity.CustomerEntity;
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
        return customerDao.save(new CustomerEntity(customer));
    }

    public GetMeResponse getMe(Long id) {
        CustomerEntity customerEntity = customerDao.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다."));
        return new GetMeResponse(customerEntity.toDomain());
    }

    public UniqueUsernameResponse checkUniqueUsername(UniqueUsernameRequest request) {
        boolean isUnique = customerDao.findByUserName(request.getUsername()).isEmpty();
        return new UniqueUsernameResponse(isUnique);
    }

    @Transactional
    public void updateMe(Long id, UpdateMeRequest request) {
        Customer customer = customerDao.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다.")).toDomain();
        Customer updatedCustomer = new Customer(new Username(request.getUsername()),
                customer.getPassword(),
                new Nickname(request.getNickname()),
                new Age(request.getAge()));
        customerDao.update(new CustomerEntity(id, updatedCustomer));
    }

    @Transactional
    public void updatePassword(Long id, UpdatePasswordRequest request) {
        Customer customer = customerDao.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다.")).toDomain();
        Password oldPassword = new Password(request.getOldPassword());
        if (!customer.hasSamePassword(oldPassword)) {
            throw new IllegalArgumentException("현재 비밀번호를 잘못 입력하였습니다.");
        }
        Customer updatedCustomer = new Customer(customer.getUsername(),
                new Password(request.getNewPassword()),
                customer.getNickname(),
                customer.getAge());
        customerDao.update(new CustomerEntity(id, updatedCustomer));
    }

    @Transactional
    public void deleteMe(Long id) {
        Customer customer = customerDao.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다.")).toDomain();
        customerDao.delete(new CustomerEntity(id, customer));
    }
}
