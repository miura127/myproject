package com.miura.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private DataSource dataSource;

	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/css/**", "/webjars/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
			.antMatchers("/css/**").permitAll()
			.antMatchers("/webjars/**").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/signup").permitAll()
			.antMatchers("/userList").hasAuthority("ROLE_ADMIN")
			.antMatchers("/userDetail").hasAuthority("ROLE_ADMIN")
			.anyRequest().authenticated();

		http.formLogin()
			.loginProcessingUrl("/login")
			.loginPage("/login")
			.failureUrl("/login")
			.usernameParameter("userId")
			.passwordParameter("password")
			.defaultSuccessUrl("/home", true);

		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login");
		}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("SELECT user_id, password, true FROM m_user WHERE user_id = ?")
			.authoritiesByUsernameQuery("SELECT user_id, role FROM m_user WHERE user_id = ?")
			.passwordEncoder(passwordEncoder());
	}
}
