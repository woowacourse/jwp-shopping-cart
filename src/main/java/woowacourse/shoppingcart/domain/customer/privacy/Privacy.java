package woowacourse.shoppingcart.domain.customer.privacy;

import java.util.Objects;

public class Privacy {
    private final Name name;
    private final Gender gender;
    private final Birthday birthday;
    private final Contact contact;

    private Privacy(Name name, Gender gender, Birthday birthday, Contact contact) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.contact = contact;
    }

    public static Privacy of(String name, String gender, String birthday, String contact) {
        return new Privacy(new Name(name), Gender.from(gender), Birthday.from(birthday), new Contact(contact));
    }

    public Name getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Birthday getBirthday() {
        return birthday;
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
                birthday, privacy.birthday) && Objects.equals(contact, privacy.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender, birthday, contact);
    }

    @Override
    public String toString() {
        return "Privacy{" +
                "name=" + name +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", contact=" + contact +
                '}';
    }
}
