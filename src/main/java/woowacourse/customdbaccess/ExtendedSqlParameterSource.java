package woowacourse.customdbaccess;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import woowacourse.shoppingcart.domain.Age;
import woowacourse.shoppingcart.domain.Nickname;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.domain.Username;

public class ExtendedSqlParameterSource extends BeanPropertySqlParameterSource {
    /**
     * Create a new BeanPropertySqlParameterSource for the given bean.
     *
     * @param object the bean instance to wrap
     */
    public ExtendedSqlParameterSource(Object object) {
        super(object);
    }

    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        Object value = super.getValue(paramName);
        if (value instanceof Username) {
            value = ((Username) value).getValue();
        }
        if (value instanceof Nickname) {
            value = ((Nickname) value).getValue();
        }
        if (value instanceof Password) {
            value = ((Password) value).getValue();
        }
        if (value instanceof Age) {
            value = ((Age) value).getValue();
        }
        return value;
    }
}
