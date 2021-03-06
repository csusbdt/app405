package app;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AdminAjaxFilter implements Filter
{
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) 
	throws IOException, ServletException
	{
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpResp = (HttpServletResponse) resp;
		UserService userService = UserServiceFactory.getUserService();
        if (!userService.isUserLoggedIn())
        {
        	String continueUrl = httpReq.getRequestURL().toString();
        	continueUrl = continueUrl.substring(0, continueUrl.lastIndexOf("/"));
        	continueUrl += "/admin.jsp";
    		resp.setContentType("application/json");
    		resp.getWriter().print("{ \"login\" : \"" + userService.createLoginURL(continueUrl) + "\" }");
        }
        else if (userService.isUserAdmin())
        {
        	// Use cache-control header to break iOS Safari caching; 
        	// see http://stackoverflow.com/questions/12506897/is-safari-on-ios-6-caching-ajax-results 
        	httpResp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");        	
        	chain.doFilter(req, resp);
        }
        else
        {
        	httpResp.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
	}

	@Override
	public void destroy()
	{
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
	}
}
