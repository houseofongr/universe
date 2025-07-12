package com.hoo.universe.domain.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.domain.Universe.UniverseID;
import com.hoo.universe.domain.entity.Piece.PieceID;
import com.hoo.universe.domain.entity.Space.SpaceID;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.PieceMetadata;
import com.hoo.universe.domain.vo.Point;
import com.hoo.universe.domain.vo.Shape;

public class PieceTestData {

    public static PieceBuilder defaultPiece() {

        Shape rectangle = Shape.getRectangleBy2Point(Point.of(0.7f, 0.7f), Point.of(0.8f, 0.8f));

        return new PieceBuilder()
                .withPieceID(new PieceID(UuidCreator.getTimeOrderedEpoch(), 789L))
                .withPieceMetadata(withShape(rectangle))
                .withCommonMetadata(CommonMetadata.create("조각", "피스는 조각입니다."));
    }

    public static PieceMetadata withShape(Shape rectangle) {
        return PieceMetadata.create(null, rectangle, false);
    }

    public static class PieceBuilder {

        private PieceID pieceID;
        private UniverseID universeID;
        private SpaceID parentSpaceID;
        private PieceMetadata pieceMetadata;
        private CommonMetadata commonMetadata;

        public PieceBuilder withPieceID(PieceID pieceID) {
            this.pieceID = pieceID;
            return this;
        }

        public PieceBuilder withUniverseID(UniverseID universeID) {
            this.universeID = universeID;
            return this;
        }

        public PieceBuilder withParentSpaceID(SpaceID parentSpaceID) {
            this.parentSpaceID = parentSpaceID;
            return this;
        }


        public PieceBuilder withPieceMetadata(PieceMetadata pieceMetadata) {
            this.pieceMetadata = pieceMetadata;
            return this;
        }

        public PieceBuilder withCommonMetadata(CommonMetadata commonMetadata) {
            this.commonMetadata = commonMetadata;
            return this;
        }

        public Piece build() {
            return Piece.create(pieceID, pieceMetadata, commonMetadata);
        }

        public Piece buildNode() {
            if (this.universeID == null) throw new UnsupportedOperationException("상위 유니버스가 존재하지 않습니다.");
            return Piece.loadNode(this.pieceID, this.universeID, this.parentSpaceID, this.pieceMetadata, this.commonMetadata);
        }
    }
}
