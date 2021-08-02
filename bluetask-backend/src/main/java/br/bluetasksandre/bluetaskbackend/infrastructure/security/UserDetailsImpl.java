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

import java.util.Collection;

import br.bluetasksandre.bluetaskbackend.domain.user.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;


/*
* Classe que representa um usuario logado, precissa implementar a interface UserDetails
*	Essa classe é instanciada pela classe "UserDetailsServiceImpl"
*	É precisso seguir o padrão, com os metodos do UserDetails, pois quem ira usar os metodos sera o Spring
*
* */

@SuppressWarnings("serial")
public class UserDetailsImpl implements UserDetails {

	private String username;
	private String password;
	private String displayName;
	
	public UserDetailsImpl(AppUser appUser) {
		this.username = appUser.getUsername();
		this.password = appUser.getPassword();
		this.displayName = appUser.getDisplayName();
	}

	//Pega as roles do usuario, o usuario pode ter varias roles.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.NO_AUTHORITIES;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	//Se a conta não está expirada
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//Se a conta não esta bloqueada
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	//Se as credencias não estão expiradas
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	//Se a conta está ativada
	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getDisplayName() {
		return displayName;
	}

}
