package com.example.demo.service;

import java.util.List;


public interface IService <T>{
	void salvar(T obj);
	void editar(T obj);
	void excluir(Long id);
	T buscarporID(Long id);
	List<T> buscarTodos();
	boolean temDependencia(Long id);

}
