package br.bluetasksandre.bluetaskbackend.domain.task;

import br.bluetasksandre.bluetaskbackend.domain.user.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity //Permite essa classe ser mapeada para uma tabela do BD
@EntityListeners(TaskListener.class)
public class Task implements Serializable {

    @Id //Indica que esse atributo será o Id na tabela do BD
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Gera automaticamente o valor do Id, incrementando-o
    private Integer id;
    @NotEmpty(message = "A descrição da tarefa é obrigatória")
    @Length(min = 3, max = 40, message = "Tamanho invalido")
    private String description;
    @NotNull(message = "A data da tarefa é obrigatória")
    @FutureOrPresent(message = "A data não pode ser uma antiga")
    private LocalDate whenTodo;
    private Boolean done = false;
    @ManyToOne //Dono do relacionamento e o lado Many. Task é o many, pois o usuario pode ter varias tarefas
    @JoinColumn(name = "app_user_id") //Nome da coluna na tabela.
    //@NotNull(message = "O usuario da tarefa é obrigatório")
    @JsonIgnore
    private AppUser appUser;

    public Task() {
    }

    public Task(String description, LocalDate whenTodo, Boolean done) {
        this.description = description;
        this.whenTodo = whenTodo;
        this.done = done;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getWhenTodo() {
        return whenTodo;
    }

    public void setWhenTodo(LocalDate whenTodo) {
        this.whenTodo = whenTodo;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
