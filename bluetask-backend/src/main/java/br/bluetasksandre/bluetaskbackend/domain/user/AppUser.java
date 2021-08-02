package br.bluetasksandre.bluetaskbackend.domain.user;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity //Permite essa classe ser mapeada para uma tabela do BD
@Table(name = "app_user") //Informa qual será o nome da tabela no BD
public class AppUser implements Serializable {

    @Id //Indica que esse atributo será o Id na tabela do BD
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Gera automaticamente o valor do Id, incrementando-o
    private Integer id;
    @NotEmpty(message = "O nome é obrigatório")
    private String username;
    @NotEmpty(message = "A senha é obrigatória")
    private String password;
    @NotEmpty(message = "O nome de exibição é obrigatório")
    private String displayName;

    public AppUser() {
    }

    public AppUser(String username, String password, String displayName) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
