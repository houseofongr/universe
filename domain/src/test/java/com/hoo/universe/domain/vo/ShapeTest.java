package com.hoo.universe.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ShapeTest {

    @Test
    @DisplayName("생성된 모양 정렬하는지 확인")
    void testSort() {
        // given
        Point s = Point.of(0.3f, 0.2f);
        Point e = Point.of(0.1f, 0.3f);

        // when
        Shape rectangle = Shape.getRectangleBy2Point(s, e);

        // then
        assertThat(rectangle.getPoints().getFirst()).isEqualTo(Point.of(0.1f, 0.2f));
        assertThat(rectangle.getPoints().getLast()).isEqualTo(Point.of(0.3f, 0.3f));
    }

    @Test
    @DisplayName("시작점과 끝점이 같은 직사각형을 만들때 오류 발생하는지 확인")
    void testEqualStartAndEnd() {
        // given

        // then
        assertThatThrownBy(() -> Shape.getRectangleBy2Point(
                Point.of(0.1f, 0.2f),
                Point.of(0.1f, 0.2f)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("모양을 생성할 좌표 숫자가 부족합니다.");
    }

    @Test
    @DisplayName("범위 초과할때 오류 발생하는지 확인")
    void testOutOfBound() {
        assertThatThrownBy(() -> Shape.getRectangleBy2Point(
                Point.of(1f, 2f),
                Point.of(0.1f, 0.2f)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("좌표 범위를 초과했습니다.");
    }

    @Test
    @DisplayName("3개 미만의 점으로 도형을 만들때 오류 발생하는지 확인")
    void testLessThan3() {
        assertThatThrownBy(() -> Shape.of(
                Point.of(0.1f, 0.1f),
                Point.of(0.1f, 0.2f)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("모양을 생성할 좌표 숫자가 부족합니다.");

        assertThatThrownBy(() -> Shape.of(
                Point.of(0.1f, 0.1f),
                Point.of(0.1f, 0.1f),
                Point.of(0.1f, 0.2f)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("모양을 생성할 좌표 숫자가 부족합니다.");
    }

}