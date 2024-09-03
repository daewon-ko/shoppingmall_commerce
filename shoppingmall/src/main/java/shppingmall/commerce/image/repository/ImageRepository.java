package shppingmall.commerce.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shppingmall.commerce.image.entity.FileType;
import shppingmall.commerce.image.entity.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("select i from Image i where i.fileType = :fileType and i.targetId =:targetId")
    List<Image> findImagesByTargetIdAndFileType(@Param("fileType") FileType fileType, @Param("targetId") Long targetId);
}
