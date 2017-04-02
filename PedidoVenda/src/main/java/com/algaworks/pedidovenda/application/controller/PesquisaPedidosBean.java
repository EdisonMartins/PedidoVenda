package com.algaworks.pedidovenda.application.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.algaworks.pedidovenda.application.controller.component.LazyPedidoDataModel;
import com.algaworks.pedidovenda.application.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.domain.model.Pedido;
import com.algaworks.pedidovenda.domain.model.StatusPedido;
import com.algaworks.pedidovenda.domain.model.repository.Pedidos;
import com.algaworks.pedidovenda.domain.model.repository.dto.PedidoFilter;

@Named
@ViewScoped
public class PesquisaPedidosBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3764745591012623625L;
	private PedidoFilter filtro;
	private LazyPedidoDataModel model;
	
	@Inject
	private Pedidos pedidoRep;

	public PesquisaPedidosBean() {
		filtro = new PedidoFilter();
	}

	public void inicializar(){
		
		if(FacesUtil.isNotPostBack()){
			model = new LazyPedidoDataModel(pedidoRep, filtro);
		}
		
	}

			

	public LazyPedidoDataModel getModel() {
		
		return model;
	}

	public PedidoFilter getFiltro() {
		return filtro;
	}
	
	
	public StatusPedido[] getStatuses(){
		return StatusPedido.values();
	}
	
	public void posProcessarXls(Object documento){
		HSSFWorkbook planilha = (HSSFWorkbook) documento;
		HSSFSheet folha = planilha.getSheetAt(0);
		HSSFRow cabecalho = folha.getRow(0);
		HSSFCellStyle estiloCelula = planilha.createCellStyle();
		Font fonteCabecalho = planilha.createFont();
		
		fonteCabecalho.setColor(IndexedColors.WHITE.getIndex());
		fonteCabecalho.setBold(true);
		fonteCabecalho.setFontHeightInPoints((short) 16);
		
		estiloCelula.setFont(fonteCabecalho);
		estiloCelula.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		estiloCelula.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		for (int i = 0; i < cabecalho.getPhysicalNumberOfCells(); i++) {
			cabecalho.getCell(i).setCellStyle(estiloCelula);
		}
	}
	
	

}
