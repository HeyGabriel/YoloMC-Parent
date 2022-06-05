package br.com.yolomc.core.wrapper;

import java.lang.reflect.Field;

/**
 * Wrapper que armazena Fields e providencia acesso de leitura aos métodos
 * {@link Field#get} e {@link Field#set}.
 *
 * @author skyprogrammer
 */
public class FieldWrapper extends AbstractWrapper<Field> {

    public FieldWrapper(Field handle) {
        super(handle);
        handle.setAccessible(true);
    }

    public <T> T get(Object obj) {
        try {
            return (T) getHandle().get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void set(Object obj, Object value) {
        try {
            getHandle().set(obj, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
