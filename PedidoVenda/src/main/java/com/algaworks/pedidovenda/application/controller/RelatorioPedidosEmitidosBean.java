package com.algaworks.pedidovenda.application.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.Session;

import com.algaworks.pedidovenda.application.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.infrastructure.util.report.ExecutorRelatorio;

@Named
@RequestScoped
public class RelatorioPedidosEmitidosBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Date dataInicio;
	private Date dataFim;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;
	
	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		
		//Parâmetros devem ser iguaid ao colocado no arquivo .jrxml
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_fim", this.dataFim);
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/relatorio_pedidos_emitidos.jasper",
				this.response, parametros, "Pedidos emitidos.pdf");
		
		Session session = (Session) manager;
		
		//O Relatório realiza uma consulta sql, por este motivo usa-se o doWork
		session.doWork(executor);
		
		if (executor.isRelatorioGerado()) {
			//Interrompe o ciclo de vida do JSF
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}

	@NotNull
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@NotNull
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}
