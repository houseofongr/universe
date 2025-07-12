package com.hoo.universe.domain.event;

public record SpaceFileOverwriteEvent(
        Long oldInnerImageID,
        Long newInnerImageID
) {
}
