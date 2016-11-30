package com.khh.project.config.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


@Configuration
@EnableWebMvc
@Slf4j
public class WebMvcConfigurerAdapter extends org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter {


	@Value("${spring.mvc.locale}")
	Locale locale;

//    @Value("${libqa.viewResolver.cached}")
//    private String viewResolverCached;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(csrfTokenAddingInterceptor()).addPathPatterns("/**");       //.includePathPatterns("/**") .excludePathPatterns("/**/*.ecxld");
		registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/**");
	}

	@Bean
	public HandlerInterceptor csrfTokenAddingInterceptor() {
		return new HandlerInterceptorAdapter() {
			@Override
			public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView view) {
				CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
				if (token != null) {
					view.addObject(token.getParameterName(), token);
				}
			}
		};
	}



	//다국어 https://justinrodenbostel.com/2014/05/13/part-4-internationalization-in-spring-boot/
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(locale);
		return slr;
	}
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}


	@Bean
	public ReloadableResourceBundleMessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:/messages/message");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(60);
		return messageSource;
	}

	@Bean
	public MessageSourceAccessor getMessageSourceAccessor(){
		ReloadableResourceBundleMessageSource m = messageSource();
		return new MessageSourceAccessor(m);
	}


    //로그인페이지.
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(WebSecurityConfigurerAdapter.LOGIN_PAGE).setViewName("security/login");
//        registry.addViewController(WebSecurityConfigurerAdapter.LOGOUT_URL).setViewName("security/logout");
    }


//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        super.addArgumentResolvers(argumentResolvers);
//    }

    //    @Override
//    public void addInterceptors(final InterceptorRegistry resistry) {
//        resistry.addInterceptor(new LoginUserHandlerInterceptor()).addPathPatterns("/", "/*", "/**", "/*/**");
//    }

//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }

//    @Bean
//    public ServletRegistrationBean h2servletRegistration() {
//        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
//        registration.addUrlMappings("/console/*");
//        return registration;
//    }

//    @Bean
//    public HandlebarsViewResolver viewResolver() throws Exception {
//        HandlebarsViewResolver viewResolver = new HandlebarsViewResolver();
//        viewResolver.setOrder(1);
//        viewResolver.setPrefix("/WEB-INF/views/");
//        viewResolver.setCache(BooleanUtils.toBoolean(viewResolverCached));
//        viewResolver.registerHelpers(new HandlebarsHelper());
//        return viewResolver;
//    }

//    @Bean
//    public EmbeddedServletContainerCustomizer containerCustomizer() {
//        return container -> {
//            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, ERROR_401);
//            ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, ERROR_403);
//            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, ERROR_404);
//            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_500);
//            container.addErrorPages(error401Page, error403Page, error404Page, error500Page);
//        };
//    }

//    @Bean
//    @ConfigurationProperties("spring.datasource")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resource/**").addResourceLocations("/resource/");
//    }


}