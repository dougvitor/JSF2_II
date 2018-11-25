package br.com.caelum.livraria.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.math.NumberUtils;

public class DAO<T> {

	private final Class<T> classe;

	public DAO(Class<T> classe) {
		this.classe = classe;
	}

	public void adiciona(T t) {

		// consegue a entity manager
		EntityManager em = new JPAUtil().getEntityManager();

		// abre transacao
		em.getTransaction().begin();

		// persiste o objeto
		em.persist(t);

		// commita a transacao
		em.getTransaction().commit();

		// fecha a entity manager
		em.close();
	}

	public void remove(T t) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();

		em.remove(em.merge(t));

		em.getTransaction().commit();
		em.close();
	}

	public void atualiza(T t) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();

		em.merge(t);

		em.getTransaction().commit();
		em.close();
	}

	public List<T> listaTodos() {
		EntityManager em = new JPAUtil().getEntityManager();
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).getResultList();

		em.close();
		return lista;
	}

	public T buscaPorId(Integer id) {
		EntityManager em = new JPAUtil().getEntityManager();
		T instancia = em.find(classe, id);
		em.close();
		return instancia;
	}

	public int contaTodos() {
		EntityManager em = new JPAUtil().getEntityManager();
		long result = (Long) em.createQuery("select count(n) from livro n")
				.getSingleResult();
		em.close();

		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults, Map<String, String> filters) {
	    EntityManager em = new JPAUtil().getEntityManager();
	    CriteriaBuilder builder = em.getCriteriaBuilder();
	    CriteriaQuery<T> query = builder.createQuery(classe);
	    Root<T> root = query.from(classe);
	    List<Predicate> predicates = null;

	    if(filters != null) {
	    	Set<String> keys = filters.keySet();
	    	
	    	predicates = new ArrayList<Predicate>();
	    	
	    	for(String key : keys) {
	    		
	    		String value = filters.get(key);
	    		
	    		if(NumberUtils.isDigits(value)) {
	    			predicates.add(builder.and(em.getCriteriaBuilder().le(root.<Number>get(key), NumberUtils.toDouble(value))));
	    		}else {
	    			predicates.add(builder.and(em.getCriteriaBuilder().like(root.<String>get(key), value + "%")));
	    		}
	    	}
	    	
	    	query = query.where(predicates.toArray(new Predicate[] {}));
	    }
	    
	    List<T> lista = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();

	    em.close();
	    return lista;
	}
	
	public int quantidadeDeElementos() {
        EntityManager em = new JPAUtil().getEntityManager();
        long result = (Long) em.createQuery("select count(n) from " + classe.getSimpleName() + " n")
                .getSingleResult();
        em.close();

        return (int) result;
    }
	
	public int quantidadeDeElementos(Map<String, String> filters) {
		EntityManager em = new JPAUtil().getEntityManager();
	    CriteriaBuilder builder = em.getCriteriaBuilder();
	    CriteriaQuery<Long> query = builder.createQuery(Long.class);
	    Root<T> root = query.from(classe);
	    List<Predicate> predicates = null;
	    
	    query.select(builder.count(root));
	    
	    if(filters != null) {
	    	Set<String> keys = filters.keySet();
	    	
	    	predicates = new ArrayList<Predicate>();
	    	
	    	for(String key : keys) {
	    		
	    		String value = filters.get(key);
	    		
	    		if(NumberUtils.isDigits(value)) {
	    			predicates.add(builder.and(em.getCriteriaBuilder().le(root.<Number>get(key), NumberUtils.toDouble(value))));
	    		}else {
	    			predicates.add(builder.and(em.getCriteriaBuilder().like(root.<String>get(key), value + "%")));
	    		}
	    	}
	    	
	    	query = query.where(predicates.toArray(new Predicate[] {}));
	    }
	    
	    long result = em.createQuery(query).getSingleResult();

	    em.close();
	    
	    return (int) result;
	}

}
