package com.proy.tecproBK.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Setter
@Getter
@AllArgsConstructor//creas con parametros
@NoArgsConstructor //constructor sin parametros
@Builder //se creara esta clase
@Table(name="roles")
public class rolesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id_rol generado automaticamente
    @Column(name = "id_rol")
    int id_rol;
    @Enumerated(EnumType.STRING)//hacemos referencia a la clase ERoles que datos de tipo string
    ERoles nombre;
    @Column(columnDefinition = "int DEFAULT 1")
    int cod_estado;

}
