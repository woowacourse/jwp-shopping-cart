package woowacourse.shoppingcart.dto.response;

public class AddressResponse {
    private String address;
    private String detailAddress;
    private String zonecode;

    public AddressResponse() {
    }

    public AddressResponse(String address, String detailAddress, String zonecode) {
        this.address = address;
        this.detailAddress = detailAddress;
        this.zonecode = zonecode;
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
        return "AddressResponse{" +
                "address='" + address + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", zonecode='" + zonecode + '\'' +
                '}';
    }
}
