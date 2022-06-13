package woowacourse.shoppingcart.domain;

import java.util.regex.Pattern;
import woowacourse.auth.support.CryptoUtils;

public class Customer {
    private static final Pattern loginPattern = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");

    private final Long id;
    private final String loginId;
    private final String name;
    private final String password;

    public Customer(String loginId, String name, String password) {
        this(null, loginId, name, password);
    }

    public Customer(Long id, String loginId, String name, String password) {
        validateLoginId(loginId);
        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }

    private void validateLoginId(String loginId) {
        if (!loginPattern.matcher(loginId).matches()) {
            throw new IllegalArgumentException("아이디 형식이 잘못되었습니다.");
        }
    }

    public boolean isSamePassword(String password) {
        return this.password.equals(password);
    }

    public void checkPasswordWithEncryption(String naivePassword) {
        String encryptedPassword = CryptoUtils.encrypt(naivePassword);
        if (!isSamePassword(encryptedPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
