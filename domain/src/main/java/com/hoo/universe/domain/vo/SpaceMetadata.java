package com.hoo.universe.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SpaceMetadata {

    private final Long innerImageID;
    private final Shape shape;
    private final boolean hidden;

    public static SpaceMetadata create(Long innerImageID, Shape shape, Boolean hidden) {
        return new SpaceMetadata(innerImageID, shape, hidden != null && hidden);
    }

    public SpaceMetadata update(Boolean hidden) {
        return new SpaceMetadata(innerImageID, shape, hidden != null && hidden);
    }

    public SpaceMetadata overwrite(Long innerImageID) {
        return new SpaceMetadata(innerImageID, shape, hidden);
    }

    public SpaceMetadata move(Shape newShape) {
        return new SpaceMetadata(innerImageID, newShape, hidden);
    }
}
