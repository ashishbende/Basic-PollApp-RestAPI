package vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/*
 * @author Ashish Bende
 */

@Configuration
@ComponentScan
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class AppAuthentication extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
		.httpBasic().and()
		.csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/api/v1/").permitAll()
		.antMatchers("/api/v1/polls/*").permitAll()
		.antMatchers(HttpMethod.POST,"/api/v1/moderators/").permitAll()
		.antMatchers("/api/v1/moderators/*").fullyAuthenticated().anyRequest().hasRole("USER");
	}
	
	@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth
			.inMemoryAuthentication()
				.withUser("foo").password("bar").roles("USER");
	}
	
}
