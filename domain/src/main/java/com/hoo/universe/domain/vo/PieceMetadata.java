package com.hoo.universe.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PieceMetadata {

    private final Long innerImageID;
    private final Shape shape;
    private final boolean hidden;

    public static PieceMetadata create(Long innerImageID, Shape shape, Boolean hidden) {
        return new PieceMetadata(innerImageID, shape, hidden != null && hidden);
    }

    public PieceMetadata update(Boolean hidden) {
        return new PieceMetadata(innerImageID, shape, hidden != null && hidden);
    }

    public PieceMetadata move(Shape newShape) {
        return new PieceMetadata(innerImageID, newShape , hidden);
    }
}
