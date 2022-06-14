package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Age;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Nickname;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UpdateMeRequest;
import woowacourse.shoppingcart.dto.request.UpdatePasswordRequest;
import woowacourse.shoppingcart.dto.response.MeResponse;
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
        Customer customer = request.toDomain();
        checkDuplicateCondition(customer);
        return customerDao.save(customer);
    }

    private void checkDuplicateCondition(Customer customer) {
        if (customerDao.exists(customer)) {
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }
    }

    @Transactional(readOnly = true)
    public MeResponse getMe(Long id) {
        Customer customer = customerDao.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다."));
        return new MeResponse(customer);
    }

    public UniqueUsernameResponse checkUniqueUsername(String username) {
        boolean isUnique = customerDao.findByUserName(username).isEmpty();
        return new UniqueUsernameResponse(isUnique);
    }

    @Transactional
    public void updateMe(Long id, UpdateMeRequest request) {
        Customer customer = customerDao.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다."));
        customer = customer.updateNickname(new Nickname(request.getNickname()))
                .updateAge(new Age(request.getAge()));
        customerDao.update(customer);
    }

    @Transactional
    public void updatePassword(Long id, UpdatePasswordRequest request) {
        Customer customer = customerDao.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다."));
        Password oldPassword = new Password(request.getOldPassword());
        if (!customer.hasSamePassword(oldPassword)) {
            throw new IllegalArgumentException("현재 비밀번호를 잘못 입력하였습니다.");
        }
        customer = customer.updatePassword(new Password(request.getNewPassword()));
        customerDao.update(customer);
    }

    @Transactional
    public void deleteMe(Long id) {
        Customer customer = customerDao.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다."));
        customerDao.delete(customer);
    }
}
