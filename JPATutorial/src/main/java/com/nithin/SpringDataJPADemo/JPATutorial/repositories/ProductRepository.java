package com.nithin.SpringDataJPADemo.JPATutorial.repositories;

import com.nithin.SpringDataJPADemo.JPATutorial.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    List<ProductEntity> findByTitle(String title);

    List<ProductEntity> findByCreatedAtAfter(LocalDateTime createdAt);

//    Optional<ProductEntity> findByTitleAndPrice(String maggi, BigDecimal bigDecimal);

    List<ProductEntity> findByQuantityGreaterThanAndPriceLessThan(int i, BigDecimal bigDecimal);

    List<ProductEntity> findFirst2ByTitleLike(String s);

    List<ProductEntity> findFirstByTitleLike(String s);


    List<ProductEntity> findByTitleContaining(String gam);

    List<ProductEntity> findByTitleContainingIgnoreCase(String gam);

    @Query("select e from ProductEntity e where e.title=?1 and e.price=?2")
    Optional<ProductEntity> findByTitleAndPrice(String str, BigDecimal price);

    List<ProductEntity> findByTitleNotLike(String s);

    List<ProductEntity> findByTitleStartingWith(String c);

    List<ProductEntity> findByTitleEndingWithIgnoreCase(String la);

    List<ProductEntity> findByPriceLessThanOrderByPriceDesc(BigDecimal bigDecimal);

    List<ProductEntity> findByTitleNot(String pepsi);

    List<ProductEntity> findByTitleNotIn(List<String> titles);

    @Query("select e.price from ProductEntity e where e.title = ?1")
    String getMeAllWhoseTitleIsPepsi(String pepsi);

    List<ProductEntity> findBy(Sort sort);

    Page<ProductEntity> findBy(Pageable pageable);
    List<ProductEntity> findByOrderByPriceDesc();
}
