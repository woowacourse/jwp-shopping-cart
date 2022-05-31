package woowacourse.shoppingcart.domain.customer.privacy;

import java.util.Objects;

public class Privacy {
    private final Name name;
    private final Gender gender;
    private final BirthDay birthDay;
    private final Contact contact;

    private Privacy(Name name, Gender gender, BirthDay birthDay, Contact contact) {
        this.name = name;
        this.gender = gender;
        this.birthDay = birthDay;
        this.contact = contact;
    }

    public static Privacy of(String name, String gender, String birthDay, String contact) {
        return new Privacy(new Name(name), Gender.from(gender), BirthDay.from(birthDay), new Contact(contact));
    }

    public Name getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public BirthDay getBirthDay() {
        return birthDay;
    }

    public Contact getContact() {
        return contact;
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
                birthDay, privacy.birthDay) && Objects.equals(contact, privacy.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender, birthDay, contact);
    }

    @Override
    public String toString() {
        return "Privacy{" +
                "name=" + name +
                ", gender=" + gender +
                ", birthDay=" + birthDay +
                ", contact=" + contact +
                '}';
    }
}
