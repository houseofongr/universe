package com.hoo.universe.adapter.in.web.space;

import com.hoo.universe.adapter.in.web.RequestMapper;
import com.hoo.universe.api.in.web.dto.command.CreateSpaceWithTwoPointCommand;
import com.hoo.universe.api.in.web.dto.result.CreateSpaceResult;
import com.hoo.universe.api.in.web.usecase.CreateSpaceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CreateSpaceController {

    private final CreateSpaceUseCase useCase;
    private final RequestMapper requestMapper;

    @PostMapping("/universes/{universeID}/spaces")
    public ResponseEntity<CreateSpaceResult> create(
            @PathVariable UUID universeID,
            @RequestParam(required = false) UUID parentSpaceID,
            @RequestParam String metadata,
            @RequestParam("background") MultipartFile backgroundFile) {

        CreateSpaceWithTwoPointCommand command = new CreateSpaceWithTwoPointCommand(
                requestMapper.mapToCreateSpaceCommandMetadata(metadata),
                requestMapper.mapToFileCommand(backgroundFile));

        return new ResponseEntity<>(useCase.createSpaceWithTwoPoint(universeID, parentSpaceID, command), HttpStatus.CREATED);
    }
}
