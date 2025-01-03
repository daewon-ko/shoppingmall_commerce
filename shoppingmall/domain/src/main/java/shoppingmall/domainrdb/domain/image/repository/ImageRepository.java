package shoppingmall.domainrdb.domain.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shoppingmall.domainrdb.domain.image.entity.FileType;
import shoppingmall.domainrdb.domain.image.entity.Image;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("select i from Image i where i.fileType in :fileTypes and i.targetId =:targetId")
    List<Image> findImagesByTargetIdAndFileType(@Param("fileTypes") List<FileType> fileType, @Param("targetId") Long targetId);

    @Query("select i from Image i where i.fileType in :fileTypes and i.targetId =:targetId and i.isDeleted = false ")
    Optional<List<Image>> findImagesBySearchCond(@Param("targetId") Long targetId, @Param("fileTypes") List<FileType> fileTypes);
}