package br.bluetasksandre.bluetaskbackend.domain.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component //Permite que o Spring gerencie objetos dessa classe de maneira automatica
@RepositoryEventHandler //Essa anotação trata eventos de repositorio
public class TaskRepositoryEventHendler {

    private TaskRepository taskRepository;

    @Autowired
    public TaskRepositoryEventHendler(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /*Método chamado quando ocorre um evento no Repositorio
    *
    * Metodo verifica se já existe uma Task salva no banco de dados com a mesma descrição
    * da que foi passado como parâmetro.
    * Também verifica se o ID da Task passado como parâmetro já existe no BD, indicando uma atualização na Task
    * */
    @HandleBeforeSave //Anotação que indica que o método deve ser chamado antes de um "PUT" no repositorio
    @HandleBeforeCreate //Anotação que indica que o método deve ser chamado antes de um "POST" no repositorio
    public void handle(Task task) throws DuplicatedTaskException{
        Task taskDb = taskRepository.findByDescription(task.getDescription());
        boolean duplicate = false;
        if (taskDb != null){
            if ((task.getId() == null || task.getId() == 0) && task.getDescription().equals(taskDb.getDescription())){
                duplicate = true;
            }
            if ((task.getId() != null && task.getId() > 0) && !task.getId().equals(taskDb.getId())){
                duplicate = true;
            }
        }
        if (duplicate){
            throw  new DuplicatedTaskException("Já existe uma tarefa com esta descrição");
        }
    }
}
