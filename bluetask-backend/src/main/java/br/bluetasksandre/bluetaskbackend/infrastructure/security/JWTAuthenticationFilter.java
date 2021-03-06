/*******************************************************************************
 *
 * MIT License
 *
 * Copyright (c) 2020 Softblue
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/
package br.bluetasksandre.bluetaskbackend.infrastructure.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.bluetasksandre.bluetaskbackend.domain.user.AppUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
*Classe que cria o JWT para autentica????o do usuario
* herda da classe "UsernamePasswordAuthenticationFilter" que ?? um filtro padr??o do spring Security
* essa classe adiciona o endpoint "/login" por padr??o na aplica????o, e permite enviar requisi??oes via POST
*
*
*
* */

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	/*Essa interface faz o processo de autentica????o
	* valida se a autentica????o deu certo ou n??o
	* */
	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/*M??todo que processa uma tentativa de autentica????o
	* M??todo ?? chamado automaticamente quando o "/login".
	* verifica os dados de usuario s??o validos.
	*
	* recebe como param??tros o request( formato JSON)
	*
	* caso o retorno "authenticationManager.authenticate(upat)" de certo
	* a classe "JWTAuthenticationFilter" ?? invocada novamente chamando o m??todo "successfulAuthentication"
	* */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			ObjectMapper mapper = new ObjectMapper(); //Classe que transforma JSON em classe ou Classe em JSON
			AppUser appUser = mapper.readValue(request.getInputStream(), AppUser.class);
			
			UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword());
			return authenticationManager.authenticate(upat);
		
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/*M??todo chamado quando a autentica????o ?? feita com sucesso
	* Esse m??todo cria um Token JWT ap??s a autentica????o e envia
	* o token para o frontend atraves do header.
	*
	* */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		//Pega o usuario que j?? est?? autenticado
		UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
		
		String jwtToken = Jwts.builder()
			.setSubject(userDetails.getUsername())
			.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
			.claim("displayName", userDetails.getDisplayName())
			.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET_KEY)
			.compact();
		
		response.addHeader(SecurityConstants.AUTHORIZATION_HEADER, SecurityConstants.TOKEN_PREFIX + jwtToken);
	}
}
