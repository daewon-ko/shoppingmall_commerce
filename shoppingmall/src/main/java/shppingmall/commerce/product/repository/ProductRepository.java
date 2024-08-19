package shppingmall.commerce.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shppingmall.commerce.image.entity.FileType;
import shppingmall.commerce.image.entity.Image;
import shppingmall.commerce.product.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select i from Image i where i.fileType = :fileType and i.targetId =:productId")
    List<Image> findImageById(@Param("fileType") FileType fileType, @Param("productId") Long productId);
}
