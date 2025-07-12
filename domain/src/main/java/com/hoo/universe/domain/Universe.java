package com.hoo.universe.domain;

import com.hoo.universe.domain.entity.Piece;
import com.hoo.universe.domain.entity.Piece.PieceID;
import com.hoo.universe.domain.entity.Space;
import com.hoo.universe.domain.entity.Space.SpaceID;
import com.hoo.universe.domain.event.PieceMoveEvent;
import com.hoo.universe.domain.event.SpaceMoveEvent;
import com.hoo.universe.domain.event.UniverseFileOverwriteEvent;
import com.hoo.universe.domain.event.UniverseMetadataUpdateEvent;
import com.hoo.universe.domain.vo.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "universeID")
public class Universe {

    private static final int MAX_SPACE_SIZE = 10;
    private static final int MAX_CHILD_SIZE = 100;

    private final UniverseID universeID;
    private final Category category;
    private UniverseMetadata universeMetadata;
    private CommonMetadata commonMetadata;
    private final List<Space> spaces;
    private final List<Piece> pieces;

    public static Universe create(UniverseID universeID, Category category, UniverseMetadata universeMetadata, CommonMetadata commonMetadata) {
        return new Universe(universeID, category, universeMetadata, commonMetadata, new ArrayList<>(), new ArrayList<>());
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

    public UniverseMetadataUpdateEvent updateMetadata(String title, String description, AccessStatus accessStatus, List<String> tags) {

        this.commonMetadata = commonMetadata.update(title, description);
        this.universeMetadata = universeMetadata.update(accessStatus, tags);

        return UniverseMetadataUpdateEvent.from(commonMetadata, universeMetadata);
    }

    public UniverseFileOverwriteEvent overwriteFiles(Long newThumbmusicID, Long newThumbnailID, Long newInnerImageID) {

        Long oldThumbmusicID = this.universeMetadata.getThumbmusicID();
        Long oldThumbnailID = this.universeMetadata.getThumbnailID();
        Long oldInnerImageID = this.universeMetadata.getInnerImageID();
        this.universeMetadata = universeMetadata.overwrite(newThumbmusicID, newThumbnailID, newInnerImageID);

        return UniverseFileOverwriteEvent.from(oldThumbmusicID, oldThumbnailID, oldInnerImageID, universeMetadata);
    }

    public Universe loadTree(Universe universe, List<Space> spaces, List<Piece> pieces) {

        List<Space> notChildSpaces = new ArrayList<>();
        List<Piece> notChildPieces = new ArrayList<>();

        for (Space space : spaces) {
            if (space.isFirstNode()) this.spaces.add(space);
            else notChildSpaces.add(space);
        }

        for (Piece piece : pieces) {
            if (piece.isFirstNode()) this.pieces.add(piece);
            else notChildPieces.add(piece);
        }

        for (Space childSpace : this.spaces) {
            makeSpaceTree(childSpace, notChildSpaces, notChildPieces);
        }

        return universe;
    }

    private void makeSpaceTree(Space parentSpace, List<Space> spaces, List<Piece> pieces) {

        List<Space> notChildSpaces = new ArrayList<>();
        List<Piece> notChildPieces = new ArrayList<>();

        for (Space space : spaces) {
            if (space.isParent(parentSpace)) parentSpace.getSpaces().add(space);
            else notChildSpaces.add(space);
        }

        for (Piece piece : pieces) {
            if (piece.isParent(parentSpace)) parentSpace.getPieces().add(piece);
            else notChildPieces.add(piece);
        }

        for (Space childSpace : parentSpace.getSpaces()) {
            makeSpaceTree(childSpace, notChildSpaces, notChildPieces);
        }
    }

    public Space getSpace(SpaceID spaceID) {

        for (Space childSpace : spaces) {
            Space found = getSpace(childSpace, spaceID);
            if (found != null) return found;
        }

        return null;
    }

    private Space getSpace(Space space, SpaceID spaceID) {

        if (space.getSpaceID().equals(spaceID)) return space;

        for (Space childSpace : space.getSpaces()) {
            Space found = getSpace(childSpace, spaceID);
            if (found != null) return found;
        }

        return null;
    }

    public Piece getPiece(PieceID pieceID) {

        for (Space childSpace : spaces) {
            Piece found = getPiece(childSpace, pieceID);
            if (found != null) return found;
        }

        return null;
    }

    private Piece getPiece(Space space, PieceID pieceID) {

        for (Piece piece : space.getPieces()) {
            if (piece.getPieceID().equals(pieceID)) return piece;
        }

        for (Space childSpace : space.getSpaces()) {
            Piece found = getPiece(childSpace, pieceID);
            if (found != null) return found;
        }

        return null;
    }

    public SpaceMoveEvent moveSpace(SpaceID spaceID, Shape newShape) {
        Space target = getSpace(spaceID);
        List<Shape> existShape = getExistShapesExcept(target);

        return target.move(existShape, newShape);
    }

    private List<Shape> getExistShapes() {
        return Stream.concat(
                        spaces.stream().map(space -> space.getSpaceMetadata().getShape()),
                        pieces.stream().map(piece -> piece.getPieceMetadata().getShape()))
                .toList();
    }

    public PieceMoveEvent movePiece(PieceID pieceID, Shape newShape) {
        Piece target = getPiece(pieceID);
        List<Shape> existShapes = getExistShapesExcept(target);

        return target.move(existShapes, newShape);
    }

    private List<Shape> getExistShapesExcept(Space target) {
        if (target.isFirstNode())
            return Stream.concat(
                            spaces.stream()
                                    .filter(space -> !space.equals(target))
                                    .map(space -> space.getSpaceMetadata().getShape()),
                            pieces.stream().map(piece -> piece.getPieceMetadata().getShape()))
                    .toList();

        Space parent = getSpace(target.getParentSpaceID());
        return Stream.concat(
                        parent.getSpaces().stream()
                                .filter(space -> !space.equals(target))
                                .map(space -> space.getSpaceMetadata().getShape()),
                        parent.getPieces().stream().map(piece -> piece.getPieceMetadata().getShape()))
                .toList();
    }

    private List<Shape> getExistShapesExcept(Piece target) {
        if (target.isFirstNode())
            return Stream.concat(
                            spaces.stream().map(space -> space.getSpaceMetadata().getShape()),
                            pieces.stream()
                                    .filter(piece -> !piece.equals(target))
                                    .map(piece -> piece.getPieceMetadata().getShape()))
                    .toList();

        Space parent = getSpace(target.getParentSpaceID());
        return Stream.concat(
                        parent.getSpaces().stream()
                                .map(space -> space.getSpaceMetadata().getShape()),
                        parent.getPieces().stream()
                                .filter(piece -> !piece.equals(target))
                                .map(piece -> piece.getPieceMetadata().getShape()))
                .toList();
    }

    @Value
    public static class UniverseID {

        @EqualsAndHashCode.Include
        UUID uuid;

        @EqualsAndHashCode.Exclude
        Long sqlID;

    }
}
