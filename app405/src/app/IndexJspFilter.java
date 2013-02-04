package app;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class IndexJspFilter implements Filter
{
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) 
	throws IOException, ServletException
	{
		HttpServletRequest httpReq = (HttpServletRequest) req;
		UserService userService = UserServiceFactory.getUserService();
        if (userService.isUserLoggedIn())
        {
        	req.setAttribute("isAdmin", userService.isUserAdmin());
        	req.setAttribute("loginUrl", userService.createLogoutURL(httpReq.getRequestURL().toString()));
        	req.setAttribute("loginTitle", "Logout");
        	chain.doFilter(req, resp);
        }
        else
        {
        	req.setAttribute("isAdmin", false);
        	Logger.getLogger("app").warning(httpReq.getRequestURL().toString());
        	req.setAttribute("loginUrl", userService.createLoginURL(httpReq.getRequestURL().toString()));
        	req.setAttribute("loginTitle", "Login");
        	chain.doFilter(req, resp);
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
