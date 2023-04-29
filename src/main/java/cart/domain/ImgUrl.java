package cart.domain;

public class ImgUrl {

    private final String imgUrl;

    public ImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public static ImgUrl of(String imgUrl) {
        return new ImgUrl(imgUrl);
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
