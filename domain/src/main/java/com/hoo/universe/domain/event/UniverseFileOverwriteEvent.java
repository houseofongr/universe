package com.hoo.universe.domain.event;

import com.hoo.universe.domain.vo.UniverseMetadata;

public record UniverseFileOverwriteEvent(
        Long oldThumbmusicID,
        Long oldThumbnailID,
        Long oldInnerImageID,
        Long newThumbmusicID,
        Long newThumbnailID,
        Long newInnerImageID
) {

    public static UniverseFileOverwriteEvent from(Long oldThumbmusicID, Long oldThumbnailID, Long oldInnerImageID, UniverseMetadata universeMetadata) {
        return new UniverseFileOverwriteEvent(
                equalsNull(oldThumbmusicID, universeMetadata.getThumbmusicID()),
                equalsNull(oldThumbnailID, universeMetadata.getThumbnailID()),
                equalsNull(oldInnerImageID, universeMetadata.getInnerImageID()),
                equalsNull(universeMetadata.getThumbmusicID(), oldThumbmusicID),
                equalsNull(universeMetadata.getThumbnailID(), oldThumbnailID),
                equalsNull(universeMetadata.getInnerImageID(), oldInnerImageID)
        );
    }

    private static Long equalsNull(Long target, Long compare) {
        return target.equals(compare)? null : target;
    }
}
