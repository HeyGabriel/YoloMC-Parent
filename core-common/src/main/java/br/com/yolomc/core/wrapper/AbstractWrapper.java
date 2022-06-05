package br.com.yolomc.core.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Wrapper simples para armazenar valores primitivos.
 *
 * @author skyprogrammer
 */
@Getter
@AllArgsConstructor
public abstract class AbstractWrapper<T> {

    private final T handle;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof AbstractWrapper) {
            AbstractWrapper that = (AbstractWrapper) o;
            return that.handle.equals(this.handle);
        }
        return false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "handle=" + handle +
                '}';
    }
}
