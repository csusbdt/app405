package app;

import java.io.IOException;
import java.util.logging.Logger;

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
		}
		else
		{
			text = text.replaceAll("<", "&lt;"); // cross-site scripting protection
			AdminMessage.createOrUpdate(text);
		}
		resp.getWriter().print(json);
	}
}
