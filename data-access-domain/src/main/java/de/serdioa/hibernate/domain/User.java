package de.serdioa.hibernate.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Data
@Entity(name = "User")
@Table(name = "t_User")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "User_Id")
    @Id
    private Integer id;

    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "Password_Changed_On", columnDefinition = "TIMESTAMP")
    private ZonedDateTime passwordChangedOn;

    @Column(name = "Expire_On", columnDefinition = "DATE")
    private LocalDate expireOn;

    @Column(name = "Locked")
    private Boolean locked;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "t_User_Role", joinColumns =
            @JoinColumn(name = "User_Id"))
    @Column(name = "Role_id")
    private Set<Integer> roleIds;
}
