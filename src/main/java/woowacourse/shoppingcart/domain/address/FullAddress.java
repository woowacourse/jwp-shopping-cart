package woowacourse.shoppingcart.domain.address;

import woowacourse.auth.exception.BadRequestException;

public class FullAddress {

    static final String INVALID_ZONE_CODE_FORMAT = "유효하지 않은 우편번호입니다.";

    private static final int PROPER_ZONE_CODE_LENGTH = 5;

    private String address;
    private String DetailAddress;
    private String zoneCode;

    public FullAddress() {
    }

    public FullAddress(String address, String detailAddress, String zoneCode) {
        validateZoneCode(zoneCode);
        this.address = address;
        this.DetailAddress = detailAddress;
        this.zoneCode = zoneCode;
    }

    private void validateZoneCode(String zoneCode) {
        if (zoneCode.length() != PROPER_ZONE_CODE_LENGTH) {
            throw new BadRequestException(INVALID_ZONE_CODE_FORMAT);
        }
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
