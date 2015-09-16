package com.algawork.pedidovenda.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

import com.algawork.pedidovenda.repository.Pedidos;
import com.algawork.pedidovenda.repository.Usuarios;
import com.algawork.pedidovenda.security.UsuarioLogado;
import com.algawork.pedidovenda.security.UsuarioSistema;
import com.algaworks.pedidovenda.model.Usuario;

@Named
@RequestScoped
@ManagedBean
public class GraficoPedidosCriadosBean implements Serializable {
	

	private static final long serialVersionUID = 1L;

	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");
	
	@Inject
	private Pedidos pedidos;
	
	
	@Inject
	@UsuarioLogado
	private UsuarioSistema usuarioLogado;
	
	//private CartesianChartModel model;
	//private PieChartModel pieModel;
	
	private LineChartModel model;

	public void preRender(){
		//this.model = new CartesianChartModel();
		this.model = new LineChartModel();
		this.model.setTitle("Pedidos criados");
		this.model.setLegendPosition("e");
		this.model.setAnimate(true);
		
		this.model.getAxes().put(AxisType.X, new CategoryAxis());
		//createPieModel();
		geraGraficoLinha();
		
	}


	private void geraGraficoLinha() {
		//this.model = new CartesianChartModel();
			
		adicionarSerie("Todos os pedidos", null);
		adicionarSerie("Meus Pedidos", usuarioLogado.getUsuario());
	}
	

	private void adicionarSerie(String rotulo, Usuario criadoPor) {
		Map<Date, BigDecimal> valoresPorData = this.pedidos.valoresTotaisPorData(15, criadoPor);
		
		ChartSeries series = new ChartSeries(rotulo);
	
		for(Date data : valoresPorData.keySet()){
			series.set(DATE_FORMAT.format(data), valoresPorData.get(data));
			
		}
				
		this.model.addSeries(series);
	
	}
	

	  public LineChartModel getModel() {  
	      return model;  
	  }  

	  
	
	//    public CartesianChartModel getModel(){
    //    	return model;
    //    }
 
    
	//    public PieChartModel getPieModel() {  
	//        return pieModel;  
	//    }  
	//    
	//   
	//	public void setPieModel(PieChartModel pieModel) {
	//		this.pieModel = pieModel;
	//	}
	//
	//
	//	private void createPieModel() {  
	//		
	//		this.pieModel = new PieChartModel();
	//		
	//        Map<String, BigDecimal> valoresPorUsuario = usuarios.valoresTotaisPorUsuario();
	//        
	//        for(String valor :valoresPorUsuario.keySet() ){
	//        	
	//        	this.pieModel.set(valor, valoresPorUsuario.get(valor));
	//          	
	//        }
	       
	//	}
	
	
}
