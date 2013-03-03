package app;

import java.io.IOException;
import java.security.SecureRandom;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Set a new CSRF cookie token for every request that comes in.
 * Only scripts originating from the site will have access to 
 * this value, so servlets can protect against malicious requests
 * by requiring that requests contain the csrf cookie token.
 */
public class CsrfSetCookieFilter implements Filter {
	
	static SecureRandom random = new SecureRandom();
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResp = (HttpServletResponse) resp;
    	Cookie csrfCookie = new Cookie("csrf", "" + random.nextLong());
    	httpResp.addCookie(csrfCookie);
    	chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
