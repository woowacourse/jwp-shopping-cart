package woowacourse.shoppingcart.entity;

public class AddressEntity {
    private final Long customerId;
    private final String address;
    private final String detailAddress;
    private final String zonecode;

    public AddressEntity(Long customerId, String address, String detailAddress, String zonecode) {
        this.customerId = customerId;
        this.address = address;
        this.detailAddress = detailAddress;
        this.zonecode = zonecode;
    }

    public AddressEntity(String address, String detailAddress, String zonecode) {
        this(null, address, detailAddress, zonecode);
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getAddress() {
        return address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public String getZonecode() {
        return zonecode;
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "customerId=" + customerId +
                ", address='" + address + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", zonecode='" + zonecode + '\'' +
                '}';
    }
}
