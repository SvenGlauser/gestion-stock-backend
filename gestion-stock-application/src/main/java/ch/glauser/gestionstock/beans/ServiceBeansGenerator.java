package ch.glauser.gestionstock.beans;

import ch.glauser.gestionstock.categorie.repository.CategorieRepository;
import ch.glauser.gestionstock.categorie.service.CategorieService;
import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.contact.repository.ContactRepository;
import ch.glauser.gestionstock.contact.service.ContactService;
import ch.glauser.gestionstock.contact.service.ContactServiceImpl;
import ch.glauser.gestionstock.exception.repository.ThrownExceptionRepository;
import ch.glauser.gestionstock.exception.service.ThrownExceptionService;
import ch.glauser.gestionstock.exception.service.ThrownExceptionServiceImpl;
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
import ch.glauser.gestionstock.piece.repository.PieceHistoriqueRepository;
import ch.glauser.gestionstock.piece.repository.PieceRepository;
import ch.glauser.gestionstock.piece.service.PieceHistoriqueService;
import ch.glauser.gestionstock.piece.service.PieceHistoriqueServiceImpl;
import ch.glauser.gestionstock.piece.service.PieceService;
import ch.glauser.gestionstock.piece.service.PieceServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeansGenerator {

    @Bean
    public CategorieService categorieService(CategorieRepository categorieRepository,
                                             PieceRepository pieceRepository) {
        return new CategorieServiceImpl(categorieRepository, pieceRepository);
    }

    @Bean
    public ContactService contactService(ContactRepository contactRepository,
                                         MachineRepository machineRepository) {
        return new ContactServiceImpl(contactRepository, machineRepository);
    }

    @Bean
    public FournisseurService fournisseurService(FournisseurRepository fournisseurRepository,
                                                 PieceRepository pieceRepository) {
        return new FournisseurServiceImpl(fournisseurRepository, pieceRepository);
    }

    @Bean
    public LocaliteService localiteService(LocaliteRepository localiteRepository,
                                           ContactRepository contactRepository,
                                           FournisseurRepository fournisseurRepository) {
        return new LocaliteServiceImpl(localiteRepository, contactRepository, fournisseurRepository);
    }

    @Bean
    public MachineService machineService(MachineRepository machineRepository) {
        return new MachineServiceImpl(machineRepository);
    }

    @Bean
    public PaysService paysService(PaysRepository paysRepository,
                                   LocaliteRepository localiteRepository) {
        return new PaysServiceImpl(paysRepository, localiteRepository);
    }

    @Bean
    public PieceService pieceService(PieceRepository pieceRepository,
                                     PieceHistoriqueService pieceHistoriqueService,
                                     CategorieService categorieService,
                                     MachineRepository machineRepository) {
        return new PieceServiceImpl(pieceRepository, pieceHistoriqueService, categorieService, machineRepository);
    }

    @Bean
    public PieceHistoriqueService pieceHistoriqueService(PieceHistoriqueRepository pieceHistoriqueRepository) {
        return new PieceHistoriqueServiceImpl(pieceHistoriqueRepository);
    }

    @Bean
    public ThrownExceptionService thrownExceptionService(ThrownExceptionRepository thrownExceptionRepository) {
        return new ThrownExceptionServiceImpl(thrownExceptionRepository);
    }
}
