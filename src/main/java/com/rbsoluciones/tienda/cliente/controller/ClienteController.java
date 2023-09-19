package com.rbsoluciones.tienda.cliente.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbsoluciones.tienda.cliente.entity.Cliente;
import com.rbsoluciones.tienda.cliente.entity.Region;
import com.rbsoluciones.tienda.cliente.servicio.ClienteServicioImp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ClienteController {
	
	@Autowired
	private ClienteServicioImp cs;

	@GetMapping("/clientes")
	public ResponseEntity<List<Cliente>> listClientes(@RequestParam(name="regionId", required=false) Long regionId) {
		List<Cliente> clientes = new ArrayList<>();
		if(regionId==null) {
			clientes = cs.listaClientes();
			if(clientes.isEmpty()) {
				ResponseEntity.notFound().build();
			}
		}else {
			Region r = new Region();
			r.setId(regionId);
			clientes = cs.listaClientesRegion(r);
			if(clientes==null) {
				log.error("Clientes con region Id {} no encontrados", regionId);
				ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.ok(clientes);
	}
	
	@GetMapping("/cliente/{id}")
	public ResponseEntity<Cliente> listCliente(@PathVariable(name="id") long id) {
		log.info("Recibiendo cliente con id {} ", id);
		Cliente cliente = cs.listaUnCliente(id);
		if(cliente==null) {
			log.error("Cliente con Id {} no encontrados", id);
			ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(cliente);
	}
	
	@PostMapping("/cliente/crear")
	public ResponseEntity<Cliente> creaCliente(@Valid @RequestBody Cliente c, BindingResult result){
		log.info("Creando cliente {}",c);
		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
		}
		
		Cliente cSaved = cs.creaCliente(c);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cSaved);
	}
	
	@PutMapping("/cliente/{id}")
	public ResponseEntity<Cliente> actualizaCliente(@PathVariable("id") long id, @RequestBody Cliente c) {
		log.info("Actualizando Cliente con id {}",id);
		Cliente encontrado = cs.listaUnCliente(id);
		if(encontrado==null) {
			log.error("Cliente con id {} no encontrado",id);
			return ResponseEntity.notFound().build();
		}
		c.setId(id);
		encontrado = cs.actualizaCliente(c);
		return ResponseEntity.ok(encontrado);
	}
	
	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<Cliente> eliminaCliente(@PathVariable("id") long id) {
		log.info("Eliminando cliente con id {}",id);
		Cliente c = cs.listaUnCliente(id);
		if(c == null) {
			log.error("Cliente con id {} no encontrado",id);
			return ResponseEntity.notFound().build();
		}
		c = cs.eliminaCliente(c);
		return ResponseEntity.ok(c);
	}

	private String formatMessage(BindingResult result) {
		List<Map<String,String>> errores = result.getFieldErrors().stream()
				.map(err -> {
						Map<String,String> error = new HashMap<>();
						error.put(err.getField(), err.getDefaultMessage());
						return error;
				}).collect(Collectors.toList());
		ErrorMessage errorMessage = ErrorMessage.builder()
				.code("01")
				.messages(errores).build();
		ObjectMapper mapper = new ObjectMapper();
		String jsonString="";
		try {
			jsonString = mapper.writeValueAsString(errorMessage);
		}catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
	
}
