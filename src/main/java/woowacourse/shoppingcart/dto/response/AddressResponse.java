package woowacourse.shoppingcart.dto.response;

public class AddressResponse {
    private String address;
    private String detailAddress;
    private String zoneCode;

    public AddressResponse() {
    }

    public AddressResponse(String address, String detailAddress, String zoneCode) {
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
        return "AddressResponse{" +
                "address='" + address + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", zoneCode='" + zoneCode + '\'' +
                '}';
    }
}
