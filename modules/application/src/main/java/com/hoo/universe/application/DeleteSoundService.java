package com.hoo.universe.application;

import com.hoo.common.internal.message.DeleteFileEventPublisher;
import com.hoo.universe.api.in.web.dto.result.DeleteSoundResult;
import com.hoo.universe.api.in.web.usecase.DeleteSoundUseCase;
import com.hoo.universe.api.out.persistence.HandleSoundEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Piece.PieceID;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.Sound.SoundID;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.sound.SoundDeleteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteSoundService implements DeleteSoundUseCase {

    private final LoadUniversePort loadUniversePort;
    private final HandleSoundEventPort handleSoundEventPort;
    private final DeleteFileEventPublisher deleteFileEventPublisher;

    @Override
    public DeleteSoundResult deleteSound(UUID universeID, UUID pieceID, UUID soundID) {

        Universe universe = loadUniversePort.loadUniverseWithAllEntity(universeID);
        Piece piece = universe.getPiece(new PieceID(pieceID));
        Sound sound = piece.getSound(new SoundID(soundID));

        SoundDeleteEvent event = sound.delete();

        handleSoundEventPort.handleSoundDeleteEvent(event);
        deleteFileEventPublisher.publishDeleteFilesEvent(event.deleteAudioFileID());

        return new DeleteSoundResult(event.deleteSoundID().uuid());
    }
}
