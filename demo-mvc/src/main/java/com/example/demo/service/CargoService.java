package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.CargoDAO;
import com.example.demo.domain.Cargo;

@Service @Transactional(readOnly = false)
public class CargoService implements IService<Cargo>{
	
	@Autowired
	private CargoDAO dao;

	@Override
	public void salvar(Cargo obj) {
		dao.salvar(obj);
		
	}

	@Override
	public void editar(Cargo obj) {
		dao.alterar(obj);
		
	}

	@Override
	public void excluir(Long id) {
		dao.remover(id);
		
	}

	@Override @Transactional(readOnly = true)
	public Cargo buscarporID(Long id) {
		return dao.consultarporID(id);
	}

	@Override @Transactional(readOnly = true)
	public List<Cargo> buscarTodos() {
		return dao.listarTodos();
	}

	@Override
	public boolean temDependencia(Long id) {
		if(buscarporID(id).getFuncionarios().isEmpty())
			return false;
		return true;
	}
	

}
