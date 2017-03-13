package com.algaworks.pedidovenda.application.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("*** contextInitialized ***");

		System.out.println("TOMCAT 7 - COERCE_TO_ZERO = false  ->  String e Wrappers assumirão valor como NULL, caso não forem preenchidos seus campos.");
		System.getProperties().put("org.apache.el.parser.COERCE_TO_ZERO", "false");
	}

}
