package woowacourse.auth.domain;

import javax.servlet.http.HttpServletRequest;

public interface BearerExtractor {
    String extract(HttpServletRequest httpServletRequest);
}
