package com.reddit.config;


import com.reddit.config.security.admin.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JwtFilter extends OncePerRequestFilter {
    private final static Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    //private final TokenBlacklistService tokenBlacklistService;


    @Autowired
    public JwtFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = jwtUtil.extractTokenFromRequest(request);
            if (StringUtils.isNotEmpty(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                //extract the username from token
                String userName = jwtUtil.extractUsername(token);
                //String userType = jwtUtil.extractUserTypeFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                //this.writeErrorResponse("Invalid Token",response);
                //return;
            }
            filterChain.doFilter(request, response);
       // }catch(JwtException e){
            SecurityContextHolder.clearContext();
            //this.writeErrorResponse("Invalid Token",response,HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            //this.writeErrorResponse("Unknown error has occurred",response,HttpStatus.INTERNAL_SERVER_ERROR);
            LOGGER.error("Unknown error", e);
        }
    }

}