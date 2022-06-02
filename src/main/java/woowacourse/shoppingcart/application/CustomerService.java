package woowacourse.shoppingcart.application;

import java.time.format.DateTimeFormatter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.AddressDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.PrivacyDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.address.FullAddress;
import woowacourse.shoppingcart.domain.customer.privacy.Privacy;
import woowacourse.shoppingcart.dto.AddressResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.EmailDuplicationResponse;
import woowacourse.shoppingcart.entity.AddressEntity;
import woowacourse.shoppingcart.entity.CustomerEntity;
import woowacourse.shoppingcart.entity.PrivacyEntity;
import woowacourse.shoppingcart.exception.notfound.CustomerNotFoundException;

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
        CustomerEntity customerEntity = convertCustomerToEntity(customer);
        PrivacyEntity privacyEntity = convertPrivacyToEntity(customer.getPrivacy());
        AddressEntity addressEntity = convertAddressToEntity(customer.getFullAddress());

        int customerId = customerDao.save(customerEntity);
        privacyDao.save(customerId, privacyEntity);
        addressDao.save(customerId, addressEntity);

        return customerId;
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(int id) {
        try {
            CustomerEntity customerEntity = customerDao.findById(id);
            PrivacyEntity privacyEntity = privacyDao.findById(id);
            AddressEntity addressEntity = addressDao.findById(id);
            return convertEntityToResponse(customerEntity, privacyEntity, addressEntity);
        } catch (EmptyResultDataAccessException e) {
            throw new CustomerNotFoundException();
        }
    }

    public void updateCustomerById(int customerId, CustomerRequest customerRequest) {
        validateExists(customerId);

        Customer customer = convertRequestToCustomer(customerRequest);
        CustomerEntity customerEntity = convertCustomerToEntity(customer);
        PrivacyEntity privacyEntity = convertPrivacyToEntity(customer.getPrivacy());
        AddressEntity addressEntity = convertAddressToEntity(customer.getFullAddress());

        customerDao.update(customerId, customerEntity);
        privacyDao.update(customerId, privacyEntity);
        addressDao.update(customerId, addressEntity);
    }

    public void deleteCustomer(int customerId) {
        validateExists(customerId);

        addressDao.delete(customerId);
        privacyDao.delete(customerId);
        customerDao.delete(customerId);
    }

    private void validateExists(int customerId) {
        try {
            customerDao.findById(customerId);
            privacyDao.findById(customerId);
            addressDao.findById(customerId);
        } catch (EmptyResultDataAccessException e) {
            throw new CustomerNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    public EmailDuplicationResponse isDuplicatedEmail(String email) {
        return new EmailDuplicationResponse(customerDao.hasEmail(email));
    }

    private Customer convertRequestToCustomer(CustomerRequest customerRequest) {
        Privacy privacy = Privacy.of(customerRequest.getName(), customerRequest.getGender(),
                customerRequest.getBirthday(), customerRequest.getContact());
        FullAddress fullAddress = FullAddress.of(customerRequest.getFullAddress().getAddress(),
                customerRequest.getFullAddress().getDetailAddress(), customerRequest.getFullAddress().getZoneCode());

        return Customer.of(customerRequest.getEmail(), customerRequest.getPassword(),
                customerRequest.getProfileImageUrl(), privacy, fullAddress, customerRequest.isTerms());
    }

    private CustomerEntity convertCustomerToEntity(Customer customer) {
        return new CustomerEntity(customer.getEmail().getValue(), customer.getPassword().getValue(),
                customer.getProfileImageUrl().getValue(), customer.isTerms());
    }

    private PrivacyEntity convertPrivacyToEntity(Privacy privacy) {
        return new PrivacyEntity(privacy.getName().getValue(), privacy.getGender().getValue(),
                privacy.getBirthday().getValue(), privacy.getContact().getValue());
    }

    private AddressEntity convertAddressToEntity(FullAddress fullAddress) {
        return new AddressEntity(fullAddress.getAddress().getValue(), fullAddress.getDetailAddress().getValue(),
                fullAddress.getZoneCode().getValue());
    }

    private CustomerResponse convertEntityToResponse(CustomerEntity customerEntity, PrivacyEntity privacyEntity,
                                                     AddressEntity addressEntity) {
        AddressResponse addressResponse = new AddressResponse(addressEntity.getAddress(),
                addressEntity.getDetailAddress(), addressEntity.getZoneCode());

        return new CustomerResponse(customerEntity.getEmail(), customerEntity.getProfileImageUrl(),
                privacyEntity.getName(), privacyEntity.getGender(),
                privacyEntity.getBirthDay().format(DateTimeFormatter.ISO_DATE), privacyEntity.getContact(),
                addressResponse, customerEntity.isTerms());
    }
}
