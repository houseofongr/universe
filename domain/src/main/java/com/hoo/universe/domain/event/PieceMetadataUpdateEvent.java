package com.hoo.universe.domain.event;

import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.PieceMetadata;

import java.time.ZonedDateTime;

public record PieceMetadataUpdateEvent(
        String title,
        String description,
        ZonedDateTime updatedTime,
        boolean hidden
) {
    public static PieceMetadataUpdateEvent from(CommonMetadata commonMetadata, PieceMetadata pieceMetadata) {
        return new PieceMetadataUpdateEvent(
                commonMetadata.getTitle(),
                commonMetadata.getDescription(),
                commonMetadata.getUpdatedTime(),
                pieceMetadata.isHidden()
        );
    }
}
