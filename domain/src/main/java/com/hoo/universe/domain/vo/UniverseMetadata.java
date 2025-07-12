package com.hoo.universe.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UniverseMetadata {

    private final Long thumbmusicID;
    private final Long thumbnailID;
    private final Long innerImageID;
    private final Long authorID;
    private final long viewCount;
    private final long likeCount;
    private final AccessStatus accessStatus;
    private final List<String> tags;

    public static UniverseMetadata create(Long thumbmusicID, Long thumbnailID, Long innerImageID, Long authorID, AccessStatus accessStatus, List<String> tags) {
        return new UniverseMetadata(thumbmusicID, thumbnailID, innerImageID, authorID, 0, 0, accessStatus, tags);
    }

    public UniverseMetadata update(AccessStatus accessStatus, List<String> tags) {
        return new UniverseMetadata(thumbmusicID, thumbnailID, innerImageID, authorID, viewCount, likeCount, accessStatus, tags);
    }

    public UniverseMetadata overwrite(Long thumbmusicID, Long thumbnailID, Long innerImageID) {

        Long newThumbmusicID = thumbmusicID == null? this.thumbmusicID : thumbmusicID;
        Long newThumbnailID = thumbnailID == null? this.thumbnailID : thumbnailID;
        Long newInnerImageID = innerImageID == null? this.innerImageID : innerImageID;

        return new UniverseMetadata(newThumbmusicID, newThumbnailID, newInnerImageID, authorID, viewCount, likeCount, accessStatus, tags);
    }
}
