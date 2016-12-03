package com.khh.project.config.web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

@Slf4j
public class FilterInvocationSecurityMetadataSource implements org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource {
//	@Autowired
//	private ResourceMetaService resourceMetaService;
//
//	@Autowired
//	private CacheManager cacheManager;


	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		log.debug(object.toString());
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		resourceMetaService.findAllResources();
//	}
}
