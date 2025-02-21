package ch.glauser.gestionstock.beans;

import ch.glauser.gestionstock.categorie.repository.CategorieRepository;
import ch.glauser.gestionstock.categorie.service.CategorieService;
import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.contact.repository.ContactRepository;
import ch.glauser.gestionstock.contact.service.ContactService;
import ch.glauser.gestionstock.contact.service.ContactServiceImpl;
import ch.glauser.gestionstock.fournisseur.repository.FournisseurRepository;
import ch.glauser.gestionstock.fournisseur.service.FournisseurService;
import ch.glauser.gestionstock.fournisseur.service.FournisseurServiceImpl;
import ch.glauser.gestionstock.localite.repository.LocaliteRepository;
import ch.glauser.gestionstock.localite.service.LocaliteService;
import ch.glauser.gestionstock.localite.service.LocaliteServiceImpl;
import ch.glauser.gestionstock.machine.repository.MachineRepository;
import ch.glauser.gestionstock.machine.service.MachineService;
import ch.glauser.gestionstock.machine.service.MachineServiceImpl;
import ch.glauser.gestionstock.pays.repository.PaysRepository;
import ch.glauser.gestionstock.pays.service.PaysService;
import ch.glauser.gestionstock.pays.service.PaysServiceImpl;
import ch.glauser.gestionstock.piece.repository.PieceRepository;
import ch.glauser.gestionstock.piece.service.PieceService;
import ch.glauser.gestionstock.piece.service.PieceServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeansGenerator {

    @Bean
    public CategorieService categorieService(CategorieRepository categorieRepository) {
        return new CategorieServiceImpl(categorieRepository);
    }

    @Bean
    public ContactService contactService(ContactRepository contactRepository) {
        return new ContactServiceImpl(contactRepository);
    }

    @Bean
    public FournisseurService fournisseurService(FournisseurRepository fournisseurRepository) {
        return new FournisseurServiceImpl(fournisseurRepository);
    }

    @Bean
    public LocaliteService localiteService(LocaliteRepository localiteRepository) {
        return new LocaliteServiceImpl(localiteRepository);
    }

    @Bean
    public MachineService machineService(MachineRepository machineRepository) {
        return new MachineServiceImpl(machineRepository);
    }

    @Bean
    public PaysService paysService(PaysRepository paysRepository) {
        return new PaysServiceImpl(paysRepository);
    }

    @Bean
    public PieceService pieceService(PieceRepository pieceRepository) {
        return new PieceServiceImpl(pieceRepository);
    }
}
