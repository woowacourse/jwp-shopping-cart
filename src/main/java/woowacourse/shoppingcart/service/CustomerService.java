package woowacourse.shoppingcart.service;

import java.time.format.DateTimeFormatter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.address.FullAddress;
import woowacourse.shoppingcart.domain.customer.privacy.Privacy;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.response.EmailDuplicationResponse;
import woowacourse.shoppingcart.exception.DuplicateEmailException;
import woowacourse.shoppingcart.exception.notfound.CustomerNotFoundException;
import woowacourse.shoppingcart.repository.CustomerRepository;

@Transactional
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public long create(CustomerRequest customerRequest) {
        validateDuplicateEmail(customerRequest);

        Customer customer = convertRequestToCustomer(customerRequest);
        return customerRepository.save(customer);
    }

    private void validateDuplicateEmail(CustomerRequest customerRequest) {
        if (customerRepository.existsByEmail(customerRequest.getEmail())) {
            throw new DuplicateEmailException();
        }
    }

    public CustomerResponse getCustomerById(long id) {
        try {
            Customer customer = customerRepository.findById(id);
            return convertCustomerToResponse(customer);
        } catch (EmptyResultDataAccessException e) {
            throw new CustomerNotFoundException();
        }
    }

    public void updateCustomerById(long customerId, CustomerRequest customerRequest) {
        validateExists(customerId);
        Customer newCustomer = convertRequestToCustomer(customerRequest);
        customerRepository.updateById(customerId, newCustomer);
    }

    public void deleteCustomer(long customerId) {
        validateExists(customerId);
        customerRepository.deleteById(customerId);
    }

    public EmailDuplicationResponse isDuplicatedEmail(String email) {
        return new EmailDuplicationResponse(customerRepository.existsByEmail(email));
    }

    private void validateExists(long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException();
        }
    }

    private CustomerResponse convertCustomerToResponse(Customer customer) {
        return new CustomerResponse(
                customer.getEmail().getValue(),
                customer.getProfileImageUrl().getValue(),
                customer.getPrivacy().getName().getValue(),
                customer.getPrivacy().getGender().getValue(),
                customer.getPrivacy().getBirthday().getValue().format(DateTimeFormatter.ISO_DATE),
                customer.getPrivacy().getContact().getValue(),
                customer.getFullAddress().getAddress().getValue(),
                customer.getFullAddress().getDetailAddress().getValue(),
                customer.getFullAddress().getZonecode().getValue(),
                customer.isTerms()
        );
    }

    private Customer convertRequestToCustomer(CustomerRequest customerRequest) {
        Privacy privacy = Privacy.of(customerRequest.getName(), customerRequest.getGender(),
                customerRequest.getBirthday(), customerRequest.getContact());
        FullAddress fullAddress = FullAddress.of(customerRequest.getAddress(), customerRequest.getDetailAddress(),
                customerRequest.getZonecode());

        return Customer.of(customerRequest.getEmail(), customerRequest.getPassword(),
                customerRequest.getProfileImageUrl(), privacy, fullAddress, customerRequest.isTerms());
    }
}
