package com.algawork.pedidovenda.repository;


import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.algaworks.pedidovenda.model.Grupo;

public class Grupos implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	

	public Grupo grupoPorId(Long id)
	{
		return manager.find(Grupo.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Grupo>todos()
	{
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Grupo.class);
		
/*		if(StringUtils.isNotBlank(filtro.getNome()))
		{
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		if (StringUtils.isNotBlank(filtro.getEmail())) {
			criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.ANYWHERE));
		}
*/
		return criteria.addOrder(Order.asc("nome")).list();

	}	
	
	public Grupo grupoPorNome(String nome)
	{
		try
			{
			return manager.createQuery("from Grupo where upper(nome) = :nome", Grupo.class)
					.setParameter("nome", nome.toUpperCase())
					.getSingleResult();
			}
		catch (NoResultException e)
		{
			return null;
		}
	}


}