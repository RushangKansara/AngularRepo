package com.pioneer.userreg;

import java.nio.charset.Charset;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.pioneer.userreg.controller.UserController;
import com.pioneer.userreg.domain.UserDTO;
import com.pioneer.userreg.repository.UserRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = UserRegApplication.class)
public class UserRegistrationTest {
	
	@Autowired
	private MockMvc mockMVc;
	
	@MockBean
	private UserRepository userRepositoryMock;

	private MediaType contentType;
	private UserDTO user;
	
	@Before
	public void setup() {
		contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(),
                Charset.forName("utf8"));
		
		user = new UserDTO();
		user.setName("Rushang");
		user.setAddress("Mississauga");
		user.setEmail("rushang@gmail.com");
	}
	
	@Test
	public void test_get_all_users() throws Exception {
		
		Mockito.when(this.userRepositoryMock.findById(1L)).thenReturn(user);
		
		String userCredential =
                "user" + ":" + "password";
		byte[] base64UserCredentialData =
                Base64.encodeBase64(userCredential.getBytes());
		
		this.mockMVc.perform(MockMvcRequestBuilders.get("/api/user/id/1")
				.header("Authorization", "Basic " + new String(base64UserCredentialData)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Rushang")));
	}
}
