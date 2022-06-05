package br.com.yolomc.core.wrapper;

import java.lang.reflect.Method;

/**
 * Wrapper que armazena Métodos e providencia acesso de leitura ao método
 * {@link Method#invoke}.
 *
 * @author skyprogrammer
 */
public class MethodWrapper extends AbstractWrapper<Method> {

    public MethodWrapper(Method handle) {
        super(handle);
        handle.setAccessible(true);
    }


    public <T> T invoke(Object... args) {
        try {
            return (T) getHandle().invoke(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
