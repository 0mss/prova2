package br.com.ucsal.util;

import java.lang.reflect.Field;

import br.com.ucsal.annotations.Inject;
import br.com.ucsal.persistencia.PersistenciaFactory;
import br.com.ucsal.persistencia.ProdutoRepository;
import br.com.ucsal.service.ProdutoService;


/* Classe utilitária responsável por injetar dependências em objetos cujos campos estão 
  anotados com @Inject. Utiliza reflexão para localizar os campos e resolve as dependências 
  com base no tipo esperado. Atualmente, suporta a injeção de ProdutoService, 
  que é criado com um ProdutoRepository obtido via PersistenciaFactory. */
public class Injetor {

    public static void injetarDependencias(Object objeto) {
        Class<?> clazz = objeto.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                Object dependencia = obterDependencia(field.getType());
                if (dependencia != null) {
                    field.setAccessible(true); 
                    try {
                        field.set(objeto, dependencia);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Erro ao injetar dependência no campo: " + field.getName(), e);
                    }
                } else {
                    throw new RuntimeException("Não foi possível resolver a dependência para o campo: " + field.getName());
                }
            }
        }
    }

    private static Object obterDependencia(Class<?> tipo) {
        if (tipo.equals(ProdutoService.class)) {
            ProdutoRepository<?, ?> repository = PersistenciaFactory.getProdutoRepository(PersistenciaFactory.MEMORIA); 
            return new ProdutoService(repository);
        }
        return null;
    }
}
