package com.hoo.universe.application;

import com.hoo.common.internal.message.DeleteFileEventPublisher;
import com.hoo.universe.api.in.dto.DeletePieceResult;
import com.hoo.universe.api.in.DeletePieceUseCase;
import com.hoo.universe.api.out.DeleteEntityPort;
import com.hoo.universe.api.out.UpdatePieceStatusPort;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Piece.PieceID;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.PieceDeleteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeletePieceService implements DeletePieceUseCase {

    private final LoadUniversePort loadUniversePort;
    private final DeleteEntityPort deleteEntityPort;
    private final DeleteFileEventPublisher deleteFileEventPublisher;

    @Override
    public DeletePieceResult deletePiece(UUID universeID, UUID pieceID) {
        Universe universe = loadUniversePort.loadUniverseWithAllEntity(universeID);
        Piece piece = universe.getPiece(new PieceID(pieceID));

        PieceDeleteEvent event = piece.delete();

        deleteEntityPort.deletePiece(event);
        deleteFileEventPublisher.publishDeleteFilesEvent(event.deleteFileIDs());

        return new DeletePieceResult(
                event.deletePieceID().uuid(),
                event.deleteSoundIDs().stream().map(Sound.SoundID::uuid).toList(),
                event.deleteFileIDs().size()
        );
    }
}
