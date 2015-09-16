package com.algawork.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.repository.filter.ClienteFilter;
import com.algaworks.pedidovenda.service.NegocioException;
import com.algaworks.pedidovenda.util.jpa.Transactional;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

public class Clientes implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public Cliente guardar(Cliente cliente) {
		//return cliente = manager.merge(cliente);
		cliente = manager.merge(cliente);
		manager.flush();		
		return cliente; 
	}

	@Transactional
	public void remover(Cliente cliente) {
		try {
			cliente = porId(cliente.getId());
			manager.remove(cliente);
			manager.flush();
			
		} catch (PersistenceException e) {
                throw new NegocioException("Cliente não pode ser excluido.");
		}
	}

	public Cliente porEmail(String email) {

		try {
			return manager
					.createQuery("from Cliente where email = :email", Cliente.class)
					.setParameter("email", email.toUpperCase()).getSingleResult();

		} catch (NoResultException e) {
			return null;

		}
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> filtrados(ClienteFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Cliente.class);

		
		if (StringUtils.isNotBlank(filtro.getDoc_receita_federal())) {
			criteria.add(Restrictions.eq("documentoReceitaFederal", FacesUtil.onlyNumbers(filtro.getDoc_receita_federal())));
			                             
		}
		

		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(),
					MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("nome")).list();
	}

	public Cliente porId(Long id) {
		return manager.find(Cliente.class, id);

	}
	
	public List<Cliente> porNome(String nome){
		return this.manager.createQuery("from Cliente "+
	            "where Upper(nome) like :nome", Cliente.class) 
	            .setParameter("nome", nome.toUpperCase() + "%")
	            .getResultList();
	}
	
	
	
}





















