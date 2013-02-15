package app;

import java.security.SecureRandom;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class Util
{	
	static SecureRandom random = new SecureRandom();

	/*
	 * The following is used to protect against CSRF attacks.
	 */
	public static void addCsrfTokenHeader(HttpServletResponse resp)
	{
    	// Set the CSRF token cookie.
    	Cookie csrfCookie = new Cookie("csrf", "" + random.nextLong());
    	resp.addCookie(csrfCookie);
	}
	
	/*
	 * The following code extracted from http://code.google.com/p/json-simple/ 
	 */
	
	/**
	 * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters
	 * (U+0000 through U+001F).
	 * 
	 * @param s
	 * @return
	 */
	public static String escape(String s)
	{
		if (s == null)
			return null;
		StringBuffer sb = new StringBuffer();
		escape(s, sb);
		return sb.toString();
	}

	/**
	 * @param s
	 *            - Must not be null.
	 * @param sb
	 */
	static void escape(String s, StringBuffer sb)
	{
		for (int i = 0; i < s.length(); i++)
		{
			char ch = s.charAt(i);
			switch (ch)
			{
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				// Reference: http://www.unicode.org/versions/Unicode5.1.0/
				if ((ch >= '\u0000' && ch <= '\u001F')
						|| (ch >= '\u007F' && ch <= '\u009F')
						|| (ch >= '\u2000' && ch <= '\u20FF'))
				{
					String ss = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - ss.length(); k++)
					{
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				} else
				{
					sb.append(ch);
				}
			}
		}
	}
}
