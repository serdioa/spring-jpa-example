package de.serdioa.hibernate.domain;

import java.io.Serializable;
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
@Entity(name = "Role")
@Table(name = "t_Role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "Role_Id")
    @Id
    private Integer id;

    @Column(name = "Role_Name")
    private String roleName;

    @Column(name = "Description")
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "t_Role_Right", joinColumns =
            @JoinColumn(name = "Role_Id"))
    @Column(name = "Right_Id")
    private Set<Integer> rightIds;
}
