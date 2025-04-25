package ch.glauser.gestionstock.contact.repository;

import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.common.repository.RepositoryUtils;
import ch.glauser.gestionstock.contact.entity.ContactEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * JPA Repository pour la gestion des contacts
 */
@Repository
public interface ContactJpaRepository extends JpaRepository<ContactEntity, Long>, JpaSpecificationExecutor<ContactEntity> {
    /**
     * Récupère un contact par id
     *
     * @param id Id du contact
     * @return Un {@link Optional} de {@link ContactEntity}
     */
    Optional<ContactEntity> findOptionalById(Long id);

    /**
     * Vérifie s'il existe un contact avec cette localité
     *
     * @param id Id de la localité
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    @Query("SELECT COUNT(contact) > 0 FROM Contact contact WHERE contact.adresse.localite.id = :id")
    boolean existsByIdLocalite(@Param("id") Long id);

    default Page<ContactEntity> search(Collection<FilterCombinator> filters, Pageable pageable) {
        return findAll(RepositoryUtils.specificationOf(filters), pageable);
    }
}
