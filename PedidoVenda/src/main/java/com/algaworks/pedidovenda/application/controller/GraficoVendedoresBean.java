package com.algaworks.pedidovenda.application.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import com.algaworks.pedidovenda.domain.model.Usuario;
import com.algaworks.pedidovenda.domain.model.repository.Pedidos;

/**
 * O PrimeFaces usa o jqPlot 
 * @author edison
 *
 */
@Named
@RequestScoped
public class GraficoVendedoresBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private PieChartModel pieModel;
	
	@Inject
	private Pedidos pedidos;
	
	    public void preRender() {
	    	pieModel = new PieChartModel();
	    	
	        pieModel.setTitle("Vendedores");
	        pieModel.setLegendPosition("e");
	        pieModel.setFill(true);
	        pieModel.setShowDataLabels(true);
	        pieModel.setDiameter(200);
	        pieModel.setMouseoverHighlight(true);
	        pieModel.setDataFormat("value");
	        pieModel.setSliceMargin(5);
	        
	        Map<Usuario, BigDecimal> valoresPorVendedor = pedidos.valoresTotaisPorVendedor();
	        for (Usuario vendedor : valoresPorVendedor.keySet()) {
	        	pieModel.set(vendedor.getNome(), valoresPorVendedor.get(vendedor));
			}
	    }


		public PieChartModel getPieModel() {
			return pieModel;
		}
	    
	    



}
