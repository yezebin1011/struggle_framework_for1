/**
 * 
 */
package com.struggle.struggleCore.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.struggle.management.base.consumer.manage.ConsumerManage;
import com.struggle.model.Consumer;

/**
 * spring安全框架-具体调用数据库数据验证用户信息的实现类
 * 
 * @author yzb
 * 
 */
public class StruggleSecurityUserDetailsService implements UserDetailsService {

	@Resource
	private ConsumerManage consumerManage;

	@Override
	public UserDetails loadUserByUsername(String consumer_name)
			throws UsernameNotFoundException, DataAccessException {

		User loadUser = null;

		Map<String, String> condition = new HashMap<String, String>();

		condition.put("consumer_name", consumer_name);

		List<Consumer> list = new ArrayList<Consumer>();

		try {
			list = consumerManage.getConsumerLogin(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!list.isEmpty()) {

			Consumer consumer = list.get(0);

			List<GrantedAuthorityImpl> authoriesList = new ArrayList<GrantedAuthorityImpl>();

			loadUser = new User(consumer.getConsumer_name(),
					consumer.getConsumer_pwd(), true, true, true, true,
					authoriesList);

		}

		return loadUser;
	}

}
