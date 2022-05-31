package woowacourse.auth.entity;

public class AddressEntity {
    private final Integer customerId;
    private final String address;
    private final String detailAddress;
    private final String zoneCode;

    public AddressEntity(Integer customerId, String address, String detailAddress, String zoneCode) {
        this.customerId = customerId;
        this.address = address;
        this.detailAddress = detailAddress;
        this.zoneCode = zoneCode;
    }

    public AddressEntity(String address, String detailAddress, String zoneCode) {
        this(null, address, detailAddress, zoneCode);
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getAddress() {
        return address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "customerId=" + customerId +
                ", address='" + address + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", zoneCode='" + zoneCode + '\'' +
                '}';
    }
}
