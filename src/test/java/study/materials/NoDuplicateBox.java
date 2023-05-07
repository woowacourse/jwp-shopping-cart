package study.materials;

import java.util.HashSet;
import java.util.List;

public class NoDuplicateBox {

        private final List<Object> objects;

        public NoDuplicateBox(Object... objects) {
            this(List.of(objects));
        }

        private NoDuplicateBox(List<Object> objects) {
            validateNoDuplicate(objects);
            this.objects = objects;
        }

        private void validateNoDuplicate(List<Object> objects) {
            if (objects.size() != new HashSet<>(objects).size()) {
                throw new IllegalArgumentException("중복이 존재합니다");
            }
        }
    }
