package kz.iitu.demo.config;

import kz.iitu.demo.service.impl.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MemberServiceImpl memberService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().
                    disable().
                    authorizeRequests().
                    antMatchers("/auth/**").permitAll().
                    antMatchers("/books/**").permitAll().
                    antMatchers("/members/issues").hasAuthority("USER").
                    antMatchers("/members").hasAuthority("ADMIN").
                    antMatchers("/issues").hasAuthority("ADMIN").
                    antMatchers("/members/register").hasAuthority("ADMIN").anyRequest().authenticated().
                    and().addFilter(new JwtTokenGeneratorFilter(authenticationManager())).
                    addFilterAfter(new JwtTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
