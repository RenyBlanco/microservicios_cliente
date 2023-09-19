package com.rbsoluciones.tienda.cliente.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbsoluciones.tienda.cliente.entity.Cliente;
import com.rbsoluciones.tienda.cliente.entity.Region;
import com.rbsoluciones.tienda.cliente.repository.IClienteRepositorio;

@Service
public class ClienteServicioImp implements IClienteServicio {

	@Autowired
	private IClienteRepositorio cr;
	
	@Override
	public List<Cliente> listaClientes() {
		return cr.findAll();
	}

	@Override
	public List<Cliente> listaClientesRegion(Region region) {
		return cr.findByRegion(region);
	}

	@Override
	public Cliente creaCliente(Cliente c) {
		Cliente clienteDB = cr.findByNumeroID(c.getNumeroID());
		if(clienteDB != null) {
			return clienteDB;
		}
		c.setEstado("creado");
		clienteDB=cr.save(c);
		return clienteDB;
	}

	@Override
	public Cliente actualizaCliente(Cliente c) {
		Cliente clienteDB = cr.findByNumeroID(c.getNumeroID());
		if(clienteDB == null) {
			return null;
		}
		c.setEstado("modificado");
		clienteDB=cr.save(c);
		return clienteDB;
	}

	@Override
	public Cliente eliminaCliente(Cliente c) {
		Cliente clienteDB = cr.findByNumeroID(c.getNumeroID());
		if(clienteDB == null) {
			return null;
		}
		c.setEstado("eliminado");
		clienteDB=cr.save(c);
		return clienteDB;
	}

	@Override
	public Cliente listaUnCliente(Long id) {
		Cliente clienteDB = cr.findById(id).orElse(null);
		return clienteDB;
	}

}
