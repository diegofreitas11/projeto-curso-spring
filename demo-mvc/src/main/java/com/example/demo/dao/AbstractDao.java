package com.example.demo.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.*;

import com.example.demo.domain.Funcionario;



public class AbstractDao<T, PK extends Serializable> {
	
	@SuppressWarnings("unchecked")
	private final Class<T> entityClass = 
			(Class<T>) ((ParameterizedType) 
					getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	
	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void salvar(T entidade) {
		entityManager.persist(entidade);
	}
	
	public void alterar(T entidade) {
		entityManager.merge(entidade);
	}
	
	public void remover(PK id) {
		entityManager.remove(entityManager.getReference(entityClass, id));
	}
	
	public T consultarporID(PK id) {
		return entityManager.find(entityClass, id);
	}
	
	public List<T> listarTodos(){
		return entityManager.createQuery("from " + entityClass.getSimpleName(), 
				entityClass).getResultList();
	}
	
	
	public List<T> consultar(String jpql, Object... params){
		TypedQuery<T> query = entityManager.createQuery(jpql, entityClass);
		for(int i = 0; i < params.length; i++) {
			query.setParameter(i+1, params[i]);
		}
		
		return query.getResultList();
	}
	

}
