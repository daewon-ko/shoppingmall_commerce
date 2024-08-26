package shppingmall.commerce.image.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shppingmall.commerce.image.entity.FileType;
import shppingmall.commerce.image.entity.Image;

import java.util.List;

@Service
public interface ImageService {

    List<Image> saveImage(List<MultipartFile> images, Long targetId, FileType fileType);

    Image getImage(String imageId);

    void deleteImages( Long targetId, FileType fileType);
}
