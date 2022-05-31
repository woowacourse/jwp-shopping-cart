package woowacourse.shoppingcart.dto;

public class AddressRequest {
    private String address;
    private String detailAddress;
    private String zoneCode;

    public AddressRequest() {
    }

    public AddressRequest(String address, String detailAddress, String zoneCode) {
        this.address = address;
        this.detailAddress = detailAddress;
        this.zoneCode = zoneCode;
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
        return "AddressRequest{" +
                "address='" + address + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", zoneCode='" + zoneCode + '\'' +
                '}';
    }
}
