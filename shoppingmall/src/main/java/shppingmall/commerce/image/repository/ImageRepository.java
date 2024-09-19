package shppingmall.commerce.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shppingmall.commerce.image.entity.FileType;
import shppingmall.commerce.image.entity.Image;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("select i from Image i where i.fileType in :fileTypes and i.targetId =:targetId")
    List<Image> findImagesByTargetIdAndFileType(@Param("fileTypes") List<FileType> fileType, @Param("targetId") Long targetId);

    List<Image> findByTargetIdAndIsDeletedFalse(@Param("targetId")Long targetId);
}