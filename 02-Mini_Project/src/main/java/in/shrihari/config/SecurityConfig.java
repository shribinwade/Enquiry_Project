package in.shrihari.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	HttpSecurity  m;
	  @Bean
	  SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
	    return http
	      .requiresChannel(channel -> 
	          channel.anyRequest().requiresSecure())
	      .authorizeRequests(authorize ->
	          authorize.anyRequest().permitAll())
	      .build();
	    }
}
