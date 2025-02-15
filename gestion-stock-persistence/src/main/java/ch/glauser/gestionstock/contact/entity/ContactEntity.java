package ch.glauser.gestionstock.contact.entity;

import ch.glauser.gestionstock.adresse.entity.AdresseEntity;
import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.contact.model.Contact;
import ch.glauser.gestionstock.contact.model.Titre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "CONTACT")
public class ContactEntity extends ModelEntity<Contact> {
    @Enumerated(EnumType.STRING)
    @Column(name = "TITRE", nullable = false)
    private Titre titre;
    @Column(name = "NOM", nullable = false)
    private String nom;
    @Column(name = "PRENOM", nullable = false)
    private String prenom;

    @Column(name = "EMAIL")
    private String email;
    @Column(name = "TELEPHONE")
    private String telephone;

    @Embedded
    private AdresseEntity adresse;

    @Column(name = "REMARQUES")
    private String remarques;

    public ContactEntity(Contact contact) {
        super(contact);
        this.titre = contact.getTitre();
        this.nom = contact.getNom();
        this.prenom = contact.getPrenom();
        this.email = contact.getEmail();
        this.telephone = contact.getTelephone();
        this.adresse = Optional.ofNullable(contact.getAdresse()).map(AdresseEntity::new).orElse(null);
        this.remarques = contact.getRemarques();
    }

    @Override
    protected Contact toDomainChild() {
        Contact contact = new Contact();
        contact.setTitre(this.titre);
        contact.setNom(this.nom);
        contact.setPrenom(this.prenom);
        contact.setEmail(this.email);
        contact.setTelephone(this.telephone);
        contact.setAdresse(Optional.ofNullable(this.adresse).map(AdresseEntity::toDomain).orElse(null));
        contact.setRemarques(this.remarques);
        return contact;
    }
}
