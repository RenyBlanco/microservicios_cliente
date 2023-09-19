package com.rbsoluciones.tienda.cliente.servicio;

import java.util.List;

import com.rbsoluciones.tienda.cliente.entity.Cliente;
import com.rbsoluciones.tienda.cliente.entity.Region;

public interface IClienteServicio {
	
	public List<Cliente> listaClientes();
	public List<Cliente> listaClientesRegion(Region region);
	public Cliente creaCliente(Cliente c);
	public Cliente actualizaCliente(Cliente c);
	public Cliente eliminaCliente(Cliente c);
	public Cliente listaUnCliente(Long id);
}
