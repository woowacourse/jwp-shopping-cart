package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.dto.AddressRequest;

public class AddressDto {

    private final String address;
    private final String detailAddress;
    private final String zoneCode;

    public AddressDto(final String address, final String detailAddress, final String zoneCode) {
        this.address = address;
        this.detailAddress = detailAddress;
        this.zoneCode = zoneCode;
    }

    public static AddressDto fromAddressRequest(final AddressRequest request) {
        return new AddressDto(request.getAddress(), request.getDetailAddress(), request.getZoneCode());
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
}
