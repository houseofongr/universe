package com.hoo.universe.domain.entity;

import com.hoo.universe.domain.Universe.UniverseID;
import com.hoo.universe.domain.event.SpaceFileOverwriteEvent;
import com.hoo.universe.domain.event.SpaceMetadataUpdateEvent;
import com.hoo.universe.domain.event.SpaceMoveEvent;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.Shape;
import com.hoo.universe.domain.vo.SpaceMetadata;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "spaceID")
public class Space {

    private static final int MAX_SPACE_SIZE = 10;
    private static final int MAX_CHILD_SIZE = 100;

    private final SpaceID spaceID;
    private final UniverseID universeID;
    private final SpaceID parentSpaceID;
    private SpaceMetadata spaceMetadata;
    private CommonMetadata commonMetadata;
    private final List<Space> spaces;
    private final List<Piece> pieces;

    public static Space create(SpaceID spaceID, SpaceMetadata spaceMetadata, CommonMetadata commonMetadata) {
        return new Space(spaceID, null, null, spaceMetadata, commonMetadata, new ArrayList<>(), new ArrayList<>());
    }

    public static Space loadNode(SpaceID spaceID, UniverseID universeID, SpaceID parentSpaceID, SpaceMetadata spaceMetadata, CommonMetadata commonMetadata) {
        return new Space(spaceID, universeID, parentSpaceID, spaceMetadata, commonMetadata, new ArrayList<>(), new ArrayList<>());
    }

    public boolean createSpaceInside(Space newSpace) {

        if (newSpace.getSpaceMetadata().getShape().isOverwrapped(getExistShapes())) return false;
        if (spaces.size() == MAX_SPACE_SIZE) return false;
        if (pieces.size() + spaces.size() == MAX_CHILD_SIZE) return false;

        spaces.add(newSpace);
        return true;
    }

    public boolean createPieceInside(Piece newPiece) {

        if (newPiece.getPieceMetadata().getShape().isOverwrapped(getExistShapes())) return false;
        if (pieces.size() + spaces.size() == MAX_CHILD_SIZE) return false;

        pieces.add(newPiece);
        return true;
    }

    private List<Shape> getExistShapes() {
        return Stream.concat(
                        spaces.stream().map(space -> space.getSpaceMetadata().getShape()),
                        pieces.stream().map(piece -> piece.getPieceMetadata().getShape()))
                .toList();
    }

    public boolean isFirstNode() {
        return this.parentSpaceID == null;
    }

    public boolean isParent(Space parentSpace) {
        return this.parentSpaceID.equals(parentSpace.spaceID);
    }

    public SpaceMetadataUpdateEvent updateMetadata(String title, String description, Boolean hidden) {

        this.commonMetadata = commonMetadata.update(title, description);
        this.spaceMetadata = spaceMetadata.update(hidden);

        return SpaceMetadataUpdateEvent.from(commonMetadata, spaceMetadata);
    }

    public SpaceFileOverwriteEvent overwriteFile(Long newInnerImageID) {

        Long oldInnerImageID = this.spaceMetadata.getInnerImageID();
        this.spaceMetadata = spaceMetadata.overwrite(newInnerImageID);

        return new SpaceFileOverwriteEvent(oldInnerImageID, spaceMetadata.getInnerImageID());
    }

    public SpaceMoveEvent move(List<Shape> existShapes, Shape newShape) {

        if (newShape.isOverwrapped(existShapes))
            return new SpaceMoveEvent(false, true, null);

        this.spaceMetadata = spaceMetadata.move(newShape);

        return new SpaceMoveEvent(true, false, spaceMetadata.getShape());
    }

    @Value
    public static class SpaceID {

        @EqualsAndHashCode.Include
        UUID uuid;

        @EqualsAndHashCode.Exclude
        Long sqlID;

    }
}
