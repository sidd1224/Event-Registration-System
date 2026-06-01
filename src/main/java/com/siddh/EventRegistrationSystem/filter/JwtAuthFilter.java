package com.siddh.EventRegistrationSystem.filter;

import com.siddh.EventRegistrationSystem.service.CustomUserDetailsService;
import com.siddh.EventRegistrationSystem.service.JwtAuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtAuthService jwtAuthService;

    private final CustomUserDetailsService service;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filter)
    throws ServletException, IOException {
        String authHeader =request.getHeader("Authorization");
        String jwt=null;
        String userName=null;
        if(authHeader !=null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                userName = jwtAuthService.extractUsername(jwt);
            } catch (Exception e) {
                logger.warn("Invalid JWT",e);
            }
        }
        if(userName!=null &&
                   SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=service.loadUserByUsername(userName);
                   Authentication auth=new UsernamePasswordAuthenticationToken(
                          userDetails,
                           null,
                           userDetails.getAuthorities()
                   );
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(auth);
               }
        filter.doFilter(request,response);
        }

    }

