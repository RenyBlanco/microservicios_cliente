package com.rbsoluciones.tienda.cliente.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rbsoluciones.tienda.cliente.entity.Cliente;
import com.rbsoluciones.tienda.cliente.entity.Region;

public interface IClienteRepositorio extends JpaRepository<Cliente, Long>{

	Cliente findByNumeroID(String numeroID);
	List<Cliente> findByApellido(String apellido);
	List<Cliente> findByRegion(Region region);
}
