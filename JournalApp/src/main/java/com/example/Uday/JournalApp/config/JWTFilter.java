package com.example.Uday.JournalApp.config;

import com.example.Uday.JournalApp.Services.JWTService;
import com.example.Uday.JournalApp.Services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    JWTService jwtService;
    @Autowired
    ApplicationContext context;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
String authHeader=request.getHeader("Authorization");
String token=null;
String userName=null;
if(authHeader!=null && authHeader.startsWith("Bearer ")){
    token=authHeader.substring(7);
    userName=jwtService.extractUserName(token);

}

if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
    UserDetails userDetails=context.getBean(UserDetailsServiceImpl.class).loadUserByUsername(userName);
    if(jwtService.validateToken(token,userDetails)){
        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        System.out.println("In JWT:"+userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

    }
}
filterChain.doFilter(request,response);
    }
}
