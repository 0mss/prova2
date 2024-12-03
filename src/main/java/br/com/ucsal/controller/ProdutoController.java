package br.com.ucsal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import br.com.ucsal.annotations.Rota;
import br.com.ucsal.commands.ProdutoListarCommand;
import br.com.ucsal.util.Injetor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/*ProdutoController agora tem o método carregarComandos(), que, utilizando reflections, inspeciona as classes Commands e mapeia elas com o devido comando num Map "commands".
No init() adicionamos a chamada do método injetarDependencias(), do objeto Injetor, passando o ProdutoController Isso permite que o Injetor processe o objeto 
ProdutoController e realize a injeção de dependências nos campos anotados com @Inject. A linha Injetor.injetarDependencias(command) é usada para configurar dinamicamente 
cada instância de command, garantindo que suas dependências sejam resolvidas antes de serem registradas e usadas no sistema. Isso elimina a necessidade de criar e configurar
manualmente os serviços e recursos em cada command, promovendo reutilização e modularidade.*/
@WebServlet("/view/*")  
public class ProdutoController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Map<String, Command> commands = new HashMap<>();
	private Command defaultCommand;
	

    @Override
    public void init() throws ServletException {
        super.init();
        
        try {
        	Injetor.injetarDependencias(this);
        	carregarComandos();
        } catch (Exception e) {
            throw new ServletException("Erro ao inicializar comandos", e);
        }
    }

	private void carregarComandos() throws Exception {
		Reflections reflections = new Reflections("br.com.ucsal.commands");
		Set<Class<?>> commandClasses = reflections.getTypesAnnotatedWith(Rota.class);

		for (Class<?> commandClass : commandClasses) {
			if (Command.class.isAssignableFrom(commandClass)) {
				Rota rota = commandClass.getAnnotation(Rota.class);
				Command command = (Command) commandClass.getDeclaredConstructor().newInstance();
				Injetor.injetarDependencias(command);
				commands.put(rota.value(), command);
				if ("/listar".equals(rota.value())) {
					defaultCommand = command;
				}
			}

		}
		if (defaultCommand == null) {
			defaultCommand = new ProdutoListarCommand(); 
			Injetor.injetarDependencias(defaultCommand);
		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path == null || path.equals("/")) {
            path = "/listar";  
        }

        Command command = commands.get(path);

        if (command != null) {
            command.execute(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Rota não encontrada");
        }
	}




}
