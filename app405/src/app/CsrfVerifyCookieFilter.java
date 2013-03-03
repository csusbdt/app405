package app;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CsrfVerifyCookieFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpResp = (HttpServletResponse) resp;
		String csrfParam = req.getParameter("csrf");
		String csrfCookie = null;
		Cookie cookies[] = httpReq.getCookies();
		for (int i = 0; i < cookies.length; ++i) 
		{
			if (cookies[i].getName().equals("csrf")) 
			{
				csrfCookie = cookies[i].getValue();
				break;
			}
		}
		if (csrfParam == null || csrfCookie == null || !csrfParam.equals(csrfCookie))
		{
			Logger.getLogger("app.SetAdminMessageServlet").warning("bad csrf token");
			httpResp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
    	chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
