package com.hoo.universe.domain.event;

import com.hoo.universe.domain.vo.AccessStatus;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.UniverseMetadata;

import java.time.ZonedDateTime;
import java.util.List;

public record UniverseMetadataUpdateEvent(
        String title,
        String description,
        ZonedDateTime updatedTime,
        AccessStatus accessStatus,
        List<String> tags
) {

    public static UniverseMetadataUpdateEvent from(CommonMetadata commonMetadata, UniverseMetadata universeMetadata) {
        return new UniverseMetadataUpdateEvent(
                commonMetadata.getTitle(),
                commonMetadata.getDescription(),
                commonMetadata.getUpdatedTime(),
                universeMetadata.getAccessStatus(),
                universeMetadata.getTags()
        );
    }
}
