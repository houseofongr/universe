package com.hoo.universe.application.category;

import com.hoo.common.IssueIDPort;
import com.hoo.universe.api.in.web.dto.command.CreateCategoryCommand;
import com.hoo.universe.api.out.persistence.CommandCategoryPort;
import com.hoo.universe.application.CreateCategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class CreateCategoryServiceTest {

    IssueIDPort issueIDPort = mock();
    CommandCategoryPort commandCategoryPort = mock();
    CreateCategoryService sut = new CreateCategoryService(issueIDPort, commandCategoryPort);

    @Test
    @DisplayName("카테고리 생성 서비스")
    void testCreateCategoryService() {
        String kor = "새 카테고리";
        String eng = "new category";

        sut.createNewCategory(new CreateCategoryCommand(kor, eng));

        verify(commandCategoryPort, times(1)).saveNewCategory(any(), anyString(), anyString());
    }

}