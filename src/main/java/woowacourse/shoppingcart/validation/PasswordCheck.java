package woowacourse.shoppingcart.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= PasswordValidator.class)
public @interface PasswordCheck {
    String message() default "[ERROR] 비밀번호는 한글이나 공백을 포함할 수 없습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
