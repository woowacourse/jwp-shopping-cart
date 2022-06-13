package woowacourse.shoppingcart.dto.request;

public class AddressRequest {
    private String address;
    private String detailAddress;
    private String zonecode;

    public AddressRequest() {
    }

    public AddressRequest(String address, String detailAddress, String zonecode) {
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
        return "AddressRequest{" +
                "address='" + address + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", zonecode='" + zonecode + '\'' +
                '}';
    }
}
