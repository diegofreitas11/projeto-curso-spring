package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.DepartamentoDAO;
import com.example.demo.domain.Departamento;

@Service @Transactional(readOnly = false)
public class DepartamentoService implements IService<Departamento>{

	@Autowired
	private DepartamentoDAO dao;
	
	@Override
	public void salvar(Departamento obj) {
		dao.salvar(obj);
		
	}

	@Override
	public void editar(Departamento obj) {
		dao.alterar(obj);
		
	}

	@Override
	public void excluir(Long id) {
		dao.remover(id);
		
	}

	@Override @Transactional(readOnly = true)
	public Departamento buscarporID(Long id) {
		return dao.consultarporID(id);
	}

	@Override @Transactional(readOnly = true)
	public List<Departamento> buscarTodos() {
		return dao.listarTodos();
	}

	@Override
	public boolean temDependencia(Long id) {
		if(buscarporID(id).getCargos().isEmpty())
			return false;
		return true;
	}

}
