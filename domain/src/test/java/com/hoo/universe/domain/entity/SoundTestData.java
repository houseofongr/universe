package com.hoo.universe.domain.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.domain.entity.Sound.SoundID;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.SoundMetadata;

public class SoundTestData {

    public static SoundBuilder defaultSound() {
        return new SoundBuilder()
                .withSoundID(new SoundID(UuidCreator.getTimeOrderedEpoch(), 111L))
                .withSoundMetadata(SoundMetadata.create(1000L, false))
                .withCommonMetadata(CommonMetadata.create("소리", "사운드는 소리입니다."));
    }

    public static class SoundBuilder {

        private SoundID soundID;
        private SoundMetadata soundMetadata;
        private CommonMetadata commonMetadata;

        public SoundBuilder withSoundID(SoundID soundID) {
            this.soundID = soundID;
            return this;
        }

        public SoundBuilder withSoundMetadata(SoundMetadata soundMetadata) {
            this.soundMetadata = soundMetadata;
            return this;
        }

        public SoundBuilder withCommonMetadata(CommonMetadata commonMetadata) {
            this.commonMetadata = commonMetadata;
            return this;
        }

        public Sound build() {
            return Sound.create(soundID, soundMetadata, commonMetadata);
        }
    }
}
