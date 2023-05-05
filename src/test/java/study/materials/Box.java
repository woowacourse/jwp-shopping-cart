package study.materials;

import java.util.List;

public class Box {

    private final List<Object> objects;

    public Box(Object... objects) {
        this(List.of(objects));
    }

    private Box(List<Object> objects) {
        this.objects = objects;
    }
}