package com.mvc.controller;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.mvc.categories.UnitTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

/**
 * Test the Web controller
 * @author Jerome
 *
 */
@Category(UnitTest.class)
@RunWith(MockitoJUnitRunner.class)
public class WebControllerTest extends AbstractSpringMVCControllerTest<WebController> {

	private static final String VIEW_INDEX = "index";
	private static final String VIEW_HIBERNATE = "hibernate";
	private static final String VIEW_PERSON = "form";

	@InjectMocks
	private WebController controller;

	@Override
	protected WebController getController() {
		return this.controller;
	}

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testIndexWithoutParameters() throws Exception {

        this.mockMvc.perform(get("/"))
        	.andExpect(status().isOk())
        	.andExpect(status().isOk())
            .andExpect(view().name(VIEW_INDEX))
            .andExpect(model().attribute("name", "World"))
            .andReturn();
    }

    @Test
    public void testIndexWithNameParameters() throws Exception {

        this.mockMvc.perform(get(new URI("/?name=test%20name")))
        	.andExpect(status().isOk())
        	.andExpect(status().isOk())
            .andExpect(view().name(VIEW_INDEX))
            .andExpect(model().attribute("name", "test name"));
    }

    @Test
    public void testHibernateRouteShouldShowHibernateView() throws Exception {

        this.mockMvc.perform(get(new URI("/hibernate")))
        	.andExpect(status().isOk())
            .andExpect(view().name(VIEW_HIBERNATE));
    }

    @Test
    public void testPersonRouteShouldShowPersonView() throws Exception {

        this.mockMvc.perform(get(new URI("/person")))
        	.andExpect(status().isOk())
        	.andExpect(status().isOk())
            .andExpect(view().name(VIEW_PERSON));
    }

    @Test
    public void testCheckPersonRouteShouldRedirectToResultsIfNoErrors() throws Exception {

        this.mockMvc.perform(
        	post(new URI("/person/check"))
	        	.param("id", "1")
	        	.param("firstName", "fn")
	        	.param("lastName", "ln")
        )
		.andExpect(status().isMovedTemporarily())
        .andExpect(model().attributeHasNoErrors("person"))
        .andExpect(redirectedUrl("/results?name=fn+ln"));
    }

    @Test
    public void testCheckPersonRouteShouldGoBackToFormIfAllFieldsMissing() throws Exception {

        this.mockMvc.perform(
        	post(new URI("/person/check"))
        )
		.andExpect(status().isOk())
        .andExpect(model().attributeHasErrors("person"))
        .andExpect(model().attributeHasFieldErrors("person", "id", "firstName", "lastName"))
        .andExpect(view().name(VIEW_PERSON));
    }

    @Test
    public void testCheckPersonRouteShouldGoBackToFormIfIdIsNotGreaterThan0() throws Exception {

        this.mockMvc.perform(
        	post(new URI("/person/check"))
        	.param("id", "0")
        	.param("firstName", "fn")
        	.param("lastName", "ln")
        )
		.andExpect(status().isOk())
        .andExpect(model().attributeHasErrors("person"))
        .andExpect(model().attributeHasFieldErrors("person", "id"))
        .andExpect(view().name(VIEW_PERSON));
    }

    @Test
    public void testCheckPersonRouteShouldGoBackToFormIfFirstNameIsNotBetween2And30Chars() throws Exception {

        this.mockMvc.perform(
        	post(new URI("/person/check"))
        	.param("id", "1")
        	.param("firstName", "f")
        	.param("lastName", "ln")
        )
		.andExpect(status().isOk())
        .andExpect(model().attributeHasErrors("person"))
        .andExpect(model().attributeHasFieldErrors("person", "firstName"))
        .andExpect(view().name(VIEW_PERSON));

        this.mockMvc.perform(
        	post(new URI("/person/check"))
        	.param("id", "1")
        	.param("firstName", "fn012345678901234567890123456789")
        	.param("lastName", "ln")
        )
		.andExpect(status().isOk())
        .andExpect(model().attributeHasErrors("person"))
        .andExpect(model().attributeHasFieldErrors("person", "firstName"))
        .andExpect(view().name(VIEW_PERSON));
    }

    @Test
    public void testCheckPersonRouteShouldGoBackToFormIfLastNameIsNotBetween2And30Chars() throws Exception {

        this.mockMvc.perform(
        	post(new URI("/person/check"))
        	.param("id", "1")
        	.param("firstName", "fn")
        	.param("lastName", "l")
        )
		.andExpect(status().isOk())
        .andExpect(model().attributeHasErrors("person"))
        .andExpect(model().attributeHasFieldErrors("person", "lastName"))
        .andExpect(view().name(VIEW_PERSON));

        this.mockMvc.perform(
        	post(new URI("/person/check"))
        	.param("id", "1")
        	.param("firstName", "fn")
        	.param("lastName", "ln012345678901234567890123456789")
        )
		.andExpect(status().isOk())
        .andExpect(model().attributeHasErrors("person"))
        .andExpect(model().attributeHasFieldErrors("person", "lastName"))
        .andExpect(view().name(VIEW_PERSON));
    }

  
}
