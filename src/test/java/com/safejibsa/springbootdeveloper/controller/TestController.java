package com.safejibsa.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safejibsa.springbootdeveloper.domain.Article;
import com.safejibsa.springbootdeveloper.dto.AddArticleRequest;
import com.safejibsa.springbootdeveloper.old.Member;
import com.safejibsa.springbootdeveloper.old.MemberRepository;
import com.safejibsa.springbootdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    protected void beforeEach(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    protected void afterEach(){
        blogRepository.deleteAll();
    }

    @Test
    @DisplayName("테스트 코드 생성-INSERT(POST) 테스트")
    void blogApiControllerTest() throws Exception {
        //given
        final String url = "/api/articles";
        final String title = "테스트 제목";
        final String content = "테스트 내용";

        AddArticleRequest addArticleRequest = new AddArticleRequest(title, content);
        String requestBody = objectMapper.writeValueAsString(addArticleRequest);


        //when
        ResultActions perform = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        //then
        perform.andExpect(status().isCreated());
        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("테스트 코드 생성-SELECT(GET) 테스트")
    void memberApiControllerTest() throws Exception {
        //given
        final String url = "/test";
        Member savedMember = memberRepository.save(new Member(1L, "홍길동"));

        //when
        ResultActions perform = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(savedMember.getId()))
                .andExpect(jsonPath("$[0].name").value(savedMember.getName()));
    }
}
