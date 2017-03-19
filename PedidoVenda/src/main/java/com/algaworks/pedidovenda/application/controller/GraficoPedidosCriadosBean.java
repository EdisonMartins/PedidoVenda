package com.algaworks.pedidovenda.application.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.algaworks.pedidovenda.domain.model.Usuario;
import com.algaworks.pedidovenda.domain.model.repository.Pedidos;
import com.algaworks.pedidovenda.security.UsuarioLogado;
import com.algaworks.pedidovenda.security.UsuarioSistema;

@Named
@RequestScoped
public class GraficoPedidosCriadosBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private LineChartModel model;
	
	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");
	private static int NUMERO_DE_DIAS = 15;
	
	@Inject
	private Pedidos pedidos;
	
	@Inject
	@UsuarioLogado
	private UsuarioSistema usuarioLogado;
	
	public void preRender(){
		this.model = new LineChartModel();
		this.model.setAnimate(true);
		this.model.setTitle("Pedidos criados");
		//Legenda ao e -east (Leste)
		this.model.setLegendPosition("e");
		this.model.setShowPointLabels(true);
		this.model.setShowDatatip(false);
		
		Axis yAxis = this.model.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(1000);
        yAxis.setTickInterval("100");
        

        this.model.getAxes().put(AxisType.X, new CategoryAxis("Últimos " + NUMERO_DE_DIAS + "dias"));
		
		
		adicionarSerie("Todos os pedidos", null);
		adicionarSerie("Meus pedidos", usuarioLogado.getUsuario());
	}

	
	
	
	private void adicionarSerie(String rotulo, Usuario criadoPor) {
		Map<Date, BigDecimal> valoresPorData = this.pedidos.valoresTotaisPorData(NUMERO_DE_DIAS, criadoPor);
		
		LineChartSeries series = new LineChartSeries(rotulo);
		
		for (Date data : valoresPorData.keySet()) {
			series.set(DATE_FORMAT.format(data), valoresPorData.get(data));
		}
		
		this.model.addSeries(series);
		
	}




	public CartesianChartModel getModel() {
		return model;
	}
}
