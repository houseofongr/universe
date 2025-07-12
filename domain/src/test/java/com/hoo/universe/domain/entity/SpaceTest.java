package com.hoo.universe.domain.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.domain.entity.Space.SpaceID;
import com.hoo.universe.domain.event.SpaceFileOverwriteEvent;
import com.hoo.universe.domain.event.SpaceMetadataUpdateEvent;
import org.assertj.core.data.TemporalUnitLessThanOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.hoo.universe.domain.entity.PieceTestData.defaultPiece;
import static com.hoo.universe.domain.entity.SpaceTestData.defaultSpace;
import static org.assertj.core.api.Assertions.assertThat;

class SpaceTest {

    @Test
    @DisplayName("스페이스 생성")
    void createSpace() {
        // given
        Space space = defaultSpace().build();
        Space space2 = defaultSpace().build();

        // when
        boolean result = space.createSpaceInside(space2);

        // then
        assertThat(result).isTrue();
        assertThat(space.getSpaces()).hasSize(1);
    }

    @Test
    @DisplayName("같은 스페이스 확인 테스트")
    void testEquals() {
        // given
        UUID uuid = UuidCreator.getTimeOrderedEpoch();

        // when
        Space space1 = defaultSpace().withSpaceId(new SpaceID(uuid, 123L)).build();
        Space space2 = defaultSpace().withSpaceId(new SpaceID(uuid, 124L)).build();

        // then
        assertThat(space1).isEqualTo(space2);
    }

    @Test
    @DisplayName("피스 생성")
    void createPiece() {
        // given
        Space space = defaultSpace().build();
        Piece piece = defaultPiece().build();

        // when
        boolean result = space.createPieceInside(piece);

        // then
        assertThat(result).isTrue();
        assertThat(space.getPieces()).hasSize(1);
    }

    @Test
    @DisplayName("스페이스 ID 비교(equals)")
    void equalsSpaceID() {
        // given
        UUID uuid = UuidCreator.getTimeOrderedEpoch();

        // when
        SpaceID spaceID1 = new SpaceID(uuid, 1L);
        SpaceID spaceID2 = new SpaceID(uuid, 2L);

        // then
        assertThat(spaceID1).isEqualTo(spaceID2);
    }

    @Test
    @DisplayName("스페이스 상세정보 수정")
    void updateSpaceMetadata() {
        // given
        Space space = defaultSpace().build();

        String title = "수정";
        String description = "수정된 내용";
        Boolean hidden = true;

        // when
        SpaceMetadataUpdateEvent event = space.updateMetadata(null, null, hidden);
        SpaceMetadataUpdateEvent event2 = space.updateMetadata(title, description, null);

        // then
        assertThat(event.title()).isEqualTo("공간");
        assertThat(event.description()).isEqualTo("스페이스는 공간입니다.");
        assertThat(event.hidden()).isTrue();
        assertThat(event.updatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitLessThanOffset(100L, ChronoUnit.MILLIS));

        assertThat(event2.title()).isEqualTo(title);
        assertThat(event2.description()).isEqualTo(description);
        assertThat(event2.hidden()).isFalse();

        assertThat(space.getCommonMetadata().getTitle()).isEqualTo(title);
        assertThat(space.getCommonMetadata().getDescription()).isEqualTo(description);
        assertThat(space.getSpaceMetadata().isHidden()).isFalse();
    }

    @Test
    @DisplayName("스페이스 파일 덮어쓰기")
    void overwriteSpaceFile() {
        // given
        Space space = defaultSpace().build();
        Long innerImageID = 123L;

        // when
        SpaceFileOverwriteEvent event = space.overwriteFile(innerImageID);

        // then
        assertThat(event.oldInnerImageID()).isEqualTo(10L);
        assertThat(event.newInnerImageID()).isEqualTo(123L);
    }

}