package ru.yandex.praktikum.shop.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.yandex.praktikum.shop.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query(value = """
                SELECT p
                FROM Product p
                WHERE (title ILIKE CONCAT('%', :search, '%') OR description ILIKE CONCAT('%', :search, '%'))
                    AND NOT deleted
            """)
    Page<Product> searchProducts(@Param("search") String search, Pageable pageable);

    Page<Product> findByDeletedFalse(Pageable pageable);
}