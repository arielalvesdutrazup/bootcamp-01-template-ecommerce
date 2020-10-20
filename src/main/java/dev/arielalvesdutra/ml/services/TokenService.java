package dev.arielalvesdutra.ml.services;

import dev.arielalvesdutra.ml.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

// 1 Usuario.java
// 2 try
// 3 catch

// 4 if (!ehValidoFormatoDoCabecalhoDeAutorizacaoDaRequisicao(cabecalhoAuthorization)) {
// 5 if (cabecalhoAuthorization == null
@Service
public class TokenService {

    @Value("${token.segredo:j88saS1swYbOrmkOEjiOZVXIHlo44S}")
    private String SEGREDO;

    @Value("${token.expiracao:86400000}")
    private Long EXPIRACAO;

    public String geraToken(Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

        return geraToken(usuarioLogado);
    }

    public String geraToken(Usuario usuario) {

        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + EXPIRACAO);

        return Jwts.builder()
                .setIssuer("Desafio Mercado Livre")
                .setSubject(usuario.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, SEGREDO)
                .compact();
    }

    public boolean ehTokenValido(String token) {
        try {
            Jwts.parser().setSigningKey(SEGREDO).parseClaimsJws(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    // @todo Refatorar nome
    // @todo Remover duplicação do substring
    public Long getUsuarioIdDoCabecalho(String cabecalho) {
        return getUsuarioId(cabecalho.substring(7, cabecalho.length()));
    }

    public Long getUsuarioId(String token) {
        Claims claims = Jwts.parser().
                setSigningKey(SEGREDO)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    /**
     * Extrai o token do cabeçalho Authorization da requisição.
     *
     * @param request
     * @return
     */
    public String recuperaToken(HttpServletRequest request) {
        String cabecalhoAuthorization = request.getHeader("Authorization");

        if (!ehValidoFormatoDoCabecalhoDeAutorizacaoDaRequisicao(cabecalhoAuthorization)) {
            return null;
        }

        return cabecalhoAuthorization.substring(7, cabecalhoAuthorization.length());
    }

    /**
     * Verifica se o valor do cabeçalho segue o formator 'Bearer'.
     *
     * Retorna verdadeiro o caso cabeçalho seja valido.
     *
     * @param cabecalhoAuthorization
     * @return
     */
    private Boolean ehValidoFormatoDoCabecalhoDeAutorizacaoDaRequisicao(String cabecalhoAuthorization) {
        if (cabecalhoAuthorization == null
                || cabecalhoAuthorization.isEmpty()
                || !cabecalhoAuthorization.startsWith("Bearer ")) {
            return false;
        }
        return true;
    }
}
