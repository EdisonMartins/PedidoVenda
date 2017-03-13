package com.algaworks.pedidovenda.application.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class LayoutPadraoBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void naoConfirmo() {
		System.out.println("Botão Não pressionado!");
	}

	public void simConfirmo() {
		System.out.println("Botão SIM pressionado!");

	}
}
