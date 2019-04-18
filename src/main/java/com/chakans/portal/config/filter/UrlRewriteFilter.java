package com.chakans.portal.config.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tuckey.web.filters.urlrewrite.Conf;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.InputStream;
import java.net.URL;

/**
 * Subclass of {@link org.tuckey.web.filters.urlrewrite.UrlRewriteFilter}
 * that overrides the configuration file loading mechanism.
 */
public class UrlRewriteFilter extends org.tuckey.web.filters.urlrewrite.UrlRewriteFilter {

    private final Logger log = LoggerFactory.getLogger(UrlRewriteFilter.class);

    @Override
    protected void loadUrlRewriter(FilterConfig filterConfig) throws ServletException {
        ServletContext context = filterConfig.getServletContext();
        String confPath = filterConfig.getInitParameter("confPath");
        boolean modRewriteStyleConf = getModRewriteStyleConf(filterConfig.getInitParameter("modRewriteConf"));
        try {
            final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(confPath);
            final URL confUrl = getClass().getClassLoader().getResource(confPath);

            if (inputStream == null) {
                log.error("unable to find urlrewrite conf file at " + confPath);
                // set the writer back to null
                if (isLoaded()) {
                    log.error("unloading existing conf");
                    destroyUrlRewriter();
                }

            } else {
                Conf conf = new Conf(context, inputStream, confPath, confUrl.toString(), modRewriteStyleConf);
                checkConf(conf);
            }
        } catch (Throwable e) {
            log.error("unloading urlrewrite conf file at " + confPath, e);
            throw new ServletException(e);
        }
    }

    private boolean getModRewriteStyleConf(String modRewriteConf) {
        boolean modRewriteStyleConf = false;
        if (!StringUtils.isBlank(modRewriteConf)) {
            modRewriteStyleConf = "true".equals(StringUtils.trim(modRewriteConf).toLowerCase());
        }
        return modRewriteStyleConf;
    }
}
