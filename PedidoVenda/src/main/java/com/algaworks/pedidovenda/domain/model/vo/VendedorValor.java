package com.algaworks.pedidovenda.domain.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;

import com.algaworks.pedidovenda.domain.model.Usuario;

public class VendedorValor implements Serializable {
	private static final long serialVersionUID = 9097838706933325757L;
	
	private Usuario vendedor;
	private BigDecimal valor;
	
	
	public Usuario getVendedor() {
		return vendedor;
	}
	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}
	public BigDecimal getValor() {
		return valor;
	}
	
	
	public String getValorFormatado(){
		NumberFormat nf = new java.text.DecimalFormat("¤ #,###,##0.00");
		return nf.format(this.getValor());
	}
	
	
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		result = prime * result + ((vendedor == null) ? 0 : vendedor.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VendedorValor other = (VendedorValor) obj;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		if (vendedor == null) {
			if (other.vendedor != null)
				return false;
		} else if (!vendedor.equals(other.vendedor))
			return false;
		return true;
	}
	
	
	
	

}
