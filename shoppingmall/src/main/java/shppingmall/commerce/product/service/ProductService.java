package shppingmall.commerce.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shppingmall.commerce.category.entity.Category;
import shppingmall.commerce.category.repository.CategoryRepository;
import shppingmall.commerce.common.FileStore;
import shppingmall.commerce.product.UploadFile;
import shppingmall.commerce.product.dto.request.ProductRequestDto;
import shppingmall.commerce.product.dto.response.ProductResponseDto;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    // TODO : Service Layer에서 타 도메인 Repository를 참고하는게 적절한가?
    private final CategoryRepository categoryRepository;
    private final FileStore fileStore;

    @Transactional
    public void createProduct(final ProductRequestDto requestDto, List<MultipartFile> images) {
        // 1. requestDTO의 imageURL을 변환 및 저장과정

        // TODO : 예외정의 후 처리 예정(ControllerAdvice 등) - 재학습 필요
        try {
            List<UploadFile> uploadFiles = fileStore.uploadFiles(images);
            for (UploadFile uploadFile : uploadFiles) {
                String uploadName = uploadFile.getUploadName();
                // Product Table에 저장될 image 저장경로 조회
                String fullPathUrl = fileStore.getFullPath(uploadName);
                // requestDto가 생성한 Category 객체 조회
                // TODO : 예외정의(?) 필요
                Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("해당하는 카테고리가 없습니다."));

                // dto -> product entity 변환 필요
                Product product = requestDto.toEntity(fullPathUrl, category);


                productRepository.save(product);


            }


        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    @Transactional(readOnly = true)

    public List<ProductResponseDto> getAllProductList() {
        List<ProductResponseDto> list = new ArrayList<>();
        List<Product> productList = productRepository.findAll();

        for (Product product : productList) {
            list.add(product.toDto());
        }
        return list;
    }
}
