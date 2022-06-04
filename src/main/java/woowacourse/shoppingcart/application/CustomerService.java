package woowacourse.shoppingcart.application;

import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.AddressDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.PrivacyDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.address.FullAddress;
import woowacourse.shoppingcart.domain.customer.privacy.Privacy;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.EmailDuplicationResponse;
import woowacourse.shoppingcart.entity.AddressEntity;
import woowacourse.shoppingcart.entity.CustomerEntity;
import woowacourse.shoppingcart.entity.PrivacyEntity;

@Transactional
@Service
public class CustomerService {
    private final CustomerDao customerDao;
    private final PrivacyDao privacyDao;
    private final AddressDao addressDao;

    public CustomerService(CustomerDao customerDao, PrivacyDao privacyDao, AddressDao addressDao) {
        this.customerDao = customerDao;
        this.privacyDao = privacyDao;
        this.addressDao = addressDao;
    }

    public int create(CustomerRequest customerRequest) {
        Customer customer = convertRequestToCustomer(customerRequest);

        int customerId = customerDao.save(customer);
        privacyDao.save(customerId, customer.getPrivacy());
        addressDao.save(customerId, customer.getFullAddress());

        return customerId;
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(int id) {
        CustomerEntity customerEntity = customerDao.findById(id);
        PrivacyEntity privacyEntity = privacyDao.findById(id);
        AddressEntity addressEntity = addressDao.findById(id);
        return convertEntityToResponse(customerEntity, privacyEntity, addressEntity);
    }

    public void updateCustomerById(int customerId, CustomerRequest customerRequest) {
        validateExists(customerId);

        Customer customer = convertRequestToCustomer(customerRequest);

        customerDao.update(customerId, customer);
        privacyDao.update(customerId, customer.getPrivacy());
        addressDao.update(customerId, customer.getFullAddress());
    }

    public void deleteCustomer(int customerId) {
        validateExists(customerId);

        addressDao.delete(customerId);
        privacyDao.delete(customerId);
        customerDao.delete(customerId);
    }

    private void validateExists(int customerId) {
        customerDao.findById(customerId);
    }

    @Transactional(readOnly = true)
    public EmailDuplicationResponse isDuplicatedEmail(String email) {
        return new EmailDuplicationResponse(customerDao.hasEmail(email));
    }

    private Customer convertRequestToCustomer(CustomerRequest customerRequest) {
        Privacy privacy = Privacy.of(customerRequest.getName(), customerRequest.getGender(),
                customerRequest.getBirthday(), customerRequest.getContact());
        FullAddress fullAddress = FullAddress.of(customerRequest.getAddress(),
                customerRequest.getDetailAddress(), customerRequest.getZonecode());

        return Customer.of(customerRequest.getEmail(), customerRequest.getPassword(),
                customerRequest.getProfileImageUrl(), privacy, fullAddress, customerRequest.isTerms());
    }

    private CustomerResponse convertEntityToResponse(CustomerEntity customerEntity, PrivacyEntity privacyEntity,
                                                     AddressEntity addressEntity) {

        return new CustomerResponse(customerEntity.getEmail(), customerEntity.getProfileImageUrl(),
                privacyEntity.getName(), privacyEntity.getGender(),
                privacyEntity.getBirthday().format(DateTimeFormatter.ISO_DATE), privacyEntity.getContact(),
                addressEntity.getAddress(), addressEntity.getDetailAddress(), addressEntity.getZoneCode(),
                customerEntity.isTerms());
    }
}
