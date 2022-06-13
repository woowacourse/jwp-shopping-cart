package woowacourse.shoppingcart.ui;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class UriCreator {

    public static URI withCurrentPath(final String additionalValue) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(additionalValue)
                .build().toUri();
    }
}
