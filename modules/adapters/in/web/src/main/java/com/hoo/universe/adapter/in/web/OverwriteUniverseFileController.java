package com.hoo.universe.adapter.in.web;

import com.hoo.common.internal.api.dto.FileCommand;
import com.hoo.universe.api.in.web.dto.result.OverwriteUniverseFileResult;
import com.hoo.universe.api.in.web.usecase.OverwriteUniverseFileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OverwriteUniverseFileController {

    private final OverwriteUniverseFileUseCase useCase;
    private final RequestMapper requestMapper;

    @PostMapping("/universes/{universeID}/thumbmusic")
    public ResponseEntity<OverwriteUniverseFileResult.Thumbmusic> updateThumbMusic(
            @PathVariable UUID universeID,
            @RequestPart(value = "thumbmusic") MultipartFile thumbmusicFile) {

        FileCommand thumbmusic = requestMapper.mapToFileCommand(thumbmusicFile);

        return ResponseEntity.ok(useCase.overwriteUniverseThumbmusic(universeID, thumbmusic));

    }

    @PostMapping("/universes/{universeID}/thumbnail")
    public ResponseEntity<OverwriteUniverseFileResult.Thumbnail> updateThumbnail(
            @PathVariable UUID universeID,
            @RequestPart(value = "thumbnail") MultipartFile thumbnailFile) {

        FileCommand thumbnail = requestMapper.mapToFileCommand(thumbnailFile);

        return ResponseEntity.ok(useCase.overwriteUniverseThumbnail(universeID, thumbnail));
    }

    @PostMapping("/universes/{universeID}/background")
    public ResponseEntity<OverwriteUniverseFileResult.Background> updateBackground(
            @PathVariable UUID universeID,
            @RequestPart(value = "background") MultipartFile backgroundFile) {

        FileCommand background = requestMapper.mapToFileCommand(backgroundFile);

        return ResponseEntity.ok(useCase.overwriteUniverseBackground(universeID, background));

    }

}
