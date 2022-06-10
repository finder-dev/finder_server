package com.cmc.finder.global.config;

import com.cmc.finder.global.interceptor.AdminAuthorizationInterceptor;
import com.cmc.finder.global.interceptor.AuthenticationInterceptor;
import com.cmc.finder.global.resolver.UserEmailArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;

//    private final AdminAuthorizationInterceptor adminAuthorizationInterceptor;

    private final UserEmailArgumentResolver userEmailArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authenticationInterceptor)
                .order(1)
                .excludePathPatterns("/api/login","/api/signup", "/auth/kakao/callback", "/api/health", "/api/oauth/login", "/api/token/reissue") // 해당 경로는 인터셉터가 가로채지 않는다.
                .addPathPatterns("/api/**");

//        registry.addInterceptor(adminAuthorizationInterceptor)
//                .order(2)
//                .addPathPatterns("/api/admin/**");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userEmailArgumentResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name()
                );
    }

}