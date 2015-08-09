/**
 * 
 */
package com.struggle.struggleCore.log;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.ThrowsAdvice;

/**
 * 系统方法操作记录日志
 * 
 * @author yezebin
 * 
 */
public class StruggleSpringLog implements AfterReturningAdvice, ThrowsAdvice {

	private static final Logger struggleLog = Logger
			.getLogger(StruggleSpringLog.class);

	public StruggleSpringLog() {

	}

	@Override
	public void afterReturning(Object arg0, Method arg1, Object[] arg2,
			Object arg3) throws Throwable {

		if (!struggleLog.isInfoEnabled())// 日志级别不是info的，无需记录信息
			return;

		// 方法名信息
		String methodName = arg1.getName();

		// 参数列表信息
		String params = "";

		Class<?>[] parameterTypes = arg1.getParameterTypes();

		if (parameterTypes != null && parameterTypes.length > 0) {

			for (int i = 0; i < parameterTypes.length; i++) {

				Class<?> clas = parameterTypes[i];

				params += clas.getName();

				if (i != parameterTypes.length - 1 && parameterTypes.length > 1)
					params += ",";
			}

		}

		String logResult = " 执行方法名 ："
				+ methodName
				+ " 参数列表："
				+ (StringUtils.isNotBlank(params) ? "( " + params + " )"
						: "无参数") + " 已经被成功执行";

		struggleLog.info(logResult);

	}

	public void afterThrowing(Method arg1, Object[] arg2, Object arg3,
			Throwable throwable) {

		// 方法名信息
		String methodName = arg1.getName();

		// 参数列表信息
		String params = "";

		Class<?>[] parameterTypes = arg1.getParameterTypes();

		if (parameterTypes != null && parameterTypes.length > 0) {

			for (int i = 0; i < parameterTypes.length; i++) {

				Class<?> clas = parameterTypes[i];

				params += clas.getName();

				if (i != parameterTypes.length - 1 && parameterTypes.length > 1)
					params += ",";

			}

		}

		String logResult = " 执行方法名 ："
				+ methodName
				+ " 参数列表："
				+ (StringUtils.isNotBlank(params) ? "( " + params + " )"
						: "无参数") + " 执行发生错误，具体信息为：\n" + throwable.toString();

		struggleLog.error(logResult);
	}

}
