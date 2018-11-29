package com.gazulabs.veterinaria.springboot.app;

import com.gazulabs.veterinaria.springboot.app.auth.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler successHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .antMatchers("/index/**").hasAnyRole("VETERINARIO", "ADMIN", "SECRETARIO")
                .antMatchers("/usuario/**").hasAnyRole("ADMIN")
                .antMatchers("/tipo-usuario/**").hasAnyRole("ADMIN")
                .antMatchers("/raza/**").hasAnyRole("ADMIN")
                .antMatchers("/especie/**").hasAnyRole("ADMIN")
                .antMatchers("/ficha-atencion/form/**").hasAnyRole("VETERINARIO", "ADMIN", "SECRETARIO")
                .antMatchers("/ficha-atencion/ver/**").hasAnyRole("VETERINARIO", "ADMIN", "SECRETARIO")
                .antMatchers("/ficha-atencion/lista-sala-espera").hasAnyRole("VETERINARIO", "ADMIN", "SECRETARIO")
                .antMatchers("/ficha-atencion/**").hasAnyRole("VETERINARIO", "ADMIN")
                .antMatchers("/atencion/**").hasAnyRole("VETERINARIO", "ADMIN")
                .antMatchers("/cliente/**").hasAnyRole("VETERINARIO", "ADMIN", "SECRETARIO")
                .antMatchers("/paciente/**").hasAnyRole("VETERINARIO", "ADMIN", "SECRETARIO")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(successHandler)
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error_403")
                .and()
                .sessionManagement().invalidSessionUrl("/login")
        ;
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {

        build.jdbcAuthentication().dataSource(dataSource)
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select username, password, eneable from users where username=?")
                .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on(a.user_id=u.id) where username=?");
      /*  User.UserBuilder users = User.withDefaultPasswordEncoder();
        build.inMemoryAuthentication()
                .withUser(users.username("admin").password("12345").roles("ADMIN", "VETERINARIO", "SECRETARIO"))
                .withUser(users.username("cfigueroa").password("12345").roles("VETERINARIO"))
                .withUser(users.username("rquezada").password("12345").roles("SECRETARIO"));*/

    }


}
