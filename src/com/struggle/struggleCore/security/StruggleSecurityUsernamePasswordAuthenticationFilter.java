/**
 * 
 */
package com.struggle.struggleCore.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.TextEscapeUtils;

import com.struggle.struggleCore.jcaptcha.CaptchaServiceSingleton;

/**
 * spring安全框架-登录信息过滤器
 * 
 * @author yezebin
 * 
 */
public class StruggleSecurityUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	// 验证码
	public static final String SPRING_SECURITY_FORM_VALIDATECODE_KEY = "j_validatecode";

	private String validateCodeParameter = SPRING_SECURITY_FORM_VALIDATECODE_KEY;

	private boolean postOnly = true;

	public StruggleSecurityUsernamePasswordAuthenticationFilter() {

		super();

		validateCodeParameter = "j_validatecode";

	}

	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST"))
			throw new AuthenticationServiceException((new StringBuilder())
					.append("Authentication method not supported: ")
					.append(request.getMethod()).toString());
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		// 匹配验证码是否正确
		checkValidateCode(request);
		if (username == null)
			username = "";
		if (password == null)
			password = "";
		username = username.trim();
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);
		HttpSession session = request.getSession(false);
		if (session != null || getAllowSessionCreation())
			request.getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME",
					TextEscapeUtils.escapeEntities(username));
		setDetails(request, authRequest);
		return getAuthenticationManager().authenticate(authRequest);

	}

	protected void checkValidateCode(HttpServletRequest request) {

		String validateCode = obtainValidateCode(request);

		if (validateCode == null)
			throw new AuthenticationServiceException("验证码超时");

		boolean checkResult = CaptchaServiceSingleton.getInstance()
				.validateResponseForID(request.getSession().getId(),
						validateCode);

		if (!checkResult)
			throw new AuthenticationServiceException("验证码不正确");
	}

	protected String obtainValidateCode(HttpServletRequest request) {

		return request.getParameter(validateCodeParameter);
	}

}
