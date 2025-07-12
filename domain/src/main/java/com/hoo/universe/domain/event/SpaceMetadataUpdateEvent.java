package com.hoo.universe.domain.event;

import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.SpaceMetadata;

import java.time.ZonedDateTime;

public record SpaceMetadataUpdateEvent(
        String title,
        String description,
        ZonedDateTime updatedTime,
        boolean hidden
) {
    public static SpaceMetadataUpdateEvent from(CommonMetadata commonMetadata, SpaceMetadata spaceMetadata) {
        return new SpaceMetadataUpdateEvent(
                commonMetadata.getTitle(),
                commonMetadata.getDescription(),
                commonMetadata.getUpdatedTime(),
                spaceMetadata.isHidden()
        );
    }
}
