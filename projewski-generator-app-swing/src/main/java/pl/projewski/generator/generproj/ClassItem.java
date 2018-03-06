package pl.projewski.generator.generproj;

import lombok.Value;

/**
 * Object created to hold class type and produce string for swing elements, which is more human readable.
 */
@Value
class ClassItem {
    private final Class<?> classItem;

    ClassItem(final Class<?> clazz) {
        this.classItem = clazz;
    }

    @Override
    public String toString() {
        return classItem.getSimpleName();
    }
}
