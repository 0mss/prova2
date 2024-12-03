package br.com.ucsal.util;

import br.com.ucsal.annotations.Singleton;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SingletonLoader {

    private static final Map<Class<?>, Object> singletons = new ConcurrentHashMap<>();

    public static void loadSingletons() {
        Reflections reflections = new Reflections("br.com.ucsal"); 
        Set<Class<?>> singletonClasses = reflections.getTypesAnnotatedWith(Singleton.class);

        for (Class<?> singletonClass : singletonClasses) {
            try {
                if (!singletons.containsKey(singletonClass)) {
                    Object instance = createInstance(singletonClass);
                    singletons.put(singletonClass, instance);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Falha ao criar instância para classe: " + singletonClass.getName(), e);
            }
        }
    }

    private static Object createInstance(Class<?> clazz) throws Exception {
        var constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true); 
        return constructor.newInstance();
    }

    public static <T> T getSingleton(Class<T> clazz) {
        @SuppressWarnings("unchecked")
        T instance = (T) singletons.get(clazz);
        if (instance == null) {
            throw new IllegalStateException("Singleton não carregado para classe: " + clazz.getName());
        }
        return instance;
    }
}
