package woowacourse.shoppingcart.dto.response;

public class AddressResponse {

    private String address;
    private String DetailAddress;
    private String zoneCode;

    public AddressResponse() {
    }

    public AddressResponse(String address, String detailAddress, String zipCode) {
        this.address = address;
        DetailAddress = detailAddress;
        this.zoneCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public String getDetailAddress() {
        return DetailAddress;
    }

    public String getZoneCode() {
        return zoneCode;
    }
}
