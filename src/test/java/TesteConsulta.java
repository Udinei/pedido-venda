import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import com.algaworks.pedidovenda.model.DataValor;
import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.model.UsuarioValor;

public class TesteConsulta {

	public static void main(String[] args) {
		 EntityManagerFactory factory =
		 Persistence.createEntityManagerFactory("PedidoPU");
		 EntityManager manager = factory.createEntityManager();
		 Session session = manager.unwrap(Session.class);

		 Usuario usuario = new Usuario();
		 usuario.setId(1L);
		 
		// Map<Date, BigDecimal> valores = valoresTotaisPorData(15, usuario, session );
		   Map<String, BigDecimal> valores = valoresTotaisPorUsuario(usuario, session );
		 
		/*for (Date data : valores.keySet()) {
			System.out.println(data + " = " + valores.get(data));
		}*/

		for (String vendedor : valores.keySet()) {
			System.out.println(vendedor + " = " + valores.get(vendedor));
		}
		 manager.close();
	}

	
	
	public static Map<Date, BigDecimal> valoresTotaisPorData(
			Integer numeroDeDias, Usuario criadoPor, Session session) {

		numeroDeDias -= 1; //subtraindo um dia
		
		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH); //anulando hora minuto e segundo da data
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias * -1);//criando uma data retroativa para 15 dias pra tras

		Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDeDias, dataInicial); 

		Criteria criteria = session.createCriteria(Pedido.class);
		
		// select date(data_criacao) as data, sum(valor_total) as valor
		// from pedido where data_criacao >= :dataInicial and vendedor_id = :criador 
		// groupy by date(data_criacao)
		
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
		
		return resultado;

	}
	

	private static Map<Date, BigDecimal> criarMapaVazio(Integer numeroDeDias, Calendar dataInicial) {

		dataInicial = (Calendar) dataInicial.clone();
		Map<Date, BigDecimal> mapaInicial = new TreeMap();

		for (int i = 0; i <= numeroDeDias; i++) {
			mapaInicial.put(dataInicial.getTime(), BigDecimal.ZERO);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}

		return mapaInicial;
	}
	
	
	
	public static Map<String, BigDecimal> valoresTotaisPorUsuario(Usuario usuario, Session session){
	
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
