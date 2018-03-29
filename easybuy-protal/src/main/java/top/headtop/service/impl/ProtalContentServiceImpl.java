package top.headtop.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import top.headtop.service.ProtalContentService;
import top.headtop.utils.HttpClientUtil;

@Service
public class ProtalContentServiceImpl implements ProtalContentService{
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_ADVERTISE}")
	private String REST_ADVERTISE;
	
	@Override
	public String getAdvertise() {
		return HttpClientUtil.doGet(REST_BASE_URL+REST_ADVERTISE);
	}

}
