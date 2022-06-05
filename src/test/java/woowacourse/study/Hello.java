package woowacourse.study;

import org.springframework.stereotype.Component;

@Component
public class Hello implements HelloOrHi {
    @Override
    public String print() {
        return "hello";
    }
}
