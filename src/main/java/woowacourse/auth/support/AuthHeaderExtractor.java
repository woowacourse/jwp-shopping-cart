package woowacourse.auth.support;

import javax.servlet.http.HttpServletRequest;

public interface AuthHeaderExtractor {
    String extract(HttpServletRequest httpServletRequest);
}
