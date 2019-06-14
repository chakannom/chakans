package com.chakans.portal.config;

import com.chakans.core.config.constants.AuthoritiesConstants;
import com.chakans.core.security.*;
import com.chakans.core.security.jwt.*;

import io.github.jhipster.config.JHipsterProperties;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final SecurityProblemSupport problemSupport;
    private final JHipsterProperties jHipsterProperties;
    private final ApplicationProperties applicationProperties;

    public SecurityConfiguration(CorsFilter corsFilter, SecurityProblemSupport problemSupport, JHipsterProperties jHipsterProperties, ApplicationProperties applicationProperties) {
        this.corsFilter = corsFilter;
        this.problemSupport = problemSupport;
        this.jHipsterProperties = jHipsterProperties;
        this.applicationProperties = applicationProperties;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenProvider tokenProvider() {
        return new TokenProvider(jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret(),
            jHipsterProperties.getSecurity().getAuthentication().getJwt().getBase64Secret(),
                jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds(),
                jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/h2-console/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
            .addFilterBefore(corsFilter, CsrfFilter.class)
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(problemSupport)
            .accessDeniedHandler(problemSupport)
        .and()
            .headers()
            .frameOptions()
            .disable()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers("/apis/authenticate").permitAll()
            .antMatchers("/apis/register").permitAll()
            .antMatchers("/apis/activate").permitAll()
            .antMatchers("/apis/account/reset-password/init").permitAll()
            .antMatchers("/apis/account/reset-password/finish").permitAll()
            .antMatchers(applicationProperties.getUrlSecurity().getPermitApisPaths()).permitAll()
            .antMatchers("/apis/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
        .and()
            .apply(securityConfigurerAdapter());
        // @formatter:on
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider());
    }
}
