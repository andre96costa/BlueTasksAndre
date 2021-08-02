package br.bluetasksandre.bluetaskbackend;


import br.bluetasksandre.bluetaskbackend.domain.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


/*Classe inicial do projeto
* Nesse projeto usamos como classe de configuração para a aplicação
*
* A interface "RepositoryRestConfigurer" permite fazer configuração do Spring Data Rest
*
* */

@SpringBootApplication
public class BluetaskBackendApplication implements RepositoryRestConfigurer {
	private static final Logger logger = LoggerFactory.getLogger(BluetaskBackendApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BluetaskBackendApplication.class, args);
		logger.info("Bluetasks in action!");
	}

	/*Método que faz configurações no Spring Data Rest
	*
	* Faz com que o ID das entidades sejam mostradas na requisição.
	*Configura o CORS da applicação, permitindo que a aplicação possa ser acessada pelo frontEnd em qualquer dominio
	*
	*
	* */
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		config.exposeIdsFor(Task.class);
		cors.addMapping("/**") //Permite acesso a qualquer URL,
				.allowedOrigins("*") //Permite qualquer dominio externo acessar a aplicação
				.allowedMethods("GET", "POST", "PUT", "DELETE"); //Lista de metodos liberados
		logger.info("Repository CORS setup");
	}

	@Bean //Indica ao Spring qual metodo deve ser chamado quando for necessario instanciar um validator
	/*Método cria uma instancia de "Validator"
	*
	* Cria um arrays de errors, informando quais entidades, qual tipo de erro e qual mensagem de erro ocorreu,
	* esses errors estão relacionados as validações especificadas nas entidades.
	* */
	public Validator validator(){
		return new LocalValidatorFactoryBean();
	}

	/*Método que configura eventos de validação
	*
	* Configuramos o validator para ser chamado quand o evento "ValidatingRepositoryEventListener"
	* ocorrer
	*
	* O método configura que o evento deve ser chamado antes de um objeto ser criado no repositorio
	* e também antes de ser salvo no BD
	* */
	@Override
	public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
		Validator validator = this.validator();
		validatingListener.addValidator("beforeCreate", validator);
		validatingListener.addValidator("beforeSave", validator);

		logger.info("Validator ok!");
	}
}
