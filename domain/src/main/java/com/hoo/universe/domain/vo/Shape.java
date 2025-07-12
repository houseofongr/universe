package com.hoo.universe.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.util.*;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Shape {

    List<Point> points;

    public static Shape of(List<Point> points) {

        Set<Point> pointSet = new TreeSet<>(points);
        if (pointSet.size() < 3) throw new IllegalArgumentException("모양을 생성할 좌표 숫자가 부족합니다.");

        points = new ArrayList<>(pointSet);

        return new Shape(points);
    }

    public static Shape of(Point... points) {
        return of(new ArrayList<>(Arrays.asList(points)));
    }

    public static Shape getRectangleBy2Point(Point s, Point e) {
        return Shape.of(s, e, Point.of(s.getX(), e.getY()), Point.of(e.getX(), s.getY()));
    }

    public Area getArea() {

        Path2D.Float path = new Path2D.Float();

        path.moveTo(points.getFirst().getX(), points.getFirst().getY());

        for (int i = 1; i < points.size(); i++) {
            path.lineTo(points.get(i).getX(), points.get(i).getY());
        }

        path.closePath();

        return new Area(path);
    }

    public boolean isOverwrapped(Shape shape) {
        Area area = getArea();
        Area area1 = shape.getArea();

        area.intersect(area1);

        return !area.isEmpty();
    }

    public boolean isOverwrapped(List<Shape> shapes) {
        return shapes.stream().anyMatch(this::isOverwrapped);
    }
}
