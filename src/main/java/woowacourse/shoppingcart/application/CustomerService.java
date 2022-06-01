package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Nickname;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.domain.Username;
import woowacourse.shoppingcart.dto.request.UniqueUsernameRequest;
import woowacourse.shoppingcart.dto.response.GetMeResponse;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UpdateMeRequest;
import woowacourse.shoppingcart.dto.request.UpdatePasswordRequest;
import woowacourse.shoppingcart.dto.response.UniqueUsernameResponse;
import woowacourse.shoppingcart.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Long signUp(SignUpRequest request) {
        Customer customer = new Customer(request.getUsername(),
                request.getPassword(), request.getNickname(), request.getAge());
        return customerRepository.save(customer);
    }

    public GetMeResponse getMe(Long id) {
        return new GetMeResponse(customerRepository.find(id));
    }

    public UniqueUsernameResponse checkUniqueUsername(UniqueUsernameRequest request) {
        boolean isUnique = !customerRepository.checkUsernameExistence(request.getUsername());
        return new UniqueUsernameResponse(isUnique);
    }

    @Transactional
    public void updateMe(Long id, UpdateMeRequest request) {
        Customer customer = customerRepository.find(id);
        Customer updatedCustomer = new Customer(new Username(request.getUsername()),
                customer.getPassword(),
                new Nickname(request.getNickname()),
                request.getAge());
        customerRepository.update(id, updatedCustomer);
    }

    @Transactional
    public void updatePassword(Long id, UpdatePasswordRequest request) {
        Customer customer = customerRepository.find(id);
        if (!customer.hasSamePassword(request.getOldPassword())) {
            throw new IllegalArgumentException("현재 비밀번호를 잘못 입력하였습니다.");
        }
        Customer updatedCustomer = new Customer(customer.getUsername(),
                new Password(request.getNewPassword()),
                customer.getNickname(),
                customer.getAge());
        customerRepository.update(id, updatedCustomer);
    }

    @Transactional
    public void deleteMe(Long id) {
        Customer customer = customerRepository.find(id);
        customerRepository.delete(id, customer);
    }
}
