import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.Endereco;
import com.algaworks.pedidovenda.model.TipoPessoa;


public class Teste {

	
	public static void main(String[] args){
		
		EntityManagerFactory factory= Persistence.createEntityManagerFactory("PedidoPU");
		EntityManager manager = factory.createEntityManager();
		
		EntityTransaction trx =  manager.getTransaction();
		trx.begin();
		
		Cliente cliente = new Cliente();
		
		cliente.setNome("João das couves 2");
		cliente.setEmail("joao@dascouves.com");
		cliente.setDocumentoReceitaFederal("543.091.689-87");
		cliente.setTipo(TipoPessoa.J);
		
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua das aboboras vermelhas");
		endereco.setNumero("111");
		endereco.setCidade("Uberlandia");
		endereco.setUf("MG");
		endereco.setCep("79091-720");
		endereco.setCliente(cliente);
		
		cliente.getEnderecos().add(endereco);
		
		manager.persist(cliente);
		trx.commit();
		
		
	}
}
