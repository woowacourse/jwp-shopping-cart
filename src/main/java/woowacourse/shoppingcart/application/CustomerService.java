package woowacourse.shoppingcart.application;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.EmailAuthentication;
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.application.dto.EmailDto;
import woowacourse.shoppingcart.application.dto.ModifiedCustomerDto;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean checkEmailDuplication(final EmailDto emailDto) {
        return customerRepository.checkEmailDuplication(new Email(emailDto.getEmail()));
    }

    public Long createCustomer(final CustomerDto newCustomer) {
        final Customer customer = CustomerDto.toCustomer(newCustomer);
        try {
            return customerRepository.createCustomer(customer);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("이미 가입한 사용자입니다.");
        }
    }

    public void updateCustomer(ModifiedCustomerDto modifiedCustomerDto) {
        final Customer modifiedCustomer = ModifiedCustomerDto.toModifiedCustomerDto(modifiedCustomerDto);
        customerRepository.updateCustomer(modifiedCustomer);
    }

    public CustomerResponse findCustomerByEmail(EmailAuthentication email) {
        final Customer customer = customerRepository.findByUserEmail(new Email(email.getEmail()));
        return CustomerResponse.fromCustomer(customer);
    }

    public void deleteCustomer(final EmailAuthentication emailDto) {
        customerRepository.deleteCustomer(new Email(emailDto.getEmail()));
    }
}
