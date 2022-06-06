package woowacourse.study;

import org.springframework.stereotype.Component;

@Component
public class Hi implements HelloOrHi {
    @Override
    public String print() {
        return "hi";
    }
}
