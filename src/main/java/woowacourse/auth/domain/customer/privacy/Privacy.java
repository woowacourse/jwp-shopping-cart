package woowacourse.auth.domain.customer.privacy;

import java.util.Objects;
import woowacourse.auth.domain.customer.address.FullAddress;

public class Privacy {
    private final Name name;
    private final Gender gender;
    private final BirthDay birthDay;
    private final Contact contact;
    private final FullAddress fullAddress;

    private Privacy(Name name, Gender gender, BirthDay birthDay, Contact contact, FullAddress fullAddress) {
        this.name = name;
        this.gender = gender;
        this.birthDay = birthDay;
        this.contact = contact;
        this.fullAddress = fullAddress;
    }

    public static Privacy of(String name, String gender, String birthDay, String contact, String address,
                             String detailAddress, String zoneCode) {
        return new Privacy(new Name(name), Gender.from(gender), BirthDay.from(birthDay), new Contact(contact),
                FullAddress.of(address, detailAddress, zoneCode));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Privacy privacy = (Privacy) o;
        return Objects.equals(name, privacy.name) && gender == privacy.gender && Objects.equals(
                birthDay, privacy.birthDay) && Objects.equals(contact, privacy.contact)
                && Objects.equals(fullAddress, privacy.fullAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender, birthDay, contact, fullAddress);
    }

    @Override
    public String toString() {
        return "Privacy{" +
                "name=" + name +
                ", gender=" + gender +
                ", birthDay=" + birthDay +
                ", contact=" + contact +
                ", fullAddress=" + fullAddress +
                '}';
    }
}
