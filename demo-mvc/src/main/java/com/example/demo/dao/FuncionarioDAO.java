package com.example.demo.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.Funcionario;

@Repository
public class FuncionarioDAO extends AbstractDao<Funcionario, Long> {

	public List<Funcionario> listarPorNome(String nome) {
		return consultar("select f from Funcionario f where f.nome like concat('%',?1,'%')", nome);
	}
	
	public List<Funcionario> listarPorCargo(Long cargo) {
		return consultar("select f from Funcionario f where f.cargo.id = ?1", cargo);
	}

	public List<Funcionario> listarPorData(LocalDate entrada, LocalDate saida) {
		return consultar("select f from Funcionario f where ?1 <= f.dataEntrada and f.dataSaida <= ?2", 
				entrada, saida);
	}
	
	public List<Funcionario> listarEmAtividade(){
		return consultar("select f from Funcionario f where f.dataSaida = null");
	}


	
}
