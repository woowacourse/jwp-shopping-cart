package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.exception.InvalidArgumentRequestException;

public class Age {
    private final int age;

    public Age(int age) {
        validateAgeRange(age);
        this.age = age;
    }

    private void validateAgeRange(int age) {
        if (age < 0) {
            throw new InvalidArgumentRequestException("나이는 0살 이상이어야 합니다.");
        }
    }

    public int getAge() {
        return age;
    }
}
