package com.algaworks.pedidovenda.security;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;

import com.algaworks.pedidovenda.application.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.application.util.jsf.SessionUtil;
import com.algaworks.pedidovenda.domain.model.Usuario;



public class MyJsfAjaxTimeoutPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 2462635991155654030L;

	@Override
	public void afterPhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		MyJsfAjaxTimeoutSetting timeoutSetting = (MyJsfAjaxTimeoutSetting)FacesUtil.getManagedBean("MyJsfAjaxTimeoutSetting");
		  FacesContext fc = FacesContext.getCurrentInstance();
		  RequestContext rc = RequestContext.getCurrentInstance();
		  ExternalContext ec = fc.getExternalContext();
		  HttpServletResponse response = (HttpServletResponse) ec.getResponse();
		  HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		         
		  if (timeoutSetting ==null) {
		   System.out.println("JSF Ajax Timeout Setting is not configured. Do Nothing!");
		   return ;
		  }
		 
		   
		  Usuario usuario = SessionUtil.getUsuario();
		  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		  //
		  // You can replace the above line of code with the security control of your application.
		  // For example , you may get the authenticated user object from session or threadlocal storage. It depends on your design.
		  //
		  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		  
		  if (usuario==null) {
		   // user credential not found. 
		   // considered to be a Timeout case
		      
		 
		   if (ec.isResponseCommitted()) {
		    // redirect is not possible
		    return;
		   }
		       
		   try{
		        
		       if ( 
		     ( (rc!=null &&  RequestContext.getCurrentInstance().isAjaxRequest())
		     || (fc!=null && fc.getPartialViewContext().isPartialRequest()))
		      && fc.getResponseWriter() == null
		       && fc.getRenderKit() == null) {
		 
		        response.setCharacterEncoding(request.getCharacterEncoding());
		   
		        RenderKitFactory factory = (RenderKitFactory)  
		     FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
		   
		        RenderKit renderKit = factory.getRenderKit(fc,
		     fc.getApplication().getViewHandler().calculateRenderKitId(fc));
		   
		        ResponseWriter responseWriter =
		     renderKit.createResponseWriter(
		     response.getWriter(), null, request.getCharacterEncoding());
		     fc.setResponseWriter(responseWriter);
		      
		        ec.redirect(ec.getRequestContextPath() + 
		          (timeoutSetting.getTimeoutUrl() != null ? timeoutSetting.getTimeoutUrl() : ""));     
		       }
		         
		   } catch (IOException e) {
		    System.out.println("Redirect to the specified page '" + 
		      timeoutSetting.getTimeoutUrl() + "' failed");
		    throw new FacesException(e);
		   }
		  } else {
		   return ; //This is not a timeout case . Do nothing !
		  }
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
