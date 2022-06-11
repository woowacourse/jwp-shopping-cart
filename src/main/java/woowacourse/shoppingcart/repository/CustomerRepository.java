package woowacourse.shoppingcart.repository;

import java.time.format.DateTimeFormatter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.dao.AddressDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.PrivacyDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.domain.customer.ProfileImageUrl;
import woowacourse.shoppingcart.domain.customer.address.FullAddress;
import woowacourse.shoppingcart.domain.customer.privacy.Privacy;
import woowacourse.shoppingcart.entity.AddressEntity;
import woowacourse.shoppingcart.entity.CustomerEntity;
import woowacourse.shoppingcart.entity.PrivacyEntity;
import woowacourse.shoppingcart.exception.notfound.AddressNotFoundException;
import woowacourse.shoppingcart.exception.notfound.CustomerNotFoundException;
import woowacourse.shoppingcart.exception.notfound.PrivacyNotFoundException;

@Component
public class CustomerRepository {
    private final CustomerDao customerDao;
    private final PrivacyDao privacyDao;
    private final AddressDao addressDao;

    public CustomerRepository(CustomerDao customerDao, PrivacyDao privacyDao, AddressDao addressDao) {
        this.customerDao = customerDao;
        this.privacyDao = privacyDao;
        this.addressDao = addressDao;
    }

    public long save(Customer customer) {
        long customerId = saveCustomer(customer);
        savePrivacy(customerId, customer);
        saveAddress(customerId, customer);

        return customerId;
    }

    private long saveCustomer(Customer customer) {
        CustomerEntity customerEntity = convertCustomerToEntity(customer);
        return customerDao.save(customerEntity);
    }

    private void savePrivacy(long customerId, Customer customer) {
        PrivacyEntity privacyEntity = convertPrivacyToEntity(customer.getPrivacy());
        privacyDao.save(customerId, privacyEntity);
    }

    private void saveAddress(long customerId, Customer customer) {
        AddressEntity addressEntity = convertFullAddressToEntity(customer.getFullAddress());
        addressDao.save(customerId, addressEntity);
    }

    public Customer findById(long id) {
        PrivacyEntity privacyEntity = privacyDao.findById(id)
                .orElseThrow(PrivacyNotFoundException::new);
        Privacy privacy = convertPrivacyEntityToDomain(privacyEntity);

        AddressEntity addressEntity = addressDao.findById(id)
                .orElseThrow(AddressNotFoundException::new);
        FullAddress fullAddress = convertAddressEntityToDomain(addressEntity);

        CustomerEntity customerEntity = customerDao.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        return convertCustomerEntityToDomain(customerEntity, privacy, fullAddress);
    }

    public Customer findByEmail(String email) {
        CustomerEntity customerEntity = customerDao.findByEmail(email)
                .orElseThrow(CustomerNotFoundException::new);

        PrivacyEntity privacyEntity = privacyDao.findById(customerEntity.getId())
                .orElseThrow(PrivacyNotFoundException::new);
        Privacy privacy = convertPrivacyEntityToDomain(privacyEntity);

        AddressEntity addressEntity = addressDao.findById(customerEntity.getId())
                .orElseThrow(AddressNotFoundException::new);
        FullAddress fullAddress = convertAddressEntityToDomain(addressEntity);

        return convertCustomerEntityToDomain(customerEntity, privacy, fullAddress);
    }

    public void updateById(long id, Customer newCustomer) {
        PrivacyEntity privacyEntity = convertPrivacyToEntity(newCustomer.getPrivacy());
        privacyDao.update(id, privacyEntity);

        AddressEntity addressEntity = convertFullAddressToEntity(newCustomer.getFullAddress());
        addressDao.update(id, addressEntity);

        CustomerEntity customerEntity = convertCustomerToEntity(newCustomer);
        customerDao.update(id, customerEntity);
    }

    public void deleteById(long id) {
        privacyDao.delete(id);
        addressDao.delete(id);
        customerDao.delete(id);
    }

    public boolean existsByEmail(String email) {
        return customerDao.existsByEmail(email);
    }

    public boolean existsById(long id) {
        return customerDao.existsById(id);
    }

    private CustomerEntity convertCustomerToEntity(Customer customer) {
        return new CustomerEntity(
                customer.getEmail().getValue(),
                customer.getPassword().getValue(),
                customer.getProfileImageUrl().getValue(),
                customer.isTerms()
        );
    }

    private PrivacyEntity convertPrivacyToEntity(Privacy privacy) {
        return new PrivacyEntity(
                privacy.getName().getValue(),
                privacy.getGender().getValue(),
                privacy.getBirthday().getValue(),
                privacy.getContact().getValue()
        );
    }

    private AddressEntity convertFullAddressToEntity(FullAddress fullAddress) {
        return new AddressEntity(
                fullAddress.getAddress().getValue(),
                fullAddress.getDetailAddress().getValue(),
                fullAddress.getZonecode().getValue()
        );
    }

    private Privacy convertPrivacyEntityToDomain(PrivacyEntity privacyEntity) {
        return Privacy.of(
                privacyEntity.getName(),
                privacyEntity.getGender(),
                privacyEntity.getBirthday().format(DateTimeFormatter.ISO_DATE),
                privacyEntity.getContact()
        );
    }

    private FullAddress convertAddressEntityToDomain(AddressEntity addressEntity) {
        return FullAddress.of(
                addressEntity.getAddress(),
                addressEntity.getDetailAddress(),
                addressEntity.getZonecode()
        );
    }

    private Customer convertCustomerEntityToDomain(CustomerEntity customerEntity, Privacy privacy,
                                                   FullAddress fullAddress) {
        Long id = customerEntity.getId();
        Email email = new Email(customerEntity.getEmail());
        Password password = new Password(customerEntity.getPassword(), new BCryptPasswordEncoder());
        ProfileImageUrl profileImageUrl = new ProfileImageUrl(customerEntity.getProfileImageUrl());

        return new Customer(
                id,
                email,
                password,
                profileImageUrl,
                privacy,
                fullAddress,
                customerEntity.isTerms()
        );
    }
}
