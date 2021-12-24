package ru.skillbox.socialnetwork.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.skillbox.socialnetwork.data.dto.posts.GetUserPostsResponse;
import ru.skillbox.socialnetwork.data.entity.Country;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.Post;
import ru.skillbox.socialnetwork.data.entity.Town;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.data.repository.PostRepository;
import ru.skillbox.socialnetwork.data.repository.TagRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PostServiceImplTest {
    @Autowired
    private PostServiceImpl postService;

    @MockBean
    private PostRepository postRepository;
    @MockBean
    private PersonRepo personRepository;
    @MockBean
    private TagRepository tagRepository;

    private static Post postFirst;
    private static Post postSecond;
    private static Person author;
    private static Country country;
    private static Town town;

    static {
        country = new Country();
        country.setId(1L);
        country.setName("testCountry");
        town = new Town();
        town.setId(1L);
        town.setCountry(country);
        town.setName("testCity");
    }

    @BeforeAll
    static void init(){
        author = new Person();
        author.setId(1L);
        author.setBlocked(false);
        author.setApproved(true);
        author.setPhoto("photo");
        author.setAbout("about");
        author.setFirstName("FirstName");
        author.setLastName("LastName");
        author.setTown(town);
        author.setEmail("test@test.ru");
        author.setPassword("pass");
        author.setPhone("+12345678");
        author.setBirthTime(LocalDateTime.now());
        author.setRegTime(LocalDateTime.now());
        author.setLastOnlineTime(LocalDateTime.now());

        postFirst = new Post();
        postFirst.setAuthor(author);
        postFirst.setTime(LocalDateTime.now());
        postFirst.setTitle("titleFirst");
        postFirst.setTextHtml("textFirst");
        postFirst.setComments(new ArrayList<>());
        postFirst.setId(1L);
        postFirst.setLikes(new ArrayList<>());
        postFirst.setTags(new ArrayList<>());

        postSecond = new Post();
        postSecond.setAuthor(author);
        postSecond.setTime(LocalDateTime.now());
        postSecond.setTitle("titleSecond");
        postSecond.setTextHtml("textSecond");
        postSecond.setComments(new ArrayList<>());
        postSecond.setId(2L);
        postSecond.setLikes(new ArrayList<>());
        postSecond.setTags(new ArrayList<>());
    }

    @Test
    void getUserPosts_WithUserDeleted(){
        author.setDeleted(true);
        Mockito.when(personRepository.findById(Mockito.any())).thenReturn(Optional.of(author));
        GetUserPostsResponse response = postService.getUserPosts(1L, 0, 20);
        verify(personRepository, times(1)).findById(1L);
        assertEquals(0, response.getPosts().size());
    }

    @Test
    void searchPosts() {
        Page<Post> page = new PageImpl<>(List.of(postFirst, postSecond));
        Mockito.when(postRepository.findAllBySearchFilter(Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any()))
                .thenReturn(page);

        GetUserPostsResponse response = postService.searchPosts("text", "", "", "author", "0", "20", "");

        assertFalse(response.getPosts().isEmpty());
        assertEquals(2, response.getPosts().size());
        verify(postRepository, times(1)).findAllBySearchFilter(Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any());

    }
}