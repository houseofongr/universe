package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.internal.message.DeleteFileEventPublisher;
import com.hoo.universe.api.out.DeleteEntityPort;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverse;
import static org.mockito.Mockito.*;

class DeletePieceServiceTest {

    LoadUniversePort loadUniversePort = mock();
    DeleteEntityPort deleteEntityPort = mock();
    DeleteFileEventPublisher deleteFileEventPublisher = mock();

    DeletePieceService sut = new DeletePieceService(loadUniversePort, deleteEntityPort, deleteFileEventPublisher);

    @Test
    @DisplayName("피스 삭제 서비스")
    void deletePieceService() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        Universe universe = defaultUniverse();
        UUID pieceID = universe.getPieces().getFirst().getId().uuid();

        // when
        when(loadUniversePort.loadUniverseWithAllEntity(universeID)).thenReturn(universe);
        sut.deletePiece(universeID, pieceID);

        // then
        verify(deleteEntityPort, times(1)).deletePiece(any());
        verify(deleteFileEventPublisher, times(1)).publishDeleteFilesEvent(anyList());
    }
}