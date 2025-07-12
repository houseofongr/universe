package com.hoo.universe.domain;

import com.hoo.universe.domain.entity.Piece;
import com.hoo.universe.domain.entity.PieceTestData;
import com.hoo.universe.domain.entity.Space;
import com.hoo.universe.domain.entity.SpaceTestData;
import com.hoo.universe.domain.event.UniverseFileOverwriteEvent;
import com.hoo.universe.domain.event.UniverseMetadataUpdateEvent;
import com.hoo.universe.domain.vo.AccessStatus;
import com.hoo.universe.domain.vo.Point;
import com.hoo.universe.domain.vo.Shape;
import org.assertj.core.data.TemporalUnitLessThanOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.hoo.universe.domain.UniverseTestData.defaultUniverse;
import static com.hoo.universe.domain.entity.PieceTestData.*;
import static com.hoo.universe.domain.entity.SpaceTestData.*;
import static org.assertj.core.api.Assertions.*;

class UniverseTest {

    @Test
    @DisplayName("스페이스 최대 10개생성 가능")
    void maxSpace10() {
        Universe universe = defaultUniverse().build();
        List<Space> spaceList = new ArrayList<>();
        for (float i = 0; i <= 0.1f; i += 0.01f) {
            Shape shape = Shape.getRectangleBy2Point(Point.of(i, i), Point.of(i + 0.01f, i + 0.01f));
            spaceList.add(defaultSpace().withSpaceMetadata(SpaceTestData.withShape(shape)).build());
        }

        for (int i = 0; i < 10; i++) {
            boolean result = universe.createSpaceInside(spaceList.get(i));
            assertThat(result).isTrue();
        }

        boolean result = universe.createSpaceInside(spaceList.getLast());
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("피스 최대 100개 생성 가능")
    void maxPiece100() {
        Universe universe = defaultUniverse().build();
        List<Piece> pieceList = new ArrayList<>();
        for (float i = 0; i <= 0.1f; i += 0.001f) {
            Shape shape = Shape.getRectangleBy2Point(Point.of(i, i), Point.of(i + 0.001f, i + 0.001f));
            pieceList.add(defaultPiece().withPieceMetadata(PieceTestData.withShape(shape)).build());
        }

        for (int i = 0; i < 100; i++) {
            boolean result = universe.createPieceInside(pieceList.get(i));
            assertThat(result).isTrue();
        }

        boolean result = universe.createPieceInside(pieceList.getLast());
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("공간 겹칠 수 없음")
    void cannotOverlapped() {
        // given
        Universe universe = defaultUniverse().build();
        Shape shape1 = Shape.getRectangleBy2Point(Point.of(0.3f, 0.2f), Point.of(0.5f, 0.6f));
        Shape shape2 = Shape.getRectangleBy2Point(Point.of(0.35f, 0.25f), Point.of(0.4f, 0.5f));
        Shape shape3 = Shape.getRectangleBy2Point(Point.of(0.5f, 0.6f), Point.of(0.7f, 0.7f));

        Space space1 = defaultSpace().withSpaceMetadata(SpaceTestData.withShape(shape1)).build();
        Space space2 = defaultSpace().withSpaceMetadata(SpaceTestData.withShape(shape2)).build();
        Piece piece1 = defaultPiece().withPieceMetadata(PieceTestData.withShape(shape1)).build();
        Piece piece2 = defaultPiece().withPieceMetadata(PieceTestData.withShape(shape1)).build();
        Piece piece3 = defaultPiece().withPieceMetadata(PieceTestData.withShape(shape3)).build();

        // then
        assertThat(universe.createSpaceInside(space1)).isTrue();
        assertThat(universe.createSpaceInside(space2)).isFalse();
        assertThat(universe.createPieceInside(piece1)).isFalse();
        assertThat(universe.createPieceInside(piece2)).isFalse();
        assertThat(universe.createPieceInside(piece3)).isTrue();
    }

    @Test
    @DisplayName("스페이스 생성")
    void createSpace() {
        // given
        Universe universe = defaultUniverse().build();
        Space space = defaultSpace().build();

        // when
        boolean result = universe.createSpaceInside(space);

        // then
        assertThat(result).isTrue();
        assertThat(universe.getSpaces()).hasSize(1);
    }

    @Test
    @DisplayName("피스 생성")
    void createPiece() {
        // given
        Universe universe = defaultUniverse().build();
        Piece piece = defaultPiece().build();

        // when
        boolean result = universe.createPieceInside(piece);

        // then
        assertThat(result).isTrue();
        assertThat(universe.getPieces()).hasSize(1);
    }

    @Test
    @DisplayName("유니버스 상세정보 수정")
    void updateUniverseMetadata() {
        // given
        Universe universe = defaultUniverse().build();

        String title = "수정";
        String description = "수정된 내용";
        AccessStatus accessStatus = AccessStatus.PRIVATE;
        List<String> tags = List.of("수", "정");

        // when
        UniverseMetadataUpdateEvent event = universe.updateMetadata(title, description, accessStatus, tags);

        // then
        assertThat(event.title()).isEqualTo(title);
        assertThat(event.description()).isEqualTo(description);
        assertThat(event.accessStatus()).isEqualTo(accessStatus);
        assertThat(event.tags()).isEqualTo(tags);
        assertThat(event.updatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitLessThanOffset(100L, ChronoUnit.MILLIS));

        assertThat(universe.getCommonMetadata().getTitle()).isEqualTo(title);
        assertThat(universe.getCommonMetadata().getDescription()).isEqualTo(description);
        assertThat(universe.getUniverseMetadata().getAccessStatus()).isEqualTo(accessStatus);
        assertThat(universe.getUniverseMetadata().getTags()).isEqualTo(tags);
        assertThat(universe.getCommonMetadata().getUpdatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitLessThanOffset(100L, ChronoUnit.MILLIS));
    }

    @Test
    @DisplayName("유니버스 파일 덮어쓰기")
    void overwriteUniverseFile() {
        // given
        Universe universe = defaultUniverse().build();

        Long thumbmusicID = 5L;
        Long thumbnailID = 6L;
        Long innerImageID = 7L;

        // when
        UniverseFileOverwriteEvent event = universe.overwriteFiles(thumbmusicID, thumbnailID, innerImageID);

        // then
        assertThat(event.oldThumbmusicID()).isEqualTo(1L);
        assertThat(event.oldThumbnailID()).isEqualTo(2L);
        assertThat(event.oldInnerImageID()).isEqualTo(3L);
        assertThat(event.newThumbmusicID()).isEqualTo(thumbmusicID);
        assertThat(event.newThumbnailID()).isEqualTo(thumbnailID);
        assertThat(event.newInnerImageID()).isEqualTo(innerImageID);

        assertThat(universe.getUniverseMetadata().getThumbmusicID()).isEqualTo(thumbmusicID);
        assertThat(universe.getUniverseMetadata().getThumbnailID()).isEqualTo(thumbnailID);
        assertThat(universe.getUniverseMetadata().getInnerImageID()).isEqualTo(innerImageID);

        UniverseFileOverwriteEvent changeThumbmusic = universe.overwriteFiles(123L, null, null);

        assertThat(changeThumbmusic.oldThumbmusicID()).isEqualTo(5L);
        assertThat(changeThumbmusic.newThumbmusicID()).isEqualTo(123L);
        assertThat(changeThumbmusic.oldThumbnailID()).isEqualTo(changeThumbmusic.newThumbnailID()).isNull();
        assertThat(changeThumbmusic.oldInnerImageID()).isEqualTo(changeThumbmusic.newInnerImageID()).isNull();

        UniverseFileOverwriteEvent changeThumbnail = universe.overwriteFiles(null, 123L, null);

        assertThat(changeThumbnail.oldThumbnailID()).isEqualTo(6L);
        assertThat(changeThumbnail.newThumbnailID()).isEqualTo(123L);
        assertThat(changeThumbnail.oldThumbmusicID()).isEqualTo(changeThumbnail.newThumbmusicID()).isNull();
        assertThat(changeThumbnail.oldInnerImageID()).isEqualTo(changeThumbnail.newInnerImageID()).isNull();

        UniverseFileOverwriteEvent changeInnerImage = universe.overwriteFiles(null, null, 123L);

        assertThat(changeInnerImage.oldInnerImageID()).isEqualTo(7L);
        assertThat(changeInnerImage.newInnerImageID()).isEqualTo(123L);
        assertThat(changeInnerImage.oldThumbmusicID()).isEqualTo(changeInnerImage.newThumbmusicID()).isNull();
        assertThat(changeInnerImage.oldThumbnailID()).isEqualTo(changeInnerImage.newThumbnailID()).isNull();
    }

}