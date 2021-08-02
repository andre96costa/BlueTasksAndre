package br.bluetasksandre.bluetaskbackend.infrastructure.web;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

/*Classe para manipulção de erros de validação
*
*
* */

public class RestResponseError {

    private String error;

    private RestResponseError() {
    }

    public String getError() {
        return error;
    }

    /*Método formata os erros de validação,
    *
    * Recebe como parâmetro a interface "Errors" que contem todos os erros de validação
    * Formatamos todos os erros recebidos em uma String única, facilitando a leitura
    * e a manipulaçao pelo frontEnd
    *
    * */
    public static RestResponseError fromValidationErrors(Errors error) {
        RestResponseError resp = new RestResponseError();
        StringBuilder sb = new StringBuilder();
        for (ObjectError err : error.getAllErrors()){
            sb.append(err.getDefaultMessage()).append(". ");
        }
        resp.error = sb.toString();
        return resp;
    }

    /*Método formata os de excessão
    *
    * Recebe a messagem de erro da excessão e retorna a penas a menssagem
    * */
    public static RestResponseError fromMessagem(String message){
        RestResponseError resp = new RestResponseError();
        resp.error = message;
        return resp;
    }
}
