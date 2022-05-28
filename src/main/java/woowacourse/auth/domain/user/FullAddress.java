package woowacourse.auth.domain.user;

import java.util.Objects;

public class FullAddress {
    private final Address address;
    private final DetailAddress detailAddress;
    private final ZoneCode zoneCode;

    private FullAddress(Address address, DetailAddress detailAddress, ZoneCode zoneCode) {
        this.address = address;
        this.detailAddress = detailAddress;
        this.zoneCode = zoneCode;
    }

    public static FullAddress of(String address, String detailAddress, String zoneCode) {
        return new FullAddress(new Address(address), DetailAddress.from(detailAddress), new ZoneCode(zoneCode));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FullAddress that = (FullAddress) o;
        return Objects.equals(address, that.address) && Objects.equals(detailAddress,
                that.detailAddress) && Objects.equals(zoneCode, that.zoneCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, detailAddress, zoneCode);
    }

    @Override
    public String toString() {
        return "FullAddress{" +
                "address=" + address +
                ", detailAddress=" + detailAddress +
                ", zoneCode=" + zoneCode +
                '}';
    }
}
