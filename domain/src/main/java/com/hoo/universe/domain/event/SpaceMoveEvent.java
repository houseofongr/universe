package com.hoo.universe.domain.event;

import com.hoo.universe.domain.vo.Shape;

public record SpaceMoveEvent(
        boolean isMoved,
        boolean isOverlapped,
        Shape shape
) {
}
