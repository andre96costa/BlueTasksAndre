package br.bluetasksandre.bluetaskbackend.infrastructure.web;

import br.bluetasksandre.bluetaskbackend.domain.task.DuplicatedTaskException;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/*Classe que trata erros de requisições rest
*
*
* */


@RestControllerAdvice //Essa anotação faz com que essa classe seja auto-detectada pelo Spring para tratar excessões, lançadas pela anotation @ExceptionHandler
public class WebRequestExceptionHandler {

    /*Método chamado quando ocorre uma "RepositoryConstraintViolationException"
    *
    *Esse método retorna a classe "RestResponseError" que manipula a excessão "RepositoryConstraintViolationException"
    * formatando a saída, tornando há amigavel para leitura
    * */
    @ExceptionHandler //Essa anotation indica que esse método deve ser chamado quando uma "RepositoryConstraintViolationException" for lançada
    @ResponseStatus(code = HttpStatus.BAD_REQUEST) //Essa anotation indica qual o return code
    public RestResponseError handleException(RepositoryConstraintViolationException e){
        return RestResponseError.fromValidationErrors(e.getErrors());
    }

    /*Método chamado quando ocorre uma "DuplicatedTaskException"
    *
    * Esse método retorna a classe "RestResponseError" que manipula a excessão "DuplicatedTaskException"
     * formatando a saída, tornando há amigavel para leitura
    * */
    @ExceptionHandler //Essa anotation indica que esse método deve ser chamado quando uma "DuplicatedTaskException" for lançada
    @ResponseStatus(code = HttpStatus.BAD_REQUEST) //Essa anotation indica qual o return code
    public RestResponseError handleException(DuplicatedTaskException e){
        return RestResponseError.fromMessagem(e.getMessage());
    }
}
