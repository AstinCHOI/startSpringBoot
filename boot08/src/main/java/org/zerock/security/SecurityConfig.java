package org.zerock.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    ZerockUsersService zerockUsersService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("security config...............");

        http.authorizeRequests().antMatchers("/guest/**").permitAll();
        http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");
        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");

        http.formLogin().loginPage("/login");
        http.exceptionHandling().accessDeniedPage("/accessDenied");
        // 세션 무효화
        http.logout().logoutUrl("/logout").invalidateHttpSession(true);

        // http.userDetailsService(zerockUsersService);

        // rememberMe의 기능은 default로 Hash-based token 저장 방식을 사용한다 - 패스워드가 변경되더라도 hash 그대로 사용 가능
        // http.rememberMe().key("zerock").userDetailsService(zerockUsersService);

        // persistent token 저장 방식으로 사용한다.
        http.rememberMe()
                .key("zerock")
                .userDetailsService(zerockUsersService)
                .tokenRepository(getJDBCRepository())
                .tokenValiditySeconds(60 * 60 * 24);
    }

    private PersistentTokenRepository getJDBCRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        log.info("builder Auth global............");
        auth.userDetailsService(zerockUsersService).passwordEncoder(passwordEncoder());
    }

//    https://www.harinathk.com/spring/no-passwordencoder-mapped-id-null/
//    @SuppressWarnings("deprecation")
//    @Bean
//    public static NoOpPasswordEncoder passwordEncoder() {
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        log.info("builder Auth global............");
//
//        String query1 =
//                "SELECT uid username, upw password, true enabled FROM tbl_members WHERE uid=?";
//        String query2 =
//                "SELECT member uid, role_name role FROM tbl_member_roles WHERE member=?";
//
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery(query1)
//                .rolePrefix("ROLE_")
//                .authoritiesByUsernameQuery(query2);
//    }


//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        log.info("builder Auth global............");
//
//        auth.inMemoryAuthentication()
//                .withUser("manager")
//                .password("1111")
//                .roles("MANAGER");
//    }
}
