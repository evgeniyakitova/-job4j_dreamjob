package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostControllerTest {
    private PostService postService;
    private CityService cityService;
    private Model model;
    private HttpSession session;

    @BeforeEach
    public void createMocks() {
        postService = mock(PostService.class);
        cityService = mock(CityService.class);
        model = mock(Model.class);
        session = mock(HttpSession.class);
    }

    @Test
    public void whenGetPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post", "Description"),
                new Post(2, "New post", "Description")
        );
        when(postService.findAll()).thenReturn(posts);
        User user = new User(1, "email", "password", "name");
        when(session.getAttribute("user")).thenReturn(user);

        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        verify(model).addAttribute("user", user);
        assertEquals(page, "posts");
    }

    @Test
    public void whenGetFormAddPost() {
        List<City> cities = List.of(
                new City(1, "Москва"),
                new City(2, "СПб"),
                new City(3, "Екб")
        );
        when(cityService.getAllCities()).thenReturn(cities);
        PostController postController = new PostController(
                postService,
                cityService
        );

        User user = new User(1, "email", "password", "name");
        when(session.getAttribute("user")).thenReturn(user);

        String page = postController.addPost(model, session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("cities", cities);
        assertEquals(page, "addPost");
    }

    @Test
    public void whenCreatePost() {
        PostController postController = new PostController(
                postService,
                cityService
        );
        Post post = new Post(1, "New post", "Description");
        String page = postController.createPost(post);
        verify(postService).add(post);
        assertEquals(page, "redirect:/posts");
    }

    @Test
    public void whenGetFormUpdatePost() {
        List<City> cities = List.of(
                new City(1, "Москва"),
                new City(2, "СПб"),
                new City(3, "Екб")
        );
        when(cityService.getAllCities()).thenReturn(cities);

        Post post = new Post(1, "New post", "Description");
        when(postService.findById(post.getId())).thenReturn(post);
        PostController postController = new PostController(
                postService,
                cityService
        );

        User user = new User(1, "email", "password", "name");
        when(session.getAttribute("user")).thenReturn(user);

        String page = postController.formUpdatePost(model, post.getId(), session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("cities", cities);
        assertEquals(page, "updatePost");
    }

    @Test
    public void whenUpdatePost() {
        PostController postController = new PostController(
                postService,
                cityService
        );

        Post post = new Post(1, "New post", "Description");
        String page = postController.updatePost(post);

        verify(postService).update(post);
        assertEquals(page, "redirect:/posts");
    }

}