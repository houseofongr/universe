package com.hoo.universe.domain;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.domain.Universe.UniverseID;
import com.hoo.universe.domain.vo.AccessStatus;
import com.hoo.universe.domain.vo.Category;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.UniverseMetadata;

import java.util.List;

public class UniverseTestData {

    public static UniverseBuilder defaultUniverse() {
        return new UniverseBuilder()
                .withUniverseId(new UniverseID(UuidCreator.getTimeOrderedEpoch(), null))
                .withCategory(new Category(2L, "Public", "공공"))
                .withUniverseMetadata(UniverseMetadata.create(1L, 2L, 3L, 10L, AccessStatus.PUBLIC, List.of("우주", "별", "지구")))
                .withCommonMetadata(CommonMetadata.create("우주", "유니버스는 우주입니다."));
    }

    public static class UniverseBuilder {
        private UniverseID universeID;
        private Category category;
        private UniverseMetadata universeMetadata;
        private CommonMetadata commonMetadata;

        public UniverseBuilder withUniverseId(UniverseID universeID) {
            this.universeID = universeID;
            return this;
        }

        public UniverseBuilder withCategory(Category category) {
            this.category = category;
            return this;
        }

        public UniverseBuilder withUniverseMetadata(UniverseMetadata universeMetadata) {
            this.universeMetadata = universeMetadata;
            return this;
        }

        public UniverseBuilder withCommonMetadata(CommonMetadata commonMetadata) {
            this.commonMetadata = commonMetadata;
            return this;
        }

        public Universe build() {
            return Universe.create(this.universeID, this.category, this.universeMetadata, this.commonMetadata);
        }
    }

}
