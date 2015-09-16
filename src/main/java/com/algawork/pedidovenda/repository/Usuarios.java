package com.algawork.pedidovenda.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.model.UsuarioValor;
import com.algaworks.pedidovenda.repository.filter.UsuarioFilter;
import com.algaworks.pedidovenda.service.NegocioException;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class Usuarios implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public Usuario guardar(Usuario usuario) {
		return usuario = manager.merge(usuario);
	}

	@Transactional
	public void remover(Usuario usuario) {
		try {
			usuario = porId(usuario.getId());
			manager.remove(usuario);
			manager.flush();
			
		} catch (PersistenceException e) {
                throw new NegocioException("Usuario não pode ser excluido.");
		}
	}

	public Usuario porEmail(String email) {

		Usuario usuario = null;
		try {
			usuario =  this.manager
					.createQuery("from Usuario where email = :email", Usuario.class)
					.setParameter("email", email.toUpperCase()).getSingleResult();

		} catch (NoResultException e) {
			return null;

		}
		
		return usuario;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> filtrados(UsuarioFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);

		
		if (StringUtils.isNotBlank(filtro.getEmail())) {
			criteria.add(Restrictions.eq("email", filtro.getEmail()));
		}
		

		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(),
					MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("nome")).list();
	}

	public Usuario porId(Long id) {
		return manager.find(Usuario.class, id);

	}
	
	public List<Usuario> vendedores(){
		//TODO implementar apenas vendendores por um grupo especifico
		return this.manager.createQuery("from Usuario", Usuario.class)
				.getResultList();
		}
	
	
	public Map<String, BigDecimal> valoresTotaisPorUsuario(){
		
		Session session = manager.unwrap(Session.class);
		
		Map<String, BigDecimal> resultado = new TreeMap();
		
		Query sql =  session.createSQLQuery("select us.nome as nomeUsuario, sum(p.valor_total) as valorPedidosUsuario from usuario us join pedido p on us.id = p.vendedor_id	group by us.id");
			
		@SuppressWarnings("unchecked")
		List<UsuarioValor> valoresPorUsuario = sql.setResultTransformer(Transformers.aliasToBean(UsuarioValor.class)).list();
		
		for (UsuarioValor usuarioValor : valoresPorUsuario) {
			resultado.put(usuarioValor.getNomeUsuario(), usuarioValor.getValorPedidosUsuario());
			
		}
		
		return resultado;
	}
	
}




















