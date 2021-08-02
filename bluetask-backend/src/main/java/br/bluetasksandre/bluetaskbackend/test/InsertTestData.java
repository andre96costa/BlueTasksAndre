package br.bluetasksandre.bluetaskbackend.test;

import br.bluetasksandre.bluetaskbackend.domain.task.Task;
import br.bluetasksandre.bluetaskbackend.domain.task.TaskRepository;
import br.bluetasksandre.bluetaskbackend.domain.user.AppUser;
import br.bluetasksandre.bluetaskbackend.domain.user.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component //Essa anotação faz com que o Spring gerencie essa classe de maneira autonoma
public class InsertTestData {

    private TaskRepository taskRepository;
    private AppUserRepository appUserRepository;

    @Autowired //Essa é a Injeção de dependencia através do construtor
    public InsertTestData(TaskRepository taskRepository, AppUserRepository appUserRepository) {
        this.taskRepository = taskRepository;
        this.appUserRepository = appUserRepository;
    }


    /*Métoque que adiciona dados ao BD quando o servidor e iniciado.
    *
    * Esse método recebe como parâmetro o evento "ContextRefreshedEvent"
    * esse evento é disparado quando há um "fresh" na aplicação, esse evento dispara a
    * anotação "EventListener" que dispara o método "onApplicationEvent"
    * */
    @EventListener //Essa anotação é chamada quando um evento ocorre.
    public void onApplicationEvent(ContextRefreshedEvent event){
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        AppUser appUser = new AppUser("john", encoder.encode("abc"), "John Coder");
        AppUser appUser2 = new AppUser("Melinda", encoder.encode("abc"), "Melinda frotend");
        appUserRepository.save(appUser);
        appUserRepository.save(appUser2);

        LocalDate baseDate = LocalDate.parse("2025-02-01");
        Task task1 = new Task("Tarefa #1", baseDate.plusDays(1), false);
        task1.setAppUser(appUser);
        Task task2 = new Task("Tarefa #2", baseDate.plusDays(2), false);
        task2.setAppUser(appUser);
        Task task3 = new Task("Tarefa #3", baseDate.plusDays(3), false);
        task3.setAppUser(appUser);
        Task task4 = new Task("Tarefa #4", baseDate.plusDays(4), false);
        task4.setAppUser(appUser);
        Task task5 = new Task("Tarefa #5", baseDate.plusDays(5), false);
        task5.setAppUser(appUser2);
        Task task6 = new Task("Tarefa #6", baseDate.plusDays(6), false);
        task6.setAppUser(appUser2);
        Task task7 = new Task("Tarefa #7", baseDate.plusDays(7), false);
        task7.setAppUser(appUser2);
        Task task8 = new Task("Tarefa #8", baseDate.plusDays(8), false);
        task8.setAppUser(appUser);

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);
        taskRepository.save(task4);
        taskRepository.save(task5);
        taskRepository.save(task6);
        taskRepository.save(task7);
        taskRepository.save(task8);
    }

}
