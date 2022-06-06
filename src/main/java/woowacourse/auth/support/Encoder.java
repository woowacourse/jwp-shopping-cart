package woowacourse.auth.support;

public interface Encoder {

    String encrypt(String rawPassword);
}
