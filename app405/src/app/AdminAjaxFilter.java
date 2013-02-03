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
		HttpServletResponse httpResp = (HttpServletResponse) resp;
		UserService userService = UserServiceFactory.getUserService();
        if (!userService.isUserLoggedIn())
        {
    		resp.setContentType("application/json");
    		resp.getWriter().print("{ \"login\" : \"true\" }");
        }
        else if (userService.isUserAdmin())
        {
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
