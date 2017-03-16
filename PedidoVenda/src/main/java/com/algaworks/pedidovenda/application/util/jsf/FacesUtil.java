package com.algaworks.pedidovenda.application.util.jsf;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.algaworks.pedidovenda.security.MyJsfAjaxTimeoutSetting;

public class FacesUtil {
	
	public static boolean isPostBack(){
		return FacesContext.getCurrentInstance().isPostback();
	}
	
	
	public static boolean isNotPostBack(){
		return !FacesUtil.isPostBack();
	}
	
	
	public static void addErrorMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
	}
	
	
	public static void addInfoMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
	}
	
	public static MyJsfAjaxTimeoutSetting getManagedBean(String beanName) {
		FacesContext fc = FacesContext.getCurrentInstance();
	     ELContext elc = fc.getELContext();
	     ExpressionFactory ef = fc.getApplication().getExpressionFactory();
	     ValueExpression ve = ef.createValueExpression(elc, getJsfEl(beanName), Object.class);
	     return (MyJsfAjaxTimeoutSetting) ve.getValue(elc);
	}
	
	private static String getJsfEl(String value) {
		return "#{" + value + "}";
	}
	
}
