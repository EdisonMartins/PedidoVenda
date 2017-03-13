package com.algaworks.pedidovenda.domain.service.exception;

public class NegocioExceptionImpl extends RuntimeException {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public NegocioExceptionImpl(String msg){
			super(msg);
		}
		
}
