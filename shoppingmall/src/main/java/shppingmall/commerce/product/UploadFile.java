package shppingmall.commerce.product;

import lombok.Getter;

@Getter
public class UploadFile {
    private String uploadName;
    private String storeName;

    public UploadFile(final String uploadName, final String storeName) {
        this.uploadName = uploadName;
        this.storeName = storeName;
    }

}
