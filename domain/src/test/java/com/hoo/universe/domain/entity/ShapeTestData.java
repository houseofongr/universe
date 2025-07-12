package com.hoo.universe.domain.entity;

import com.hoo.universe.domain.vo.Point;
import com.hoo.universe.domain.vo.Shape;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ShapeTestData {

    public static List<Shape> createNonOverlappingShapes(int size) {

        if (size > 10) throw new UnsupportedOperationException("최대 사이즈 개수 초과");

        List<Shape> shapes = new ArrayList<>();

        float unitSize = (float) 1 / size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                shapes.add(Shape.getRectangleBy2Point(
                        Point.of(unitSize * i, unitSize * j),
                        Point.of(unitSize * (i + 1), unitSize * (j + 1))
                ));
            }
        }

        return shapes;
    }

    @Test
    @DisplayName("shape 사이즈와 겹치는지 확인")
    void sizeAndOverlapping() {
        List<Shape> shapes1 = createNonOverlappingShapes(1);
        List<Shape> shapes5 = createNonOverlappingShapes(5);
        List<Shape> shapes10 = createNonOverlappingShapes(10);

        assertThat(shapes1).hasSize(1);
        assertThat(shapes5).hasSize(25);
        assertThat(shapes10).hasSize(100);

        assertThat(isOverlapping(shapes1)).isFalse();
        assertThat(isOverlapping(shapes5)).isFalse();
        assertThat(isOverlapping(shapes10)).isFalse();
    }

    boolean isOverlapping(List<Shape> shapes) {
        for (int i = 0; i < shapes.size(); i++) {
            Shape shape = shapes.get(i);
            for (int j = i + 1; j < shapes.size(); j++) {
                if (shape.isOverwrapped(shapes.get(j))) return true;
            }
        }
        return false;
    }
}
