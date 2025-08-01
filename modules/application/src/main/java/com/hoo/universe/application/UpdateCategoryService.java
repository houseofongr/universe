package com.hoo.universe.application;

import com.hoo.universe.api.in.dto.UpdateCategoryCommand;
import com.hoo.universe.api.in.dto.UpdateCategoryResult;
import com.hoo.universe.api.in.UpdateCategoryUseCase;
import com.hoo.universe.api.out.UpdateCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateCategoryService implements UpdateCategoryUseCase {

    private final UpdateCategoryPort updateCategoryPort;

    @Override
    public UpdateCategoryResult updateCategory(UUID categoryID, UpdateCategoryCommand command) {

        updateCategoryPort.updateCategory(categoryID, command.kor(), command.eng());

        return new UpdateCategoryResult(
                categoryID,
                command.eng(),
                command.kor()
        );
    }
}
