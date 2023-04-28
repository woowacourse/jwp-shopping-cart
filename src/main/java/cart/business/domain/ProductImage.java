package cart.business.domain;

import java.net.MalformedURLException;
import java.net.URL;

public class ProductImage {

    private final URL url;

    public ProductImage(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("URL 포맷이 맞지 않습니다");
        }
    }

    public String getValue() {
        return url.toString();
    }
}
