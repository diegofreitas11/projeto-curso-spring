package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.FuncionarioDAO;
import com.example.demo.domain.Funcionario;

@Service @Transactional(readOnly = false)
public class FuncionarioService implements IService<Funcionario>{

	@Autowired
	private FuncionarioDAO dao;
	
	@Override
	public void salvar(Funcionario obj) {
		dao.salvar(obj);
	}

	@Override
	public void editar(Funcionario obj) {
		dao.alterar(obj);
	}

	@Override
	public void excluir(Long id) {
		dao.remover(id);
	}

	@Override
	public Funcionario buscarporID(Long id) {
		return dao.consultarporID(id);
	}

	@Override
	public List<Funcionario> buscarTodos() {
		return dao.listarTodos();
	}
	
	

	@Override
	public boolean temDependencia(Long id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<Funcionario> buscarEmAtividade(){
		return dao.listarEmAtividade();
	}


	public List<Funcionario> buscarPorNome(String nome) {
		return dao.listarPorNome(nome);
	}

	public List<Funcionario> buscarPorCargo(Long cargo) {
		return dao.listarPorCargo(cargo);
	}

	public List<Funcionario> buscarPorData(LocalDate entrada, LocalDate saida) {
		return dao.listarPorData(entrada, saida);
	}
	

}
