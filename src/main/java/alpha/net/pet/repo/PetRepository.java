package alpha.net.pet.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import alpha.net.pet.Pet;

import java.util.Optional;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {


    /**
     * Finds all pets that match the given filters and sorts them by name in ascending order.
     * <p>
     * This method allows filtering by various attributes of the pet and its owner. 
     * If a filter parameter is null, that filter is ignored.
     * </p>
     *
     * @param species   the species of the pet to filter by; if null, this filter is ignored
     * @param subtype   the subtype of the pet to filter by; if null, this filter is ignored
     * @param name      the name of the pet to filter by; if null, this filter is ignored
     * @param age       the age of the pet to filter by; if null, this filter is ignored
     * @param weight    the weight of the pet to filter by; if null, this filter is ignored
     * @param gender    the gender of the pet to filter by; if null, this filter is ignored
     * @param ownerName the full name of the pet's owner to filter by; if null, this filter is ignored
     * @return an {@code Optional} containing a list of pets that match the filters, 
     *         or an empty {@code Optional} if no pets match
     */
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
