package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    public static final String DEF_USERS_BY_USERNAME_QUERY =
//            "select username, password, enabled " +
//                    "from users" +
//
//    public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY =
//            "select username, authority " +
//                    "from authorities " +
//                    "where username = ?";
//
//    public static final String DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY =
//            "select g.id, g.group_name, ga.authority " +
//                    "from groups g, group_member gm, group_authorities ga " +
//                    "where gm.username = ? " +
//                    "and g.id = ga.group_id " +
//                    "and g.id = gm.group_id";

//    @Autowired
//    DataSource dataSource;

    private final UserDetailsService userDetailsService;

    @Autowired
    SecurityConfig(@Qualifier("userDetailServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    protected PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(encoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/design", "/orders")
                .hasAnyAuthority(Permission.DEVELOPERS_READ.getPermission(),
                        Permission.DEVELOPERS_WRITE.getPermission())
                .antMatchers("/", "/**").permitAll()

                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/home")

                .and()
                .logout()
                .logoutSuccessUrl("/home");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(daoAuthenticationProvider());


//                .jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery(DEF_USERS_BY_USERNAME_QUERY)
//                .authoritiesByUsernameQuery(DEF_AUTHORITIES_BY_USERNAME_QUERY);

        /** Using In-Memory user store**/
//                .inMemoryAuthentication()
//                .withUser("buzz")
//                .password("infinity")
//                .authorities("ROLE_USER")
//                .and()
//                .withUser("wooby")
//                .password("bullsey")
//                .authorities("ROLE_USER");
    }
}
