package top.headtop.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import top.headtop.dao.JedisClient;
import top.headtop.mapper.TbUserMapper;
import top.headtop.pojo.EasyBuyResult;
import top.headtop.pojo.TbUser;
import top.headtop.pojo.TbUserExample;
import top.headtop.pojo.TbUserExample.Criteria;
import top.headtop.utils.CookieUtils;
import top.headtop.utils.JsonUtils;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_SEESION_KEY}")
	private String REDIS_SEESION_KEY;
	@Value("${REDIS_SESSION_EXPIRE}")
	private int REDIS_SESSION_EXPIRE;

	@Override
	public EasyBuyResult userCheck(String param, String type) {
		if(!type.isEmpty()&&type.equals("1")){
			List<TbUser> list = selectUser(param);
			if (list.size()==0) {
				return EasyBuyResult.ok(true);
			}
			EasyBuyResult.ok(false);
		}else if(!type.isEmpty()&&type.equals("2")){
			TbUserExample example = new TbUserExample();
			Criteria criteria = example.createCriteria();
			criteria.andPhoneEqualTo(param);
			List<TbUser> list = userMapper.selectByExample(example);
			if (list.size()==0) {
				return EasyBuyResult.ok(true);
			}
			EasyBuyResult.ok(false);
		}
		return EasyBuyResult.ok(false);
	}

	private List<TbUser> selectUser(String param) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(param);
		List<TbUser> list = userMapper.selectByExample(example);
		return list;
	}
	
	@Override
	public EasyBuyResult userRegister(TbUser uiUser) {
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(uiUser.getPassword().getBytes());
		uiUser.setPassword(md5DigestAsHex);
		uiUser.setCreated(new Date());
		uiUser.setUpdated(new Date());
		userMapper.insert(uiUser);
		return EasyBuyResult.ok();
	}

	@Override
	public EasyBuyResult userLogin(TbUser uiUser,HttpServletRequest request,HttpServletResponse response) {
		List<TbUser> list = selectUser(uiUser.getUsername());
		if (list.size()==0) {
			return EasyBuyResult.build(500, "亲,用户名或密码错误！");
		}
		TbUser user = list.get(0);
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(uiUser.getPassword().getBytes());
		if(!md5DigestAsHex.equals(user.getPassword())){
			return EasyBuyResult.build(500, "亲,用户名或密码错误！");
		}
		user.setPassword(null);
		String token = UUID.randomUUID().toString();
		
		CookieUtils.setCookie(request, response, "eb_token", token);
		jedisClient.set(REDIS_SEESION_KEY +":"+token, JsonUtils.objectToJson(user));
		jedisClient.expire(REDIS_SEESION_KEY +":"+token, REDIS_SESSION_EXPIRE);
		return EasyBuyResult.ok(token);
	}

	@Override
	public EasyBuyResult getCookieValue(String token) {

		String redisUserInfo = jedisClient.get(REDIS_SEESION_KEY +":"+token);
		if(StringUtils.isEmpty(redisUserInfo)){
			return EasyBuyResult.build(500, "亲，你的登录已经失效");
		}
		jedisClient.expire(REDIS_SEESION_KEY +":"+token, REDIS_SESSION_EXPIRE);
		return EasyBuyResult.ok(JsonUtils.jsonToPojo(redisUserInfo, TbUser.class));
	}


}
