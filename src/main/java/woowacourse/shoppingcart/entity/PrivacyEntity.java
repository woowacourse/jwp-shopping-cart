package woowacourse.shoppingcart.entity;

import java.time.LocalDate;

public class PrivacyEntity {
    private final Long customerId;
    private final String name;
    private final String gender;
    private final LocalDate birthday;
    private final String contact;

    public PrivacyEntity(Long customerId, String name, String gender, LocalDate birthday, String contact) {
        this.customerId = customerId;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.contact = contact;
    }

    public PrivacyEntity(String name, String gender, LocalDate birthday, String contact) {
        this(null, name, gender, birthday, contact);
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getContact() {
        return contact;
    }

    @Override
    public String toString() {
        return "PrivacyEntity{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", contact='" + contact + '\'' +
                '}';
    }
}
