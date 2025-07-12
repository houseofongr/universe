package com.hoo.universe.domain.event;

import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.PieceMetadata;
import com.hoo.universe.domain.vo.SoundMetadata;

import java.time.ZonedDateTime;

public record SoundMetadataUpdateEvent(
        String title,
        String description,
        ZonedDateTime updatedTime,
        boolean hidden
) {
    public static SoundMetadataUpdateEvent from(CommonMetadata commonMetadata, SoundMetadata soundMetadata) {
        return new SoundMetadataUpdateEvent(
                commonMetadata.getTitle(),
                commonMetadata.getDescription(),
                commonMetadata.getUpdatedTime(),
                soundMetadata.isHidden()
        );
    }
}
