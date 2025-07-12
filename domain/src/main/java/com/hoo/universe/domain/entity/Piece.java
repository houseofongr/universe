package com.hoo.universe.domain.entity;

import com.hoo.universe.domain.Universe.UniverseID;
import com.hoo.universe.domain.entity.Space.SpaceID;
import com.hoo.universe.domain.event.PieceMetadataUpdateEvent;
import com.hoo.universe.domain.event.PieceMoveEvent;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.PieceMetadata;
import com.hoo.universe.domain.vo.Shape;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "pieceID")
public class Piece {

    private static final int MAX_SOUND_SIZE = 100;

    private final PieceID pieceID;
    private final UniverseID universeID;
    private final SpaceID parentSpaceID;
    private PieceMetadata pieceMetadata;
    private CommonMetadata commonMetadata;
    private final List<Sound> sounds;

    public static Piece create(PieceID pieceID, PieceMetadata pieceMetadata, CommonMetadata commonMetadata) {
        return new Piece(pieceID, null, null, pieceMetadata, commonMetadata, new ArrayList<>());
    }

    public static Piece loadNode(PieceID pieceID, UniverseID universeID, SpaceID parentSpaceID, PieceMetadata pieceMetadata, CommonMetadata commonMetadata) {
        return new Piece(pieceID, universeID, parentSpaceID, pieceMetadata, commonMetadata, new ArrayList<>());
    }

    public boolean addSound(Sound sound) {

        if (sounds.size() == MAX_SOUND_SIZE) return false;

        sounds.add(sound);
        return true;
    }

    public boolean isFirstNode() {
        return this.parentSpaceID == null;
    }

    public boolean isParent(Space parentSpace) {
        return this.parentSpaceID.equals(parentSpace.getSpaceID());
    }

    public PieceMetadataUpdateEvent updateMetadata(String title, String description, Boolean hidden) {

        this.commonMetadata = commonMetadata.update(title, description);
        this.pieceMetadata = pieceMetadata.update(hidden);

        return PieceMetadataUpdateEvent.from(commonMetadata, pieceMetadata);
    }

    public PieceMoveEvent move(List<Shape> existShapes, Shape newShape) {

        if (newShape.isOverwrapped(existShapes))
            return new PieceMoveEvent(false, true, null);

        this.pieceMetadata = pieceMetadata.move(newShape);

        return new PieceMoveEvent(true, false, newShape);
    }

    @Value
    public static class PieceID {

        @EqualsAndHashCode.Include
        UUID uuid;

        @EqualsAndHashCode.Exclude
        Long sqlID;

    }
}
