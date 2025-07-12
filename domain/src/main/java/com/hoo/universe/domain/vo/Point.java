package com.hoo.universe.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Point implements Comparable<Point> {

    float x;
    float y;

    public static Point of(float x, float y) {
        if (x < 0 || x > 1 || y < 0 || y > 1) throw new IllegalArgumentException("좌표 범위를 초과했습니다.");
        return new Point(x, y);
    }

    @Override
    public int compareTo(Point e) {
        if (this.x == e.x) return Float.compare(this.y, e.y);
        return Float.compare(this.x, e.x);
    }

}
