package com.example.demo.web;

import com.example.demo.enums.StateType;
import com.example.demo.mock.InvestingMock;
import com.example.demo.mock.MockUtils;
import com.example.demo.mock.ProductMock;
import com.example.demo.service.InvestingService;
import com.example.demo.service.ProductService;
import com.example.demo.service.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InvestingResource.class)
class InvestingResourceTest {

  private MockMvc mockMvc;

  private final Long userId = 1L;

  @MockBean private ProductService productService;

  @MockBean private InvestingService investingService;

  @BeforeEach
  void init() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(new InvestingResource(productService, investingService))
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .build();
  }

  @Test
  @DisplayName("전체 투자 상품 조회 API")
  void showProductList() throws Exception {

    ProductInfoDTO dto = ProductMock.createdProductInfoDTO();

    Page<ProductInfo> mockList = ProductMock.cratedProductInfoToPage();

    BDDMockito.given(productService.getProductList(any())).willReturn(mockList);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .param("startedAtStr", dto.getStartedAtStr())
                    .param("finishedAtStr", dto.getFinishedAtStr())
                    .param("page", String.valueOf(dto.getPage()))
                    .param("size", String.valueOf(dto.getSize())))
            .andDo(print());

    action
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$['content'][0]['productId']")
                .value(mockList.getContent().get(0).getProductId()))
        .andExpect(
            jsonPath("$['content'][0]['title']").value(mockList.getContent().get(0).getTitle()))
        .andExpect(
            jsonPath("$['content'][0]['totalInvestingAmount']")
                .value(mockList.getContent().get(0).getTotalInvestingAmount()))
        .andExpect(
            jsonPath("$['content'][0]['currentInvestingAmount']")
                .value(mockList.getContent().get(0).getCurrentInvestingAmount()))
        .andExpect(
            jsonPath("$['content'][0]['startedAt']")
                .value(mockList.getContent().get(0).getStartedAt()))
        .andExpect(
            jsonPath("$['content'][0]['finishedAt']")
                .value(mockList.getContent().get(0).getFinishedAt()))
        .andExpect(
            jsonPath("$['content'][0]['state']")
                .value(mockList.getContent().get(0).getState().name()))
        .andExpect(
            jsonPath("$['content'][0]['memberCount']")
                .value(mockList.getContent().get(0).getMemberCount()));
  }

  @Test
  @DisplayName("투자하기 API")
  void investProduct() throws Exception {

    InvestProductDTO dto = InvestingMock.createdInvestProductDTO(1000);

    StateType mock = StateType.SOLDOUT;

    BDDMockito.given(investingService.investProduct(any(), any())).willReturn(mock);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/api/investing")
                    .content(MockUtils.asJsonString(dto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("X-USER-ID", 1)
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

    BDDMockito.then(investingService).should().investProduct(any(), any());

    action
        .andExpect(status().isOk())
        .andExpect(result -> result.getResponse().getContentAsString().equals(mock.name()));
  }

  @Test
  @DisplayName("나의 투자상품 조회 API")
  void showInvestingProductList() throws Exception {

    InvestingProductInfoDTO dto = InvestingMock.createdInvestingProductInfoDTO();

    Page<InvestingProductInfo> mockList = InvestingMock.createdPageMockList();

    BDDMockito.given(investingService.getInvestingProductList(any(), any())).willReturn(mockList);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/api/investing-products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .header("X-USER-ID", 1)
                    .param("page", String.valueOf(dto.getPage()))
                    .param("size", String.valueOf(dto.getSize())))
            .andDo(print());

    BDDMockito.then(investingService).should().getInvestingProductList(any(), any());

    action
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$['content'][0]['productId']")
                .value(mockList.getContent().get(0).getProductId()))
        .andExpect(
            jsonPath("$['content'][0]['title']").value(mockList.getContent().get(0).getTitle()))
        .andExpect(
            jsonPath("$['content'][0]['totalInvestingAmount']")
                .value(mockList.getContent().get(0).getTotalInvestingAmount()))
        .andExpect(
            jsonPath("$['content'][0]['investingAmount']")
                .value(mockList.getContent().get(0).getInvestingAmount()))
        .andExpect(
            jsonPath("$['content'][0]['investingDate']")
                .value(mockList.getContent().get(0).getInvestingDate()));
  }
}
