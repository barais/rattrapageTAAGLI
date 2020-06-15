package com.eit.vipo.config;

import java.util.Collection;
import java.util.List;

import com.eit.vipo.security.*;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.security.*;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JHipsterProperties jHipsterProperties;

    private final CorsFilter corsFilter;
    private final SecurityProblemSupport problemSupport;
    private UserDetailsService userDetailsService;

    public SecurityConfiguration(JHipsterProperties jHipsterProperties, // RememberMeServices rememberMeServices */,
            CorsFilter corsFilter, SecurityProblemSupport problemSupport, UserDetailsService userDetailsService) {
        this.jHipsterProperties = jHipsterProperties;
        // this.rememberMeServices = rememberMeServices;
        this.corsFilter = corsFilter;
        this.problemSupport = problemSupport;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
        return new AjaxAuthenticationSuccessHandler();
    }

    @Bean
    public AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
        return new AjaxAuthenticationFailureHandler();
    }

    @Bean
    public AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler() {
        return new AjaxLogoutSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Create an in-memory authentication manager. We create 1 user (localhost which
     * is the CN of the client certificate) which has a role of USER.
     */
    /*
     * public void configure(AuthenticationManagerBuilder auth) throws Exception {
     * auth.inMemoryAuthentication().withUser("localhost").password("none").roles(
     * "USER"); }
     */

    @Override
    public void configure(WebSecurity web) {
        System.err.println("pass par la");

        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").antMatchers("/app/**/*.{js,html}").antMatchers("/i18n/**")
                .antMatchers("/content/**").antMatchers("/swagger-ui/index.html").antMatchers("/test/**");
    }

  /*  @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
    /*    http
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
            .addFilterBefore(corsFilter, CsrfFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(problemSupport)
            .accessDeniedHandler(problemSupport)
        .and()
            .rememberMe()
            .rememberMeServices(rememberMeServices)
            .rememberMeParameter("remember-me")
            .key(jHipsterProperties.getSecurity().getRememberMe().getKey())
        .and()
            .formLogin()
            .loginProcessingUrl("/api/authentication")
            .successHandler(ajaxAuthenticationSuccessHandler())
            .failureHandler(ajaxAuthenticationFailureHandler())
            .permitAll()
        .and()
            .logout()
            .logoutUrl("/api/logout")
            .logoutSuccessHandler(ajaxLogoutSuccessHandler())
            .permitAll()
        .and()
            .headers()
            .contentSecurityPolicy("default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:")
        .and()
            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
        .and()
            .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
        .and()
            .frameOptions()
            .deny()*/

           /* http
            .authorizeRequests()
                .anyRequest()
                    .authenticated()
            .and()
                .x509()
            .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.NEVER)
            .and()
                .csrf()
                    .disable(); */         /*  .authorizeRequests()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/account/reset-password/init").permitAll()
            .antMatchers("/api/account/reset-password/finish").permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN);*/
        // @formatter:on
    /* } */

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().anyRequest().authenticated().and().x509().subjectPrincipalRegex("CN=(.*?)(?:,|$)")
                .userDetailsService(this.userDetailsService).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER).and().csrf().disable();
    }

    /*
     * @Bean public UserDetailsService userDetailsService() { return new
     * UserDetailsService() {
     *
     * @Override public UserDetails loadUserByUsername(String username) {
     * System.out.println("err"); if (username.equals("cid")) { return new
     * UserDetail(username, "",
     * AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER")); } else
     * return null; } }; }
     *
     * class UserDetail implements UserDetails {
     *
     * private static final long serialVersionUID = 1L; String username; String
     * password; List<GrantedAuthority> commaSeparatedStringToAuthorityList;
     *
     * public UserDetail(String username, String password, List<GrantedAuthority>
     * commaSeparatedStringToAuthorityList) { this.username = username;
     * this.password = password; this.commaSeparatedStringToAuthorityList =
     * commaSeparatedStringToAuthorityList;
     *
     * }
     *
     * @Override public Collection<? extends GrantedAuthority> getAuthorities() {
     * return commaSeparatedStringToAuthorityList; }
     *
     * @Override public String getPassword() { return password; }
     *
     * @Override public String getUsername() { return this.username; }
     *
     * @Override public boolean isAccountNonExpired() { return true; }
     *
     * @Override public boolean isAccountNonLocked() { return true; }
     *
     * @Override public boolean isCredentialsNonExpired() { return true; }
     *
     * @Override public boolean isEnabled() { return true; }
     *
     * }
     */
}
