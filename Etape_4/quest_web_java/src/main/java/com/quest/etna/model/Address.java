package com.quest.etna.model;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Data;

@Entity
@Data
public class Address {

    @Id // Définit l'attribut comme étant la clé primaire de l'entité
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Génère automatiquement les valeurs de clé primaire
    private Integer id;

    @Column(nullable = false, length = 100) // Définit une colonne dans la table avec les propriétés spécifiées
    private String street;

    @Column(name = "postal_code", nullable = false, length = 30)
    private String postalCode;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 50)
    private String country;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id", nullable = false)  // Définit la colonne de jointure avec l'entité User
    private User user;

    @Column(name = "creation_date")
    @Temporal( TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @PrePersist
    protected void onCreate() {
        creationDate = Date.from(new Date().toInstant());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = Date.from(new Date().toInstant());
    }

}
