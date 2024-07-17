package ca.sfu.cmpt276.grow.with.you.models;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlantRepository extends JpaRepository<Plant, Integer> {
        List<Plant> findByName(String name);

        List<Plant> findByGrower(Grower grower, Sort sort);

        List<Plant> findBySponsor(Sponsor sponsor, Sort sort);

        @Query("SELECT p FROM Plant p " +
                        "WHERE (:name IS NULL OR p.name LIKE %:name%) " +
                        "AND (:province IS NULL OR p.province = :province) " +
                        "AND (:city IS NULL OR p.city = :city)" +
                        "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
                        "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
        Page<Plant> findAllByFilters(
                        @Param("name") String name,
                        @Param("province") String province,
                        @Param("city") String city,
                        @Param("minPrice") Optional<Integer> minPrice,
                        @Param("maxPrice") Optional<Integer> maxPrice,
                        Pageable pageable);

}
