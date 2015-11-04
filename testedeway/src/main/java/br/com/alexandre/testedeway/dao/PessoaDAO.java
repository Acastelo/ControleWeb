package br.com.alexandre.testedeway.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.alexandre.testedeway.util.JPAUtil;

public class PessoaDAO<Pessoa> {
	
private Class<Pessoa> aClass;
	
	protected PessoaDAO(Class<Pessoa> aClass){
		this.aClass = aClass;
		
	}
	
	protected EntityManager getEntityManager(){
		return JPAUtil.getInstance().getEntityManager();
	}
	
	public void save(Pessoa entity){
		EntityManager manager = getEntityManager();
		manager.getTransaction().begin();
		manager.persist(entity);
		manager.getTransaction().commit();
		manager.close();
	}
	
	public void update(Pessoa entity){
		EntityManager manager = getEntityManager();
		manager.getTransaction().begin();
		manager.merge(entity);
		manager.getTransaction().commit();
		manager.close();
	}
	
	public void delete(Long id){
		EntityManager manager = getEntityManager();
		manager.getTransaction().begin();
		manager.remove(manager.getReference(aClass, id));
		manager.getTransaction().commit();
		manager.close();
	}
	
	public Pessoa findById(Long id){
		EntityManager manager = getEntityManager();
		manager.getTransaction().begin();
		
		Pessoa entity = (Pessoa) manager.find(aClass, id);
		
		manager.getTransaction().commit();
		manager.close();
		
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	public List<Pessoa> findAll(){
		EntityManager manager = getEntityManager();
		manager.getTransaction().begin();
		
		Query query = manager.createQuery("from " + aClass.getSimpleName());
		List<Pessoa> entities = query.getResultList();
		
		manager.getTransaction().commit();
		manager.close();
		
		return entities;
	}
	
	@SuppressWarnings("unchecked")
	public List<Pessoa> find(String jpql, Object... params){
		EntityManager manager = getEntityManager();
		manager.getTransaction().begin();
		
		Query query = manager.createQuery(jpql);
		
		for(int i=0; i < params.length; i++){
			query.setParameter(i+1, params[i]);
		}
		
		List<Pessoa> entities = query.getResultList();
		
		manager.getTransaction().commit();
		manager.close();
		
		return entities;
	}
	
	@SuppressWarnings("unchecked")
	public Pessoa findOne(String jpql, Object... params){
		EntityManager manager = getEntityManager();
		manager.getTransaction().begin();
		
		Query query = manager.createQuery(jpql);
		
		for(int i=0; i < params.length; i++){
			query.setParameter(i+1, params[i]);
		}
		
		Pessoa entity = (Pessoa) query.getSingleResult();
		
		manager.getTransaction().commit();
		manager.close();
		
		return entity;
	}
	
	public long count(){
		EntityManager manager = getEntityManager();
		manager.getTransaction().begin();
		
		Query query = manager.createQuery("Select count(c) from " + aClass.getSimpleName() + "c");
		
		long count = (long) query.getSingleResult();
		
		manager.getTransaction().commit();
		manager.close();
		
		return count;
	}

}
