package com.algaworks.pedidovenda.infrastructure.dao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import com.algaworks.pedidovenda.domain.model.Pedido;
import com.algaworks.pedidovenda.domain.model.Usuario;
import com.algaworks.pedidovenda.domain.model.repository.dto.PedidoFilter;
import com.algaworks.pedidovenda.domain.model.vo.DataValor;

public class PedidoDAO extends GenericDAO<Pedido> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PedidoDAO() {
		super();
	}

	public int getQuantidadeFiltrados(PedidoFilter filtro) {

		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.rowCount());

		return ((Number) criteria.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Pedido> filtrados(PedidoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);

		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistro());

		// Join com cliente
		criteria.createAlias("cliente", "c");
		// Join com vendedor
		criteria.createAlias("vendedor", "v");

		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {

			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));

		} else if (filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}

		return criteria.list();
	}

	private Criteria criarCriteriaParaFiltro(PedidoFilter filtro) {

		Session session = this.entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Pedido.class);
  
		if (filtro.getNumeroDe() != null) {
			// Id deve ser maior ou igual (ge = greater or equals)
			criteria.add(Restrictions.ge("id", filtro.getNumeroDe()));
		}

		if (filtro.getNumeroAte() != null) {

			// Id deve ser menor ou igual (le = lower or equal)
			criteria.add(Restrictions.le("id", filtro.getNumeroAte()));
		}

		if (filtro.getDataCriacaoDe() != null) {
			criteria.add(Restrictions.ge("dataCriacao", filtro.getDataCriacaoDe()));
		}

		if (filtro.getDataCriacaoAte() != null) {
			criteria.add(Restrictions.le("dataCriacao", filtro.getDataCriacaoAte()));
		}

		if (StringUtils.isNotBlank(filtro.getNomeCliente())) {
			// Acessamos o nome do cliente associado ao pedido pelo alias "c",
			// criado anteriormente.
			criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCliente(), MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(filtro.getNomeVendedor())) {
			// Acessamos o nome do vendedor associado ao pedido pelo alias "v",
			// criado anteriormente.
			criteria.add(Restrictions.ilike("v.nome", filtro.getNomeVendedor(), MatchMode.ANYWHERE));
		}

		if (filtro.getStatuses() != null && filtro.getStatuses().length > 0) {
			// Adicionamos uma restrição "in", passando um array de constantes
			// de enum StatusPedido
			criteria.add(Restrictions.in("status", filtro.getStatuses()));
		}

		return criteria;

	}
	
	
	
	@SuppressWarnings({ "unchecked" })
	public Map<Date, BigDecimal> valoresTotaisPorData(Integer numeroDeDias, Usuario criadoPor) {
		Session session = this.getEntityManager().unwrap(Session.class);
		
		numeroDeDias -= 1;
		
		Calendar dataInicial = Calendar.getInstance();
		
		//Mantém a data somente com o dia do mês.
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		
		//Calcula a data inicial. 
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias * -1);
		
		Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDeDias, dataInicial);
		
		Criteria criteria = session.createCriteria(Pedido.class);
		
		// select date(data_criacao) as data, sum(valor_total) as valor 
		// from pedido where data_criacao >= :dataInicial and vendedor_id = :criadoPor 
		// group by date(data_criacao)
		
		criteria.setProjection(Projections.projectionList()
				.add(Projections.sqlGroupProjection("date(data_criacao) as data", 
						"date(data_criacao)", new String[] { "data" }, 
						new Type[] { StandardBasicTypes.DATE } ))
				.add(Projections.sum("valorTotal").as("valor"))
			)
			.add(Restrictions.ge("dataCriacao", dataInicial.getTime()));
		
		if (criadoPor != null) {
			criteria.add(Restrictions.eq("vendedor", criadoPor));
		}
		
		List<DataValor> valoresPorData = criteria
				.setResultTransformer(Transformers.aliasToBean(DataValor.class)).list();
		
		for (DataValor dataValor : valoresPorData) {
			resultado.put(dataValor.getData(), dataValor.getValor());
		}
		
		return resultado;
	}

	/**
	 * Cria mapa iniciando na data inicial até a soma de número de dias. <br />
	 * O Mapa só possui as chaves que são as datas, mas seus valores são 0.
	 * @param numeroDeDias
	 * @param dataInicial
	 * @return
	 */
	private Map<Date, BigDecimal> criarMapaVazio(Integer numeroDeDias,
			Calendar dataInicial) {
		
		//Altera a data na mesma instância.
		dataInicial = (Calendar) dataInicial.clone();
		Map<Date, BigDecimal> mapaInicial = new TreeMap<>();

		for (int i = 0; i <= numeroDeDias; i++) {
			mapaInicial.put(dataInicial.getTime(), BigDecimal.ZERO);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		return mapaInicial;
	}
	
	
	
	

}
