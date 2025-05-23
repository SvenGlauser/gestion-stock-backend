package ch.glauser.gestionstock.pays.repository;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.pays.entity.PaysEntity;
import ch.glauser.gestionstock.pays.model.Pays;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implémentation du repository de gestion des pays
 */
@Repository
public class PaysRepositoryImpl implements PaysRepository {

    private final PaysJpaRepository paysJpaRepository;

    public PaysRepositoryImpl(PaysJpaRepository paysJpaRepository) {
        this.paysJpaRepository = paysJpaRepository;
    }

    @Override
    public Optional<Pays> get(Long id) {
        return this.paysJpaRepository.findById(id).map(ModelEntity::toDomain);
    }

    @Override
    public Pays getByAbreviation(String abreviation) {
        return this.paysJpaRepository.findByAbreviation(abreviation).map(ModelEntity::toDomain).orElse(null);
    }

    @Override
    public SearchResult<Pays> search(SearchRequest searchRequest) {
        Page<PaysEntity> page = this.paysJpaRepository.search(PageUtils.getFiltersCombinators(searchRequest), PageUtils.paginate(searchRequest));
        return PageUtils.transform(page);
    }

    @Override
    public Pays create(Pays pays) {
        return this.paysJpaRepository.save(new PaysEntity(pays)).toDomain();
    }

    @Override
    public Pays modify(Pays pays) {
        return this.paysJpaRepository.save(new PaysEntity(pays)).toDomain();
    }

    @Override
    public void delete(Long id) {
        this.paysJpaRepository.deleteById(id);
    }

    @Override
    public boolean existByNom(String nom) {
        return this.paysJpaRepository.existsByNom(nom);
    }

    @Override
    public boolean existByAbreviation(String abreviation) {
        return this.paysJpaRepository.existsByAbreviation(abreviation);
    }
}
