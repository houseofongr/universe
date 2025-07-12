package com.hoo.universe.domain.event;

public record SoundFileOverwriteEvent(
        Long oldAudioID,
        Long newAudioID
) {
}
