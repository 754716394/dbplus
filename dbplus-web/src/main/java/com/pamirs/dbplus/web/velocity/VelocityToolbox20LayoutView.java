package com.pamirs.dbplus.web.velocity;

import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

public class VelocityToolbox20LayoutView extends VelocityLayoutView {  
    
	public VelocityToolbox20LayoutView() {
		super();
//		super.setLayoutUrl("/layouts/layout.vm");
		
	}


	@Override
    protected Context createVelocityContext(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response)  
            throws Exception {// Create a
                                // ChainedContext  
                                // instance.  
		if(this.getWebApplicationContext().getResource("views/layouts/"+getBeanName()+".vm").isReadable()){
			super.setLayoutUrl("/layouts/"+getBeanName()+".vm");
		}
		
//		this.checkResource(new Locale(getBeanName()+".vm"));
		
        ViewToolContext ctx;  
  
        ctx = new ViewToolContext(getVelocityEngine(), request, response,  
                getServletContext());  
        model.put("timestamp", new Date().getTime());
        ctx.putAll(model);  
  
        if (this.getToolboxConfigLocation() != null) {  
            ToolManager tm = new ToolManager();  
            tm.setVelocityEngine(getVelocityEngine());  
            tm.configure(getServletContext().getRealPath(  
                    getToolboxConfigLocation()));  
            if (tm.getToolboxFactory().hasTools(Scope.REQUEST)) {  
                ctx.addToolbox(tm.getToolboxFactory().createToolbox(  
                        Scope.REQUEST));  
            }  
            if (tm.getToolboxFactory().hasTools(Scope.APPLICATION)) {  
                ctx.addToolbox(tm.getToolboxFactory().createToolbox(  
                        Scope.APPLICATION));  
            }  
            if (tm.getToolboxFactory().hasTools(Scope.SESSION)) {  
                ctx.addToolbox(tm.getToolboxFactory().createToolbox(  
                        Scope.SESSION));  
            }  
        }  
        return ctx;  
    }  
}  
