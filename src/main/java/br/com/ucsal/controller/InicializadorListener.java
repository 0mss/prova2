package br.com.ucsal.controller;

import br.com.ucsal.persistencia.MemoriaProdutoRepository;
import br.com.ucsal.util.SingletonLoader;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

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