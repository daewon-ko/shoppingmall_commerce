package shoppingmall.domainrdb.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shoppingmall.domainrdb.domain.image.entity.FileType;
import shoppingmall.domainrdb.domain.image.entity.Image;
import shoppingmall.domainrdb.product.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select i from Image i where i.fileType = :fileType and i.targetId =:productId")
    List<Image> findImagesByProductId(@Param("fileType") FileType fileType, @Param("productId") Long productId);
}
