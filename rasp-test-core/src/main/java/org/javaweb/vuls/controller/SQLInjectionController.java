package org.javaweb.vuls.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.javaweb.utils.HttpServletResponseUtils.responseHTML;

/**
 * Creator: yz
 * Date: 2020-05-04
 */
@Controller
@RequestMapping("/SQLInjection/")
public class SQLInjectionController {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@RequestMapping("/Login.php")
	public void login(String username, String password, String action,
	                  HttpServletRequest request, HttpServletResponse response,
	                  HttpSession session) throws IOException {

		String      sessionKey  = "USER_INFO";
		Object      sessionUser = session.getAttribute(sessionKey);
		PrintWriter out         = response.getWriter();

		// 退出登陆
		if (sessionUser != null && "exit".equals(action)) {
			session.removeAttribute(sessionKey);
			responseHTML(response, "<script>alert('Bye!');location.reload();</script>");
			return;
		}

		Map<String, String> userInfo;

		// 检查用户是否已经登陆成功
		if (sessionUser instanceof Map) {
			userInfo = (Map<String, String>) sessionUser;
			responseHTML(response,
					"<p>Welcome:" + userInfo.get("username") + ",ID:" +
							userInfo.get("id") + " \r<a href='?action=exit'>Logout</a></p>"
			);

			return;
		}

		// 处理用户登陆逻辑
		if (username != null && password != null) {
			userInfo = new HashMap<>();

			try {
				String sql = "select id,username,password from sys_user where username = '" +
						username + "' and password = '" + password + "'";

				System.out.println(sql);

				jdbcTemplate.queryForMap(sql, userInfo);

				// 检查是否登陆成功
				if (userInfo.size() > 0) {
					// 设置用户登陆信息
					session.setAttribute(sessionKey, userInfo);

					// 跳转到登陆成功页面
					response.sendRedirect(request.getServletPath());
				} else {
					responseHTML(response, "<script>alert('Login failed, username or password is wrong!');history.back(-1)</script>");
				}
			} catch (Exception e) {
				responseHTML(response, "<script>alert('Login error!');history.back(-1)</script>");
			}

			return;
		}

		responseHTML(response, "<html>\n" +
				"<head>\n" +
				"    <meta charset='UTF-8'/>" +
				"    <title>Login Test</title>\n" +
				"</head>\n" +
				"<body>\n" +
				"<div style=\"margin: 30px;\">\n" +
				"    <form action=\"#\" method=\"POST\">\n" +
				"        Username:<input type=\"text\" name=\"username\" value=\"admin\"/><br/>\n" +
				"        Password:<input type=\"text\" name=\"password\" value=\"' or '1'='1\"/><br/>\n" +
				"        <input type=\"submit\" value=\"Login\"/>\n" +
				"    </form>\n" +
				"</div>\n" +
				"</body>\n" +
				"</html>");

		out.flush();
		out.close();
	}

}
