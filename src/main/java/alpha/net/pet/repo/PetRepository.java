package alpha.net.pet.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import alpha.net.pet.Pet;

import java.util.Optional;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query(value = "SELECT p.* FROM pet p " +
    "JOIN app_user u ON u.id = p.user_id " +
    "WHERE (:species IS NULL OR LOWER(p.species) LIKE LOWER(CONCAT('%', :species, '%'))) AND " +
    "(:subtype IS NULL OR LOWER(p.subtype) LIKE LOWER(CONCAT('%', :subtype, '%'))) AND " +
    "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
    "(:age IS NULL OR p.age = :age) AND " +
    "(:weight IS NULL OR p.weight = :weight) AND " +
    "(:gender IS NULL OR p.gender = :gender) AND " +
    "(:ownerName IS NULL OR LOWER(CONCAT(u.first_name, ' ', u.last_name)) LIKE LOWER(CONCAT('%', :ownerName, '%')))" + 
    " ORDER BY p.name ASC", nativeQuery = true)
Optional<List<Pet>> findByAllPetsByFilter(
    @Param("species") String species,
    @Param("subtype") String subtype,
    @Param("name") String name,
    @Param("age") Integer age,
    @Param("weight") Float weight,
    @Param("gender") Boolean gender,
    @Param("ownerName") String ownerName
);
    
}
