/**
 * 
 */
package com.struggle.struggleCore.jcaptcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.octo.captcha.service.CaptchaServiceException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 验证码图片生成servlet
 * 
 * @author yezebin
 * 
 */
public class JcaptchaFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;

		HttpServletResponse response = (HttpServletResponse) res;

		genernateCaptchaImage(request, response);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	private void genernateCaptchaImage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		byte[] captchaChallengeAsJpeg = null;

		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();

		try {
			String captchaId = request.getSession().getId();

			BufferedImage challenge = CaptchaServiceSingleton.getInstance()
					.getImageChallengeForID(captchaId, request.getLocale());

			JPEGImageEncoder jpegEncoder = JPEGCodec
					.createJPEGEncoder(jpegOutputStream);

			jpegEncoder.encode(challenge);

		} catch (CaptchaServiceException e) {

			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

			return;

		} catch (IllegalArgumentException e) {

			response.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;

		} catch (Exception e) {

			e.printStackTrace();
		}

		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

		// flush it in the response
		response.setHeader("Cache-Control", "no-store");

		response.setHeader("Pragma", "no-cache");

		response.setDateHeader("Expires", 0);

		response.setContentType("image/jpeg");

		ServletOutputStream responseOutputStream = response.getOutputStream();

		responseOutputStream.write(captchaChallengeAsJpeg);

		responseOutputStream.flush();

		responseOutputStream.close();

	}

}
