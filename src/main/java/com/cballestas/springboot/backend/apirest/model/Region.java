package com.cballestas.springboot.backend.apirest.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "regiones")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Region implements Serializable {

    static final long serialVersionUID = -1456120226189543425L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "region_sequence")
    @SequenceGenerator(name = "region_sequence", sequenceName = "region_sequence", allocationSize = 1)
    Long id;

    String nombre;

}
