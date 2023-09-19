package com.rbsoluciones.tienda.cliente.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name="clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="El número de identidad no puede ser vacio")
	@Size(min=8, max=9, message="El tamaño del documento debe ser minimo 8, máximno 9")
	@Column(name="identificacion", unique = true, length= 9, nullable = false)
	private String numeroID;
	
	@NotEmpty(message="El nombre no puede ser vacio")
	@Column(nullable=false)
	private String nombre;
	
	@NotEmpty(message="El apellido no puede ser vacio")
	@Column(nullable=false)
	private String apellido;
	
	@NotEmpty(message="El correo no puede ser vacio")
	@Email(message="no es una dirección de correo valida")
	@Column(unique=true, nullable=false)
	private String correo;
	
	@Column(name="foto_url")
	private String fotoUrl;
	
	@NotNull(message="La región no puede ser vacia")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_region")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Region region;
	
	private String estado;

}
