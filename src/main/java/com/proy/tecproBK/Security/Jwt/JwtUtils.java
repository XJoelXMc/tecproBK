package com.proy.tecproBK.Security.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {
    //configurar clave secreta, creacion de metodos generadores de tokens, tambien para validar los tokems,tiempo de expiracion
    //validadion del token del usuario
    @Value("${jwt.secret.key}")
    private String csecreta;
    @Value("${jwt.time.expiration}")
    private String expiracion;

    //Generacion del token
    public String GenerateAccessToken(String username) {
        return Jwts.builder().setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expiracion))).signWith(firmatoken(), SignatureAlgorithm.HS256).compact();
    }

    public Key firmatoken() {
        //decodificamos la clave del token
        byte[] clave = Decoders.BASE64.decode(csecreta);
        return Keys.hmacShaKeyFor(clave);
    }

    //Vaidacion del token de acceso

    public boolean tokenvalidator(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(firmatoken()).build().parseClaimsJws(token).getBody();
            return true;
        }
        catch (Exception es){
            log.error(("Token invalido".concat(es.getMessage())));
            return false;
        }
    }


       /*obtener todoel cuerpo del token*/
    public Claims obtenerClaims(String token){
        return Jwts.parserBuilder().setSigningKey(firmatoken()).build().parseClaimsJws(token).getBody();
    }
    //Segmentamos la partes del token
    public <T> T obtenerClaim(String token, Function<Claims,T> claimsTFunction) {
        Claims datos= obtenerClaims(token);
        return  claimsTFunction.apply(datos);
    }
    public String obtenerNombreUsuario(String token){
        return obtenerClaim(token,Claims::getSubject);
    }

}
