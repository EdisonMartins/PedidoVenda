package com.algaworks.pedidovenda.application.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@Named
@RequestScoped
public class GraficoPedidosCriadosBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private LineChartModel model;
	
	@PostConstruct
	public void init(){
		this.model = new LineChartModel();
		this.model.setAnimate(true);
		this.model.setTitle("Pedidos criados");
		//Legenda ao e -east (Leste)
		this.model.setLegendPosition("e");
		
		Axis yAxis = this.model.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(1000);
        yAxis.setTickInterval("100");
		
        Axis xAxis = this.model.getAxis(AxisType.X);
        xAxis.setMin(0);
        xAxis.setMax(5);
        xAxis.setTickInterval("1");
        
		
		adicionarSerie("Todos os pedidos");
		adicionarSerie("Meus pedidos");
	}

	
	
	
	private void adicionarSerie(String rotulo) {
		LineChartSeries series = new LineChartSeries(rotulo);
		
		series.set("1", Math.random() * 1000);
		series.set("2", Math.random() * 1000);
		series.set("3", Math.random() * 1000);
		series.set("4", Math.random() * 1000);
		series.set("5", Math.random() * 1000);
		
		this.model.addSeries(series);
		
	}




	public CartesianChartModel getModel() {
		return model;
	}
}
