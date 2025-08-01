package com.hoo.universe.adapter.in.web;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.enums.AccessLevel;
import com.hoo.universe.api.in.dto.CreateUniverseCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CreateUniverseMetadataDocumentationController {

    @GetMapping("/universes/create-universe-metadata")
    ResponseEntity<CreateUniverseCommand.Metadata> getMetadata() {
        return ResponseEntity.ok(new CreateUniverseCommand.Metadata(
                "생성할 유니버스의 제목",
                "생성할 유니버스의 내용",
                UuidCreator.getTimeOrderedEpoch(),
                UuidCreator.getTimeOrderedEpoch(),
                AccessLevel.PUBLIC.name(),
                List.of("태그1", "태그2")
        ));
    }
}
