package com.hoo.universe.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SoundMetadata {

    private final Long audioID;
    private final boolean hidden;

    public static SoundMetadata create(Long audioID, Boolean hidden) {
        return new SoundMetadata(audioID, hidden != null && hidden);
    }

    public SoundMetadata update(Boolean hidden) {
        return new SoundMetadata(audioID, hidden != null && hidden);
    }

    public SoundMetadata overwrite(Long newAudioID) {
        return new SoundMetadata(newAudioID, hidden);
    }
}
