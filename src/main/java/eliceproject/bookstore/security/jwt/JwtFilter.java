package eliceproject.bookstore.security.jwt;

import eliceproject.bookstore.user.User;
import eliceproject.bookstore.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        System.out.println("JwtFilter.doFilterInternal");
        logger.info("1");
        if(authorizationHeader == null){
            filterChain.doFilter(request, response);
            return;
        }

        logger.info("2");
        if(!authorizationHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
        }

        String token = authorizationHeader.split(" ")[1];

        logger.info("3");
        if(JwtUtil.isExpired(token)){
            filterChain.doFilter(request, response);
        }

        String username = JwtUtil.getUsername(token);

        logger.info("4");
        User user = userRepository.findByUsername(username);
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        logger.info("4");
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
