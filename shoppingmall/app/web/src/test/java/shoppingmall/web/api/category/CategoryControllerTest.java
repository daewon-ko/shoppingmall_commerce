package shoppingmall.web.api.category;

import shoppingmall.web.api.category.dto.request.CategoryRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import shoppingmall.web.api.support.ControllerTestSupport;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest extends ControllerTestSupport {

    @DisplayName("카테고리를 생성한다.")
    @Test
    void createCategory() throws Exception {
        //given
        CategoryRequestDto createCategoryRequest = CategoryRequestDto.builder()
                .categoryName("test")
                .build();

        Mockito.when(categoryRdbService.createCategory(any(CategoryRequestDto.class)))
                .thenReturn(1L);




        //when, then
        mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCategoryRequest))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.httpStatus").value("CREATED"))
                .andExpect(jsonPath("$.message").value("CREATED"));


    }

    @DisplayName("카테고리를 생성 시, 카테고리 이름은 필수이다.")
    @Test
    void createCategoryWithWrongName() throws Exception {
        //given
        CategoryRequestDto createCategoryRequest = CategoryRequestDto.builder()
                .build();

        Mockito.when(categoryRdbService.createCategory(any(CategoryRequestDto.class)))
                .thenReturn(1L);





        //when, then
        mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCategoryRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("이름은 공백일 수 없습니다."));


    }


}