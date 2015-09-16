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

import com.algaworks.pedidovenda.model.Endereco;
import com.algaworks.pedidovenda.repository.filter.EnderecoFilter;
import com.algaworks.pedidovenda.service.NegocioException;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class Enderecos implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public Endereco guardar(Endereco endereco) {
		return endereco = manager.merge(endereco);
	}

	@Transactional
	public void remover(Endereco endereco) {
		try {
			endereco = porId(endereco.getId());
			manager.remove(endereco);
			manager.flush();
			
		} catch (PersistenceException e) {
                throw new NegocioException("Endereco não pode ser excluido.");
		}
	}

	public Endereco porLogradouro(String email) {

		try {
			return manager
					.createQuery("from Endereco where logradouro = :logradouro", Endereco.class)
					.setParameter("logradouro", email.toUpperCase()).getSingleResult();

		} catch (NoResultException e) {
			return null;

		}
	}

	@SuppressWarnings("unchecked")
	public List<Endereco> filtrados(EnderecoFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Endereco.class);

		
		if (StringUtils.isNotBlank(filtro.getLogradouro())) {
			criteria.add(Restrictions.eq("logradouro", filtro.getLogradouro()));
		}
		

		if (StringUtils.isNotBlank(filtro.getNumero())) {
			criteria.add(Restrictions.ilike("logradouro", filtro.getNumero(),
					MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("logradouro")).list();
	}

	public Endereco porId(Long id) {
		return manager.find(Endereco.class, id);

	}
}


