package de.serdioa.hibernate.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;


@Data
@Entity(name = "Right")
@Table(name = "t_Right")
public class Right implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "Right_Id")
    @Id
    private Integer id;

    @Column(name = "Right_Name")
    private String rightName;

    @Column(name = "Description")
    private String description;
}
