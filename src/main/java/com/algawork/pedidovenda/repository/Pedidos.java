package com.algawork.pedidovenda.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import com.algaworks.pedidovenda.model.DataValor;
import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.filter.PedidoFilter;
import com.algaworks.pedidovenda.service.NegocioException;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class Pedidos implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public Pedido guardar(Pedido pedido) {
		return pedido = manager.merge(pedido);
	}

	@Transactional
	public void remover(Pedido pedido) {
		try {
			pedido = porId(pedido.getId());
			manager.remove(pedido);
			manager.flush();
			
		} catch (PersistenceException e) {
                throw new NegocioException("Pedido não pode ser excluido.");
		}
	}

	public Pedido porEmail(String email) {

		try {
			return manager
					.createQuery("from Pedido where email = :email", Pedido.class)
					.setParameter("email", email.toUpperCase()).getSingleResult();

		} catch (NoResultException e) {
			return null;

		}
	}

	@SuppressWarnings("unchecked")
	public List<Pedido> filtrados(PedidoFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Pedido.class)
				//  alias de associação (join)  com cliente alias = c
				.createAlias("cliente", "c")
				//  alias de associação (join)  com cliente alias = c
		        .createAlias("vendedor", "v");
		
		
		if(filtro.getNumeroDe() != null){
			//id deve ser maior ou igual (ge = greates or equals a filtro.numeroDe
			criteria.add(Restrictions.ge("id", filtro.getNumeroDe()));			
		}
		
		if(filtro.getNumeroAte() != null){
			//id deve ser menor ou igual (le = lower or equals a filtro.numeroAte
			criteria.add(Restrictions.le("id", filtro.getNumeroAte()));			
		}
		
		
		if(filtro.getDataCriacaoDe() != null){
			criteria.add(Restrictions.ge("dataCriaco", filtro.getDataCriacaoDe()));			
		}
		
		if(filtro.getDataCriacaoAte() != null){
			criteria.add(Restrictions.ge("dataCriaco", filtro.getDataCriacaoAte()));			
		}
		
		
		if (StringUtils.isNotBlank(filtro.getNomeCliente())) {
			criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCliente(), MatchMode.ANYWHERE));
			                             
		}
		
		if (StringUtils.isNotBlank(filtro.getNomeVendendor())) {
			criteria.add(Restrictions.ilike("v.nome", filtro.getNomeVendendor(), MatchMode.ANYWHERE));
			                             
		}
		
		if (filtro.getStatuses() != null && filtro.getStatuses().length > 0) {
			criteria.add(Restrictions.in("status", filtro.getStatuses()));
			                             
		}
		


		return criteria.addOrder(Order.asc("id")).list();
	}

	public Pedido porId(Long id) {
		return manager.find(Pedido.class, id);

	}
	
	public Map<Date, BigDecimal> valoresTotaisPorData(Integer numeroDeDias, Usuario criadoPor) {

		Session session = manager.unwrap(Session.class);
		numeroDeDias -= 1;
		
		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias * -1);

		Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDeDias, dataInicial);

		Criteria criteria = session.createCriteria(Pedido.class);
		criteria.setProjection(Projections.projectionList()
		        .add(Projections.sqlGroupProjection("date(data_criacao) as data",
				                            "date(data_criacao)", new String[] {"data" },
				                            new Type[] { StandardBasicTypes.DATE } ))
				                            .add(Projections.sum("valorTotal").as("valor"))
				                            )
				                            .add(Restrictions.ge("dataCriacao", dataInicial.getTime()));
		
		if(criadoPor != null){
			criteria.add(Restrictions.eq("vendedor", criadoPor));
			
		}
		
		@SuppressWarnings("unchecked")
		List<DataValor> valoresPorData = criteria
				.setResultTransformer(Transformers.aliasToBean(DataValor.class)).list();
		
		for(DataValor dataValor : valoresPorData){
			resultado.put(dataValor.getData(), dataValor.getValor());
		}
		
		// select date(data_criacao) as data, sum(valor_total) as valor
		// from pedido where data_criacao >= :dataInicial and vendedor_id = :criador 
		// groupy by date(data_criacao)
		
		return resultado;

	}
	

	private Map<Date, BigDecimal> criarMapaVazio(Integer numeroDeDias, Calendar dataInicial) {

		dataInicial = (Calendar) dataInicial.clone();
		Map<Date, BigDecimal> mapaInicial = new TreeMap();

		for (int i = 0; i <= numeroDeDias; i++) {
			mapaInicial.put(dataInicial.getTime(), BigDecimal.ZERO);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}

		return mapaInicial;
	}

}
