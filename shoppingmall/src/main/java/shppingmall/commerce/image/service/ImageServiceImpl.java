package shppingmall.commerce.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shppingmall.commerce.common.FileStore;
import shppingmall.commerce.image.entity.FileType;
import shppingmall.commerce.image.repository.ImageRepository;
import shppingmall.commerce.image.entity.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final FileStore fileStore;

    // TODO : 예외정의 후 처리 예정(ControllerAdvice 등) - 재학습 필요
    @Override
    public List<Image> saveImage(List<MultipartFile> multipartFiles, Long targetId, FileType fileType) {


        List<String> fileNames;
        try {
            fileNames = fileStore.uploadFiles(multipartFiles);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        List<Image> images = new ArrayList<>();

        for (String uploadName : fileNames) {
            String fullPathUrl = fileStore.getFullPath(uploadName);
            Image image = Image.builder()
                    .uploadName(uploadName)
                    .fileType(fileType)
                    .targetId(targetId)
                    .fullPathUrl(fullPathUrl)
                    .build();

            imageRepository.save(image);
            images.add(image);
        }

        return images;


    }

    @Override
    public Image getImage(String imageId) {
        return null;
    }
}
