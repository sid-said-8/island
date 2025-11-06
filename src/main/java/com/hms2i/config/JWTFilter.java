package com.hms2i.config;

import com.hms2i.entity.Islanduser;
import com.hms2i.repository.IslanduserRepository;
import com.hms2i.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter{

    private JWTService jwtService;
    private final IslanduserRepository islanduserRepository;

    public JWTFilter(JWTService jwtService,
                     IslanduserRepository islanduserRepository) {
        this.jwtService = jwtService;
        this.islanduserRepository = islanduserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("authentication");
        if(token!=null && token.startsWith("Bearer ")){
            String tokenVal = token.substring(8, token.length() - 1);
            String username = jwtService.getUsername(tokenVal);

            Optional<Islanduser> opUser = islanduserRepository.findByUsername(username);
            if(opUser.isPresent()){
                Islanduser islanduser = opUser.get();
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(islanduser,null,null);
                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }filterChain.doFilter(request,response);

    }

}
