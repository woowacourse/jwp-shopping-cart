package cart.entity.vo;

import java.util.regex.Pattern;

public class Image {
    private final String image;
    private final static Pattern urlPattern = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");
    public Image(final String image) {
        validate(image);
        this.image = image;
    }
    private void validate(final String image){
        if(!urlPattern.matcher(image).matches()){
            throw new IllegalArgumentException("url 형식이 아닙니다.");
        }
    }
    public String value() {
        return image;
    }
}
