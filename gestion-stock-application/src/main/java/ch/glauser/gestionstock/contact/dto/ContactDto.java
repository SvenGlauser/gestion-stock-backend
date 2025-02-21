package ch.glauser.gestionstock.contact.dto;

import ch.glauser.gestionstock.adresse.dto.AdresseDto;
import ch.glauser.gestionstock.common.dto.ModelDto;
import ch.glauser.gestionstock.contact.model.Contact;
import ch.glauser.gestionstock.contact.model.Titre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class ContactDto extends ModelDto<Contact> {

    private String titre;
    private String nom;
    private String prenom;

    private String email;
    private String telephone;

    private AdresseDto adresse;

    private String remarques;

    public ContactDto(Contact contact) {
        super(contact);
        this.titre = Optional.ofNullable(contact.getTitre()).map(Titre::name).orElse(null);
        this.nom = contact.getNom();
        this.prenom = contact.getPrenom();
        this.email = contact.getEmail();
        this.telephone = contact.getTelephone();
        this.adresse = Optional.ofNullable(contact.getAdresse()).map(AdresseDto::new).orElse(null);
        this.remarques = contact.getRemarques();
    }

    @Override
    protected Contact toDomainChild() {
        Contact contact = new Contact();
        contact.setTitre(Optional.ofNullable(titre).map(Titre::valueOf).orElse(null));
        contact.setNom(this.nom);
        contact.setPrenom(this.prenom);
        contact.setEmail(this.email);
        contact.setTelephone(this.telephone);
        contact.setAdresse(Optional.ofNullable(this.adresse).map(AdresseDto::toDomain).orElse(null));
        contact.setRemarques(this.remarques);
        return contact;
    }
}
