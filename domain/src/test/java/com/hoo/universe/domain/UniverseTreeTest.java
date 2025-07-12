package com.hoo.universe.domain;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.domain.Universe.UniverseID;
import com.hoo.universe.domain.entity.Piece;
import com.hoo.universe.domain.entity.Piece.PieceID;
import com.hoo.universe.domain.entity.PieceTestData;
import com.hoo.universe.domain.entity.Space;
import com.hoo.universe.domain.entity.Space.SpaceID;
import com.hoo.universe.domain.entity.SpaceTestData;
import com.hoo.universe.domain.event.PieceMoveEvent;
import com.hoo.universe.domain.event.SpaceMoveEvent;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.Point;
import com.hoo.universe.domain.vo.Shape;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.hoo.universe.domain.UniverseTestData.defaultUniverse;
import static com.hoo.universe.domain.entity.PieceTestData.defaultPiece;
import static com.hoo.universe.domain.entity.ShapeTestData.createNonOverlappingShapes;
import static com.hoo.universe.domain.entity.SpaceTestData.defaultSpace;
import static org.assertj.core.api.Assertions.assertThat;

public class UniverseTreeTest {

    public static List<Space> defaultSpaceNodes(UniverseID universeID) {

        List<Shape> shapes = createNonOverlappingShapes(4);

        Space 스페이스1 = defaultSpace()
                .withSpaceId(new SpaceID(UuidCreator.getTimeOrderedEpoch(), 1L))
                .withUniverseID(universeID)
                .withSpaceMetadata(SpaceTestData.withShape(shapes.get(0)))
                .withCommonMetadata(CommonMetadata.create("스페이스1", "유니버스의 첫번째 스페이스")).buildNode();

        Space 스페이스2 = defaultSpace()
                .withSpaceId(new SpaceID(UuidCreator.getTimeOrderedEpoch(), 2L))
                .withUniverseID(universeID)
                .withSpaceMetadata(SpaceTestData.withShape(shapes.get(1)))
                .withCommonMetadata(CommonMetadata.create("스페이스2", "유니버스의 두번째 스페이스")).buildNode();

        Space 스페이스3 = defaultSpace()
                .withSpaceId(new SpaceID(UuidCreator.getTimeOrderedEpoch(), 3L))
                .withUniverseID(universeID)
                .withParentSpaceID(스페이스1.getSpaceID())
                .withSpaceMetadata(SpaceTestData.withShape(shapes.get(2)))
                .withCommonMetadata(CommonMetadata.create("스페이스3", "스페이스1의 첫번째 스페이스")).buildNode();

        Space 스페이스4 = defaultSpace()
                .withSpaceId(new SpaceID(UuidCreator.getTimeOrderedEpoch(), 4L))
                .withUniverseID(universeID)
                .withParentSpaceID(스페이스2.getSpaceID())
                .withSpaceMetadata(SpaceTestData.withShape(shapes.get(3)))
                .withCommonMetadata(CommonMetadata.create("스페이스4", "스페이스2의 첫번째 스페이스")).buildNode();

        Space 스페이스5 = defaultSpace()
                .withSpaceId(new SpaceID(UuidCreator.getTimeOrderedEpoch(), 5L))
                .withUniverseID(universeID)
                .withParentSpaceID(스페이스2.getSpaceID())
                .withSpaceMetadata(SpaceTestData.withShape(shapes.get(4)))
                .withCommonMetadata(CommonMetadata.create("스페이스5", "스페이스2의 두번째 스페이스")).buildNode();

        return new ArrayList<>(List.of(스페이스1, 스페이스2, 스페이스3, 스페이스4, 스페이스5));
    }

    public static List<Piece> defaultPieceNodes(UniverseID universeID, List<Space> spaces) {

        List<Shape> shapes = createNonOverlappingShapes(4);

        Piece 피스1 = defaultPiece()
                .withPieceID(new PieceID(UuidCreator.getTimeOrderedEpoch(), 1L))
                .withUniverseID(universeID)
                .withPieceMetadata(PieceTestData.withShape(shapes.get(5)))
                .withCommonMetadata(CommonMetadata.create("피스1", "유니버스의 첫번째 피스")).buildNode();

        Piece 피스2 = defaultPiece()
                .withPieceID(new PieceID(UuidCreator.getTimeOrderedEpoch(), 2L))
                .withUniverseID(universeID)
                .withParentSpaceID(spaces.get(0).getSpaceID())
                .withPieceMetadata(PieceTestData.withShape(shapes.get(6)))
                .withCommonMetadata(CommonMetadata.create("피스2", "스페이스1의 첫번째 피스")).buildNode();

        Piece 피스3 = defaultPiece()
                .withPieceID(new PieceID(UuidCreator.getTimeOrderedEpoch(), 3L))
                .withUniverseID(universeID)
                .withParentSpaceID(spaces.get(2).getSpaceID())
                .withPieceMetadata(PieceTestData.withShape(shapes.get(7)))
                .withCommonMetadata(CommonMetadata.create("피스3", "스페이스3의 첫번째 피스")).buildNode();

        Piece 피스4 = defaultPiece()
                .withPieceID(new PieceID(UuidCreator.getTimeOrderedEpoch(), 4L))
                .withUniverseID(universeID)
                .withParentSpaceID(spaces.get(3).getSpaceID())
                .withPieceMetadata(PieceTestData.withShape(shapes.get(8)))
                .withCommonMetadata(CommonMetadata.create("피스4", "스페이스4의 첫번째 피스")).buildNode();

        Piece 피스5 = defaultPiece()
                .withPieceID(new PieceID(UuidCreator.getTimeOrderedEpoch(), 5L))
                .withUniverseID(universeID)
                .withParentSpaceID(spaces.get(3).getSpaceID())
                .withPieceMetadata(PieceTestData.withShape(shapes.get(9)))
                .withCommonMetadata(CommonMetadata.create("피스5", "스페이스4의 두번째 피스")).buildNode();

        Piece 피스6 = defaultPiece()
                .withPieceID(new PieceID(UuidCreator.getTimeOrderedEpoch(), 6L))
                .withUniverseID(universeID)
                .withParentSpaceID(spaces.get(4).getSpaceID())
                .withPieceMetadata(PieceTestData.withShape(shapes.get(10)))
                .withCommonMetadata(CommonMetadata.create("피스6", "스페이스5의 첫번째 피스")).buildNode();

        Piece 피스7 = defaultPiece()
                .withPieceID(new PieceID(UuidCreator.getTimeOrderedEpoch(), 7L))
                .withUniverseID(universeID)
                .withParentSpaceID(spaces.get(4).getSpaceID())
                .withPieceMetadata(PieceTestData.withShape(shapes.get(11)))
                .withCommonMetadata(CommonMetadata.create("피스7", "스페이스5의 두번째 피스")).buildNode();


        return new ArrayList<>(List.of(피스1, 피스2, 피스3, 피스4, 피스5, 피스6, 피스7));
    }

    /*
     * 트리구조
     Universe
        ├── Space1
        │   ├── Piece2
        │   └── Space3
        │       └── Piece3
        ├── Space2
        │   ├── Space4
        │   │   ├── Piece4
        │   │   └── Piece5
        │   └── Space5
        │       ├── Piece6
        │       └── Piece7
        └── Piece1
     */

    public static Universe getTreeUniverse() {
        Universe universe = defaultUniverse()
                .withUniverseId(new UniverseID(UuidCreator.getTimeOrderedEpoch(), 1L))
                .build();

        List<Space> spaces = defaultSpaceNodes(universe.getUniverseID());
        List<Piece> pieces = defaultPieceNodes(universe.getUniverseID(), spaces);

        return universe.loadTree(universe, spaces, pieces);
    }

    @Test
    @DisplayName("유니버스 전체 트리 불러오기")
    void loadUniverseTree() {
        // given
        Universe treeUniverse = getTreeUniverse();

        // then
        assertThat(treeUniverse.getSpaces()).hasSize(2)
                .anyMatch(space -> space.getCommonMetadata().getTitle().equals("스페이스1"))
                .anyMatch(space -> space.getCommonMetadata().getTitle().equals("스페이스2"));
        assertThat(treeUniverse.getPieces()).hasSize(1)
                .anyMatch(piece -> piece.getCommonMetadata().getTitle().equals("피스1"));

        Space firstChild = treeUniverse.getSpaces().get(0);
        Space secondChild = treeUniverse.getSpaces().get(1);

        assertThat(firstChild.getSpaces()).hasSize(1)
                .anyMatch(space -> space.getCommonMetadata().getTitle().equals("스페이스3"));

        assertThat(firstChild.getPieces()).hasSize(1)
                .anyMatch(piece -> piece.getCommonMetadata().getTitle().equals("피스2"));

        assertThat(secondChild.getSpaces()).hasSize(2)
                .anyMatch(space -> space.getCommonMetadata().getTitle().equals("스페이스4"))
                .anyMatch(space -> space.getCommonMetadata().getTitle().equals("스페이스5"));

        assertThat(secondChild.getPieces()).isEmpty();
    }

    @Test
    @DisplayName("특정 스페이스 검색")
    void getSpace() {
        // given
        Universe treeUniverse = getTreeUniverse();

        // when
        Space 스페이스2 = treeUniverse.getSpaces().getLast();
        Space 스페이스3 = treeUniverse.getSpaces().getFirst().getSpaces().getFirst();

        // then
        assertThat(treeUniverse.getSpace(스페이스2.getSpaceID())).isEqualTo(스페이스2);
        assertThat(treeUniverse.getSpace(스페이스3.getSpaceID())).isEqualTo(스페이스3);
    }

    @Test
    @DisplayName("특정 피스 검색")
    void getPiece() {
        // given
        Universe treeUniverse = getTreeUniverse();

        // when
        Piece 피스2 = treeUniverse.getSpaces().getFirst().getPieces().getFirst();
        Piece 피스3 = treeUniverse.getSpaces().getFirst().getSpaces().getFirst().getPieces().getFirst();

        // then
        assertThat(treeUniverse.getPiece(피스2.getPieceID())).isEqualTo(피스2);
        assertThat(treeUniverse.getPiece(피스3.getPieceID())).isEqualTo(피스3);
    }

    @Test
    @DisplayName("스페이스 이동")
    void moveSpace() {
        // given
        Universe universe = getTreeUniverse();
        Space 스페이스2 = universe.getSpaces().getLast();
        Shape newShape = Shape.getRectangleBy2Point(Point.of(0.7f, 0.7f), Point.of(0.8f, 0.8f));
        Shape overlappedShape = Shape.getRectangleBy2Point(Point.of(0.1f, 0.1f), Point.of(0.2f, 0.2f));

        // when
        SpaceMoveEvent event = universe.moveSpace(스페이스2.getSpaceID(), newShape);
        SpaceMoveEvent event2 = universe.moveSpace(스페이스2.getSpaceID(), overlappedShape);

        // then
        assertThat(event.isMoved()).isTrue();
        assertThat(event.isOverlapped()).isFalse();
        assertThat(event.shape()).isEqualTo(newShape);
        assertThat(스페이스2.getSpaceMetadata().getShape()).isEqualTo(newShape);

        assertThat(event2.isMoved()).isFalse();
        assertThat(event2.isOverlapped()).isTrue();
        assertThat(event2.shape()).isNull();
    }

    @Test
    @DisplayName("피스 이동")
    void movePiece() {
        // given
        Universe universe = getTreeUniverse();
        Piece 피스2 = universe.getSpaces().getFirst().getPieces().getFirst();
        Shape newShape = Shape.getRectangleBy2Point(Point.of(0.7f, 0.7f), Point.of(0.8f, 0.8f));
        Shape overlappedShape = Shape.getRectangleBy2Point(Point.of(0.1f, 0.5f), Point.of(0.2f, 0.6f));

        // when
        PieceMoveEvent event = universe.movePiece(피스2.getPieceID(), newShape);
        PieceMoveEvent event2 = universe.movePiece(피스2.getPieceID(), overlappedShape);

        // then
        assertThat(event.isMoved()).isTrue();
        assertThat(event.isOverlapped()).isFalse();
        assertThat(event.shape()).isEqualTo(newShape);
        assertThat(피스2.getPieceMetadata().getShape()).isEqualTo(newShape);

        assertThat(event2.isMoved()).isFalse();
        assertThat(event2.isOverlapped()).isTrue();
        assertThat(event2.shape()).isNull();
    }
}
