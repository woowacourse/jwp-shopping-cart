package woowacourse.auth.entity;

import java.time.LocalDate;

public class PrivacyEntity {
    private final Integer customerId;
    private final String name;
    private final String gender;
    private final LocalDate birthDay;
    private final String contact;

    public PrivacyEntity(Integer customerId, String name, String gender, LocalDate birthDay, String contact) {
        this.customerId = customerId;
        this.name = name;
        this.gender = gender;
        this.birthDay = birthDay;
        this.contact = contact;
    }

    public PrivacyEntity(String name, String gender, LocalDate birthDay, String contact) {
        this(null, name, gender, birthDay, contact);
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirthDay() {
        return birthDay;
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
                ", birthDay=" + birthDay +
                ", contact='" + contact + '\'' +
                '}';
    }
}
