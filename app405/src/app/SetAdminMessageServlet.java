package app;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SetAdminMessageServlet extends HttpServlet
{
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException
	{
		String text = req.getParameter("text");
		resp.setContentType("application/json");
		String json = "{}";
		if (text == null)
		{
			json = "{ \"error\" : \"bad request\" }";
			Logger.getLogger("app.SetAdminMessageServlet").warning("text parameter missing");
			return;
		}
		String csrfParam = req.getParameter("csrf");
		String csrfCookie = null;
		Cookie cookies[] = req.getCookies();
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
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		text = text.replaceAll("<", "&lt;"); // cross-site scripting protection
		AdminMessage.createOrUpdate(text);
		resp.getWriter().print(json);
	}
}
