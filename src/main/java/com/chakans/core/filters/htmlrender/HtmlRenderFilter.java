package com.chakans.core.filters.htmlrender;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class HtmlRenderFilter implements Filter {

    private final List<String> PARAMETER_NAMES = Lists.newArrayList("remoteAddressForChromeDriver", "token", "whiteList");

    private HtmlRenderService htmlRenderService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.htmlRenderService = new HtmlRenderService(toMap(filterConfig));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (htmlRenderService.isExecutable()) {
            if (htmlRenderService.execute((HttpServletRequest) request, (HttpServletResponse) response)) {
                return;
            }
            if (!htmlRenderService.isAcceptable((HttpServletRequest) request, (HttpServletResponse) response)) {
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        htmlRenderService.destroy();
    }

    protected Map<String, String> toMap(FilterConfig filterConfig) {
        Map<String, String> config = Maps.newHashMap();
        for (String parameterName : PARAMETER_NAMES) {
            config.put(parameterName, filterConfig.getInitParameter(parameterName));
        }
        return config;
    }



}