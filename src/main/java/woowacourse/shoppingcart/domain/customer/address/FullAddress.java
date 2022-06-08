package woowacourse.shoppingcart.domain.customer.address;

import java.util.Objects;

public class FullAddress {
    private final Address address;
    private final DetailAddress detailAddress;
    private final Zonecode zonecode;

    private FullAddress(Address address, DetailAddress detailAddress, Zonecode zonecode) {
        this.address = address;
        this.detailAddress = detailAddress;
        this.zonecode = zonecode;
    }

    public static FullAddress of(String address, String detailAddress, String zonecode) {
        return new FullAddress(new Address(address), DetailAddress.from(detailAddress), new Zonecode(zonecode));
    }

    public Address getAddress() {
        return address;
    }

    public DetailAddress getDetailAddress() {
        return detailAddress;
    }

    public Zonecode getZonecode() {
        return zonecode;
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
                that.detailAddress) && Objects.equals(zonecode, that.zonecode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, detailAddress, zonecode);
    }

    @Override
    public String toString() {
        return "FullAddress{" +
                "address=" + address +
                ", detailAddress=" + detailAddress +
                ", zonecode=" + zonecode +
                '}';
    }
}
