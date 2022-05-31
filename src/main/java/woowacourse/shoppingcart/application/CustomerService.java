package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.AddressDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.PrivacyDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.address.FullAddress;
import woowacourse.shoppingcart.domain.customer.privacy.Privacy;
import woowacourse.shoppingcart.dto.CustomerRequest;
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
        CustomerEntity customerEntity = convertCustomerToEntity(customer);
        PrivacyEntity privacyEntity = convertPrivacyToEntity(customer.getPrivacy());
        AddressEntity addressEntity = convertAddressToEntity(customer.getFullAddress());

        int customerId = customerDao.save(customerEntity);
        privacyDao.save(customerId, privacyEntity);
        addressDao.save(customerId, addressEntity);

        return customerId;
    }

    private Customer convertRequestToCustomer(CustomerRequest customerRequest) {
        Privacy privacy = Privacy.of(customerRequest.getName(), customerRequest.getGender(),
                customerRequest.getBirthDay(), customerRequest.getContact());
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
                privacy.getBirthDay().getValue(), privacy.getContact().getValue());
    }

    private AddressEntity convertAddressToEntity(FullAddress fullAddress) {
        return new AddressEntity(fullAddress.getAddress().getValue(), fullAddress.getDetailAddress().getValue(),
                fullAddress.getZoneCode().getValue());
    }


}
