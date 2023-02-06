//package LeiYang.Util;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.header.Header;
//import org.springframework.security.web.header.writers.StaticHeadersWriter;
//
//import java.util.Arrays;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
//
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity.authorizeRequests()
//                .antMatchers("/login/**").permitAll()  // 所有login请求无需验证
////                .antMatchers("/login/**").hasAnyRole() ;
//                .anyRequest().authenticated();  // 其余请求都需要过滤
//
//        httpSecurity.formLogin()
//                .loginPage("/login"); // 设置跳转登录页面
//
//        httpSecurity.csrf().disable();  // CRSF禁用，因为不使用session 可以预防CRSF攻击
//
//        httpSecurity.sessionManagement().disable(); // 禁用session
//
//        httpSecurity.cors(); // 支持跨域
//
//
//    }
//
//}
