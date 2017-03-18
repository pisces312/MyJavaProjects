package spring.boot.test.powermock;


import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import spring.boot.singlewebapp.SingleAppWithController;

@RunWith(PowerMockRunner.class)
// @PrepareForTest(SpringBootSampleController.class)
public class RestServerMockmvcPowerMockTest {
    private MockMvc mvc;


    @InjectMocks
    SingleAppWithController controller;


    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void test() throws Exception {
        RequestBuilder getRequest = get("/get").param("id", "123");
        ResultActions actions = mvc.perform(getRequest);
        actions.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.code", is(0))).andExpect(jsonPath("$.message", is("123")));
        System.out.println(actions.andReturn().getResponse().getContentAsString());
    }
}
