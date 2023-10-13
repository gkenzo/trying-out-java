package br.com.helloworld.todolist.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.helloworld.todolist.user.IUserRepository;
import br.com.helloworld.todolist.user.UserModel;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  @Autowired
  IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String filteredRoutes = "/tasks/";
    Boolean doesRequestComeFromFilteredRoute = request.getServletPath().startsWith(filteredRoutes);

    if (!doesRequestComeFromFilteredRoute) {
      filterChain.doFilter(request, response);
      return;
    }

    this.handleUserAuth(request, response);

    filterChain.doFilter(request, response);
  }

  private void handleUserAuth(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String authDecoded = this.decodeBasicAuth(request.getHeader("Authorization"));
    String[] userCredentials = authDecoded.split(":");
    Boolean isAuthenticated = this.doesUserAuthenticate(userCredentials);

    if (!isAuthenticated) {
      response.sendError(HttpStatus.UNAUTHORIZED.value(), authDecoded);
      return;
    }

    UserModel user = getUserByUsername(userCredentials[0]);
    request.setAttribute("userId", user.getId());
  }

  private String decodeBasicAuth(String encodedAuthorization) {
    String[] auth = encodedAuthorization.split(" ");
    String authEncode = auth[1];
    return new String(Base64.getDecoder().decode(authEncode));
  }

  private UserModel getUserByUsername(String username) {
    UserModel user = this.userRepository.findByUsername(username);

    return user;
  }

  private Boolean doesUserAuthenticate(String[] userCredentials) {
    String username = userCredentials[0];
    String password = userCredentials[1];
    UserModel user = getUserByUsername(username);

    if (user == null)
      return false;

    return (BCrypt.verifyer().verify(password.toCharArray(), user.getPassword())).verified;
  }

}
