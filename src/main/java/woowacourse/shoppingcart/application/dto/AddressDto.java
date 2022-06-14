package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.address.FullAddress;
import woowacourse.shoppingcart.dto.request.AddressRequest;

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
        return new AddressDto(request.getAddress(), request.getDetailAddress(), request.getZonecode());
    }

    public static AddressDto fromFullAddress(final FullAddress address) {
        return new AddressDto(address.getAddress(), address.getDetailAddress(), address.getZoneCode());
    }

    public static FullAddress toFullAddress(final AddressDto request) {
        return new FullAddress(request.getAddress(), request.getDetailAddress(), request.getZoneCode());
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
