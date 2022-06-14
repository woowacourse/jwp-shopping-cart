package woowacourse.shoppingcart.domain.customer;

public class Customer {

    private final Long id;
    private final Username username;
    private Password password;
    private PhoneNumber phoneNumber;
    private String address;

    public Customer(Long id, Username username, Password password, PhoneNumber phoneNumber, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public static Customer of(String username, String password, String phoneNumber, String address) {
        return new Customer(
            null,
            new Username(username),
            Password.from(password),
            new PhoneNumber(phoneNumber),
            address);
    }

    public static Customer of(Long id, String username, String password, String phoneNumber, String address) {
        return new Customer(
            id,
            new Username(username),
            Password.from(password),
            new PhoneNumber(phoneNumber),
            address);
    }

    public void matchPassword(String password) {
        this.password.match(password);
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = this.phoneNumber.update(phoneNumber);
    }

    public void updateAddress(String address) {
        this.address = address;
    }

    public void updatePassword(String password) {
        this.password = this.password.update(password);
    }

    public Long getId() {
        return id;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Customer customer = (Customer)o;

        if (getId() != null ? !getId().equals(customer.getId()) : customer.getId() != null)
            return false;
        if (getUsername() != null ? !getUsername().equals(customer.getUsername()) : customer.getUsername() != null)
            return false;
        if (getPassword() != null ? !getPassword().equals(customer.getPassword()) : customer.getPassword() != null)
            return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(customer.getPhoneNumber()) :
            customer.getPhoneNumber() != null)
            return false;
        return getAddress() != null ? getAddress().equals(customer.getAddress()) : customer.getAddress() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        return result;
    }
}
