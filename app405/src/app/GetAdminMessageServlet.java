package app;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class GetAdminMessageServlet extends HttpServlet
{
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException
	{
		AdminMessage adminMessage = AdminMessage.getAdminMessage();
		resp.setContentType("application/json");
		resp.getWriter().print("{ \"msg\" : \"" + adminMessage.getText() + "\" }");
	}
}
