package spring.boot.test.springjunit;


import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import spring.boot.common.SetParam;
import spring.boot.singlewebapp.SingleAppWithController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SingleAppWithController.class)
// @SpringApplicationConfiguration(classes={spring.boot.SpringBootSampleControllerTest.class} )
// @SpringApplicationConfiguration(classes={MockServletContext.class,SpringBootSampleControllerTest.class}
// )
// @ComponentScan(basePackages={"spring.boot"})
// @SpringApplicationConfiguration(classes = MockServletContext.class)
// @WebAppConfiguration
public class SampleControllerAppTest {
    private MockMvc mvc;

    @Autowired
    SingleAppWithController controller;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller)
            .build();
    }

    @Test
    public void testSetRequestBody() throws Exception {
        SetParam setParam = new SetParam();
        Set<Object> set = new HashSet<>();

        set.add("a");
        set.add("b");
        set.add("c");
        setParam.setSet(set);

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(setParam);


        RequestBuilder getRequest = get("/getset").contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonStr);
        ResultActions actions = mvc.perform(getRequest);
        // actions.andExpect(status().isOk())
        // .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        // .andExpect(jsonPath("$.code", is(0))).andExpect(jsonPath("$.message", is("Success")));
        System.out.println(actions.andReturn()
            .getResponse()
            .getContentAsString());
    }

    @Test
    public void test() throws Exception {
        RequestBuilder getRequest = get("/get").param("id", "123");
        ResultActions actions = mvc.perform(getRequest);
        actions.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.code", is(0)))
            .andExpect(jsonPath("$.message", is("123")));
        System.out.println(actions.andReturn()
            .getResponse()
            .getContentAsString());
    }
}
