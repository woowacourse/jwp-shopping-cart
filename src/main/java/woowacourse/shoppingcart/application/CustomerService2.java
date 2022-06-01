package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.domain.Customer2;
import woowacourse.auth.domain.User2;
import woowacourse.shoppingcart.dao.CustomerDao2;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UpdateMeRequest;
import woowacourse.shoppingcart.dto.request.UpdatePasswordRequest;
import woowacourse.shoppingcart.dto.response.GetMeResponse;
import woowacourse.shoppingcart.dto.response.UniqueUsernameResponse;
import woowacourse.shoppingcart.exception.NotFoundException;

@Service
public class CustomerService2 {

    private final CustomerDao2 customerDao;

    public CustomerService2(CustomerDao2 customerDao) {
        this.customerDao = customerDao;
    }

    public GetMeResponse getCustomer(User2 user) {
        return new GetMeResponse(findCustomer(user));
    }

    public UniqueUsernameResponse checkUniqueUsername(String username) {
        boolean isUnique = customerDao.findByUserName(username).isEmpty();
        return new UniqueUsernameResponse(isUnique);
    }

    @Transactional
    public Long createCustomer(SignUpRequest request) {
        Customer2 customer = new Customer2(request.getUsername(),
                request.getPassword(), request.getNickname(), request.getAge());
        return customerDao.save(customer);
    }

    @Transactional
    public void updateNicknameAndAge(User2 user, UpdateMeRequest request) {
        Customer2 customer = findCustomer(user);
        if(!customer.hasSameUsername(request.getUsername())) {
            throw new IllegalArgumentException("아이디는 수정할 수 없습니다.");
        }
        Customer2 updatedCustomer = new Customer2(customer.getUsername(),
                customer.getPassword(), request.getNickname(), request.getAge());
        customerDao.updateByUsername(updatedCustomer);
    }

    @Transactional
    public void updatePassword(User2 user, UpdatePasswordRequest request) {
        Customer2 customer = findCustomer(user);
        if (!user.hasSamePassword(request.getOldPassword())) {
            throw new IllegalArgumentException("현재 비밀번호를 잘못 입력하였습니다.");
        }
        Customer2 updatedCustomer = new Customer2(user.getUsername(),
                request.getNewPassword(), customer.getNickname(), customer.getAge());
        customerDao.updateByUsername(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(User2 user) {
        Customer2 customer = findCustomer(user);
        customerDao.delete(customer);
    }

    private Customer2 findCustomer(User2 user) {
        return customerDao.findByUserName(user.getUsername())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다."));
    }
}
