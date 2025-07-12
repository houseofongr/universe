package com.hoo.universe.domain.entity;

import com.hoo.universe.domain.event.PieceMetadataUpdateEvent;
import com.hoo.universe.domain.event.SoundFileOverwriteEvent;
import com.hoo.universe.domain.event.SoundMetadataUpdateEvent;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.SoundMetadata;
import lombok.*;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "soundID")
public class Sound {

    private final SoundID soundID;
    private SoundMetadata soundMetadata;
    private CommonMetadata commonMetadata;

    public static Sound create(SoundID soundID, SoundMetadata soundMetadata, CommonMetadata commonMetadata) {
        return new Sound(soundID, soundMetadata, commonMetadata);
    }

    public SoundMetadataUpdateEvent updateMetadata(String title, String description, Boolean hidden) {

        this.commonMetadata = commonMetadata.update(title, description);
        this.soundMetadata = soundMetadata.update(hidden);

        return SoundMetadataUpdateEvent.from(commonMetadata, soundMetadata);
    }

    public SoundFileOverwriteEvent overwriteFile(Long newAudioID) {

        Long oldAudioID = this.soundMetadata.getAudioID();
        this.soundMetadata = soundMetadata.overwrite(newAudioID);

        return new SoundFileOverwriteEvent(oldAudioID, soundMetadata.getAudioID());
    }

    @Value
    public static class SoundID {

        @EqualsAndHashCode.Include
        UUID uuid;

        @EqualsAndHashCode.Exclude
        Long sqlID;

    }
}
