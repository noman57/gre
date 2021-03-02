package com.project.gre.security;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("corsFilter")
public class CustomCorsFilter extends CorsFilter {

    String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    String ACCESS_CONTROL_ALLOW_ORIGIN_VALUE = "*";

    String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    String ACCESS_CONTROL_ALLOW_METHODS_VALUE = "GET, POST, PUT, PATCH, DELETE, OPTIONS";

    String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    String ACCESS_CONTROL_ALLOW_HEADERS_VALUE = "Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers";

    public CustomCorsFilter(final CorsConfigurationSource configSource) {
        super(configSource);
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
        response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, ACCESS_CONTROL_ALLOW_METHODS_VALUE);
        response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, ACCESS_CONTROL_ALLOW_HEADERS_VALUE);
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
