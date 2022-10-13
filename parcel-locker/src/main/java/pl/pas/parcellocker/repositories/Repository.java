package pl.pas.parcellocker.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Repository<T> {
    protected List<T> objects;

    public T get(int index) {
        if (objects.size() >= index && index >= 0) {
            return objects.get(index);
        }
        return null;
    }

    public void add(T object) {
        if (object != null)
            objects.add(object);
    }

    public void remove(T object) {
        if (object != null) {
            objects.remove(object);
        }
    }

    public String report() {
        StringBuilder stringBuilder = new StringBuilder();
        for (T object : objects) {
            stringBuilder.append(object.toString());
        }
        return stringBuilder.toString();
    }

    public int size() {
        return objects.size();
    }

    public List<T> findBy(Predicate<T> predicate) {
        List<T> found = new ArrayList<>();
        for (T object : objects) {
            if (object != null && predicate.test(object)) {
                found.add(object);
            }
        }
        return found;
    }

    public List<T> findAll() {
        return findBy((T) -> true);
    }
}
