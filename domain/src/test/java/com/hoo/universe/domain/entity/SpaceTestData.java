package com.hoo.universe.domain.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.domain.Universe.UniverseID;
import com.hoo.universe.domain.entity.Space.SpaceID;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.Point;
import com.hoo.universe.domain.vo.Shape;
import com.hoo.universe.domain.vo.SpaceMetadata;

public class SpaceTestData {

    public static SpaceBuilder defaultSpace() {

        Shape rectangle = Shape.getRectangleBy2Point(Point.of(0.3f, 0.2f), Point.of(0.5f, 0.6f));

        return new SpaceBuilder()
                .withSpaceId(new SpaceID(UuidCreator.getTimeOrderedEpoch(), 456L))
                .withSpaceMetadata(withShape(rectangle))
                .withCommonMetadata(CommonMetadata.create("공간", "스페이스는 공간입니다."));
    }

    public static SpaceMetadata withShape(Shape shape) {
        return SpaceMetadata.create(10L, shape, false);
    }

    public static class SpaceBuilder {

        private SpaceID spaceID;
        private UniverseID universeID;
        private SpaceID parentSpaceID;
        private SpaceMetadata spaceMetadata;
        private CommonMetadata commonMetadata;

        public SpaceBuilder withSpaceId(SpaceID spaceID) {
            this.spaceID = spaceID;
            return this;
        }

        public SpaceBuilder withUniverseID(UniverseID universeID) {
            this.universeID = universeID;
            return this;
        }

        public SpaceBuilder withParentSpaceID(SpaceID parentSpaceID) {
            this.parentSpaceID = parentSpaceID;
            return this;
        }

        public SpaceBuilder withSpaceMetadata(SpaceMetadata spaceMetadata) {
            this.spaceMetadata = spaceMetadata;
            return this;
        }

        public SpaceBuilder withCommonMetadata(CommonMetadata commonMetadata) {
            this.commonMetadata = commonMetadata;
            return this;
        }

        public Space build() {
            return Space.create(this.spaceID, this.spaceMetadata, this.commonMetadata);
        }

        public Space buildNode() {
            if (this.universeID == null) throw new UnsupportedOperationException("상위 유니버스가 존재하지 않습니다.");
            return Space.loadNode(this.spaceID, this.universeID, this.parentSpaceID, this.spaceMetadata, this.commonMetadata);
        }
    }
}
