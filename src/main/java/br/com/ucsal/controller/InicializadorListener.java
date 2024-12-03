package br.com.ucsal.controller;

import br.com.ucsal.persistencia.MemoriaProdutoRepository;
import br.com.ucsal.util.SingletonLoader;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/*Ao listener foi adicionada a chamada do método loadSingletons() do objeto SingletonLoader, responsável por implementar um container de singletons, 
garantindo que apenas uma instância de cada classe anotada com @Singleton exista durante a execução do programa. Ainda há um pequeno teste para ter
certeza que, ao criar dois repositórios em memória, ambos apontam para a mesma instância.*/

@WebListener
public class InicializadorListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SingletonLoader.loadSingletons();
        
        MemoriaProdutoRepository repo1 = SingletonLoader.getSingleton(MemoriaProdutoRepository.class);
        MemoriaProdutoRepository repo2 = SingletonLoader.getSingleton(MemoriaProdutoRepository.class);

        System.out.println(repo1 == repo2); 
    	System.out.println("Inicializando recursos na inicialização da aplicação");
    }


}
