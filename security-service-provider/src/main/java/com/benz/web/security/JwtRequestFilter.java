package com.benz.web.security;

import com.benz.web.logger.Logger;
import com.benz.web.services.UserDetailsImpl;
import com.benz.web.services.UserDetailsServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

     @Autowired
     private UserDetailsServiceImpl userDetailsService;

     @Autowired
     private JwtUtil jwtUtil;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            final String authorizationHeader = request.getHeader("Authorization");
            
            System.out.println(authorizationHeader);
                        
            String jwt = null;
            String userName = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Benz ")) {
            	
                jwt = authorizationHeader.substring(5);
                
                System.out.println(jwt);
              
                userName = jwtUtil.extractUserName(jwt);
                
                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                	
                    UserDetailsImpl userDetails = (UserDetailsImpl) this.userDetailsService.loadUserByUsername(userName);

                    
                    if (jwtUtil.validateToken(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }
        }catch (Exception ex)
        {
            Logger.ERROR_LOGGER.error("Can not set user authentication ",ex);
        }
        filterChain.doFilter(request,response);
    }
}
