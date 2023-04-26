package cart.business.domain;

import java.net.MalformedURLException;
import java.net.URL;

public class ProductImage {

    private final URL url;

    public ProductImage(String url) throws MalformedURLException {
        this.url = new URL(url);
    }
}
