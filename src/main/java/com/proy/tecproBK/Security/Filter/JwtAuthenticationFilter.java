package com.proy.tecproBK.Security.Filter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proy.tecproBK.Security.Jwt.JwtUtils;
import com.proy.tecproBK.model.usuariosModel;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//importanmos JwtUtils

        private JwtUtils jwtUtils;
        public JwtAuthenticationFilter(JwtUtils jwtUtils){
            this.jwtUtils=jwtUtils;
        }

        /**
         * metodo que viene en spring security , cuando se intenta authenticate
         * @param request devuelve el usuario que se intento authenticate

         * @return retorna si se authentico
         * @throws AuthenticationException
         */
        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

            usuariosModel userEntity=null;
            String usuario="";
            String contrasenia="";
            try {
                userEntity = new ObjectMapper().readValue(request.getInputStream(), usuariosModel.class);//toma los parametros usuario y contrasenia y mapea
                usuario=userEntity.getUsuario();
                contrasenia=userEntity.getContrasenia();
            } catch (StreamReadException e) {
                throw new RuntimeException(e);
            } catch (DatabindException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(usuario, contrasenia);

            return getAuthenticationManager().authenticate(authenticationToken);
        }

        @Override
        protected void successfulAuthentication(HttpServletRequest request,
                                                HttpServletResponse response,
                                                FilterChain chain,
                                                Authentication authResult) throws IOException, ServletException {
            User user=(User) authResult.getPrincipal();

            String token = jwtUtils.GenerateAccessToken(user.getUsername());//genramos el token para dar acceso a los endpoint protegidos
            response.addHeader("Authorization", token);
            Map<String, Object> httpResponse=new HashMap<>();
            httpResponse.put("token",token);
            httpResponse.put("mensaje","Authentication correcta");
            httpResponse.put("Usuario",user.getUsername());
            response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().flush();

            super.successfulAuthentication(request, response, chain, authResult);
        }

}
