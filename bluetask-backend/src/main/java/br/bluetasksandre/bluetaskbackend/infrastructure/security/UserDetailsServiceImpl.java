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

import br.bluetasksandre.bluetaskbackend.domain.user.AppUser;
import br.bluetasksandre.bluetaskbackend.domain.user.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 * Classe responsavél por buscar o usuario que realizou o login na aplicação.
 * A anotação @Service faz com que a classe seja chamada automaticamente
 * durante o processo de autenticação.
 *
 * */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private AppUserRepository appUserRepository;

	@Autowired
	public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}

	/*Método chamado automaticamente durante a autenticação
	*
	* Busca o usuario no banco de dados
	*
	* retorna uma nova instancia de "UserDetailsImpl" passando o usuario buscado no BD como parâmetro
	* */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = appUserRepository.findByUsername(username);
		
		if (appUser == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new UserDetailsImpl(appUser);
	}
}
