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
        Customer customer = request.toDomain();
        checkDuplicateCondition(customer);
        return customerDao.save(toEntity(customer));
    }

    private void checkDuplicateCondition(Customer customer) {
        if (customerDao.exists(toEntity(customer))) {
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }
    }

    public GetMeResponse getMe(Long id) {
        Customer customer = customerDao.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다."))
                .toDomain();
        return new GetMeResponse(customer);
    }

    public UniqueUsernameResponse checkUniqueUsername(UniqueUsernameRequest request) {
        boolean isUnique = customerDao.findByUserName(request.getUsername()).isEmpty();
        return new UniqueUsernameResponse(isUnique);
    }

    @Transactional
    public void updateMe(Long id, UpdateMeRequest request) {
        Customer customer = customerDao.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다."))
                .toDomain();
        customer = customer.updateUsername(new Username(request.getUsername()))
                .updateNickname(new Nickname(request.getNickname()))
                .updateAge(new Age(request.getAge()));
        checkDuplicateCondition(customer);
        customerDao.update(toEntity(customer));
    }

    @Transactional
    public void updatePassword(Long id, UpdatePasswordRequest request) {
        Customer customer = customerDao.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다."))
                .toDomain();
        Password oldPassword = new Password(request.getOldPassword());
        if (!customer.hasSamePassword(oldPassword)) {
            throw new IllegalArgumentException("현재 비밀번호를 잘못 입력하였습니다.");
        }
        customer = customer.updatePassword(new Password(request.getNewPassword()));
        customerDao.update(toEntity(customer));
    }

    @Transactional
    public void deleteMe(Long id) {
        CustomerEntity customerEntity = customerDao.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다."));
        customerDao.delete(customerEntity);
    }

    private CustomerEntity toEntity(Customer customer) {
        return new CustomerEntity(null, customer.getUsername().getValue(), customer.getPassword().getValue()
        , customer.getNickname().getValue(), customer.getAge().getValue());
    }
}
