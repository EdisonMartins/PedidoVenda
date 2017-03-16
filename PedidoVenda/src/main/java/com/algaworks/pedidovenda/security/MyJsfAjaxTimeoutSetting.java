package com.algaworks.pedidovenda.security;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class MyJsfAjaxTimeoutSetting implements Serializable{

	private static final long serialVersionUID = -3909532493297114212L;

	public MyJsfAjaxTimeoutSetting() {
	}

	private String timeoutUrl;

	public String getTimeoutUrl() {
		return timeoutUrl;
	}

	public void setTimeoutUrl(String timeoutUrl) {
		this.timeoutUrl = timeoutUrl;
	}
}
