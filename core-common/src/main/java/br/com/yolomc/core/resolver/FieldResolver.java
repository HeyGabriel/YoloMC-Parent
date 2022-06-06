package br.com.yolomc.core.resolver;

import br.com.yolomc.core.wrapper.FieldWrapper;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;

/**
 * Resolvedor de Field.
 *
 * @author skyprogrammer
 */
@RequiredArgsConstructor
public class FieldResolver extends Resolver<Field> {

    private final Class<?> mainClass;
    private final String name;

    @Override
    public Field resolve() {
        Field found = null;
        Class<?> search = this.mainClass;
        try {
            found = search.getDeclaredField(name);
            found.setAccessible(true);
        } catch (Exception err) {
            while ((search = search.getSuperclass()) != null && found == null) {
                try {
                    (found = search.getDeclaredField(name)).setAccessible(true);
                } catch (Exception err2) {
                    continue;
                }
            }
        }
        return found;
    }
}
