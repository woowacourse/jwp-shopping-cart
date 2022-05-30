package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.GetMeResponse;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.UpdateMeRequest;
import woowacourse.shoppingcart.dto.UpdatePasswordRequest;
import woowacourse.shoppingcart.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Long signUp(SignUpRequest request) {
        Customer customer = new Customer(request.getUsername(),
                request.getPassword(), request.getNickname(), request.getAge());
        return customerRepository.save(customer);
    }

    public GetMeResponse getMe(Long id) {
        return new GetMeResponse(customerRepository.find(id));
    }

    public void updateMe(Long id, UpdateMeRequest request) {
        Customer customer = customerRepository.find(id);
        Customer updatedCustomer = new Customer(request.getUsername(),
                customer.getPassword(),
                request.getNickname(),
                request.getAge());
        customerRepository.update(updatedCustomer);
    }

    public void updatePassword(Long id, UpdatePasswordRequest request) {
        Customer customer = customerRepository.find(id);
        if (!customer.hasSamePassword(request.getOldPassword())) {
            throw new IllegalArgumentException("현재 비밀번호를 잘못 입력하였습니다.");
        }
        Customer updatedCustomer = new Customer(customer.getUsername(),
                request.getNewPassword(),
                customer.getNickname(),
                customer.getAge());
        customerRepository.update(updatedCustomer);
    }

    public void deleteMe(Long id) {
        Customer customer = customerRepository.find(id);
        customerRepository.update(customer);
    }
}
