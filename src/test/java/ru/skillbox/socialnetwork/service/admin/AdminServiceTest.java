package ru.skillbox.socialnetwork.service.admin;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.skillbox.socialnetwork.data.dto.admin.PersonStatisticResponse;
import ru.skillbox.socialnetwork.data.dto.admin.StatisticRequest;
import ru.skillbox.socialnetwork.data.dto.admin.StatisticResponse;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.Post;
import ru.skillbox.socialnetwork.data.entity.PostComment;
import ru.skillbox.socialnetwork.data.entity.PostLike;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.data.repository.PostCommentsRepository;
import ru.skillbox.socialnetwork.data.repository.PostLikesRepository;
import ru.skillbox.socialnetwork.data.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AdminServiceTest {
    @Autowired
    private AdminService adminService;

    @MockBean
    private PostRepository postRepository;
    @MockBean
    private PersonRepo personRepository;
    @MockBean
    private PostCommentsRepository postCommentsRepository;
    @MockBean
    private PostLikesRepository postLikesRepository;

    private static Post postFirst;
    private static Post postSecond;
    private static Person personFirst;
    private static Person personSecond;
    private static PostComment postCommentFirst;
    private static PostComment postCommentSecond;
    private static PostLike postLikeFirst;
    private static PostLike postLikeSecond;


    @BeforeAll
    static void init(){
        personFirst = new Person();
        personFirst.setId(1L);
        personFirst.setGender("Female");
        personFirst.setBirthTime(LocalDateTime.of(1993, 12, 11, 0, 0));
        personFirst.setRegTime(LocalDateTime.of(2021, 1, 1, 1, 0));

        personSecond = new Person();
        personSecond.setId(2L);
        personSecond.setGender("Male");
        personSecond.setBirthTime(LocalDateTime.of(2000, 1, 1, 0, 0));
        personSecond.setRegTime(LocalDateTime.of(2011, 2, 2, 2, 0));

        postFirst = new Post();
        postFirst.setTime(LocalDateTime.of(2021, 1, 1, 1, 0));

        postSecond = new Post();
        postSecond.setTime(LocalDateTime.of(2011, 2, 2, 2, 0));

        postCommentFirst = new PostComment();
        postCommentFirst.setPost(postFirst);
        postCommentFirst.setAuthor(personFirst);
        postCommentFirst.setTime(LocalDateTime.of(2021, 1, 1, 1, 0));
        postCommentFirst.setId(1L);

        postCommentSecond = new PostComment();
        postCommentFirst.setPost(postSecond);
        postCommentSecond.setAuthor(personSecond);
        postCommentSecond.setTime(LocalDateTime.of(2011, 2, 2, 2, 0));
        postCommentSecond.setId(1L);

        postLikeFirst = new PostLike();
        postLikeFirst.setPost(postFirst);
        postLikeFirst.setPerson(personFirst);
        postLikeFirst.setTime(LocalDateTime.of(2021, 1, 1, 1, 0));

        postLikeSecond = new PostLike();
        postLikeSecond.setPost(postSecond);
        postLikeSecond.setPerson(personSecond);
        postLikeSecond.setTime(LocalDateTime.of(2011, 2, 2, 2, 0));
    }

    @Test
    void getPostStatistic_With_graphPeriod_years() {
          Mockito.when(postRepository.findAllByTimeBetweenDates(Mockito.any(), Mockito.any())).thenReturn(List.of(postFirst, postSecond));
          Mockito.when(postRepository.count()).thenReturn(2L);
          Mockito.when(postRepository.findAll()).thenReturn(List.of(postFirst, postSecond));
          StatisticRequest request = StatisticRequest.builder()
                .dateFromGraph("2011-01-03T02:00:00 04:00")
                .dateToGraph("2021-01-03T01:00:19 04:00")
                .graphPeriod("years")
                .dateFromDiagram("2011-01-03T02:00:00 04:00")
                .dateToDiagram("2021-01-03T01:00:19 04:00")
                .diagramPeriod("allTime")
                .build();

        StatisticResponse response = adminService.getPostStatistic(request);
        assertEquals(2, response.getTotalDataCount());
        assertEquals(2, response.getFoundDataCount());
        assertEquals(11, response.getGraphData().size());
        assertEquals(24, response.getDataByHour().size());
        assertEquals(50.0, response.getDataByHour().get(1));
        assertEquals(50.0, response.getDataByHour().get(2));
    }

    @Test
    void getPostStatistic_With_graphPeriod_months() {
        Mockito.when(postRepository.findAllByTimeBetweenDates(Mockito.any(), Mockito.any())).thenReturn(List.of(postFirst));
        Mockito.when(postRepository.count()).thenReturn(2L);
        Mockito.when(postRepository.findAll()).thenReturn(List.of(postFirst, postSecond));
        StatisticRequest request = StatisticRequest.builder()
                .dateFromGraph("2020-01-03T02:00:00 04:00")
                .dateToGraph("2021-01-03T01:00:19 04:00")
                .graphPeriod("months")
                .dateFromDiagram("2011-01-03T02:00:00 04:00")
                .dateToDiagram("2021-01-03T01:00:19 04:00")
                .diagramPeriod("allTime")
                .build();

        StatisticResponse response = adminService.getPostStatistic(request);
        assertEquals(2, response.getTotalDataCount());
        assertEquals(1, response.getFoundDataCount());
        assertEquals(12, response.getGraphData().size());
        assertEquals(24, response.getDataByHour().size());
        assertEquals(50.0, response.getDataByHour().get(1));
        assertEquals(50.0, response.getDataByHour().get(2));
    }

    @Test
    void getPostStatistic_With_graphPeriod_days() {
        Mockito.when(postRepository.findAllByTimeBetweenDates(Mockito.any(), Mockito.any())).thenReturn(List.of(postFirst));
        Mockito.when(postRepository.count()).thenReturn(2L);
        Mockito.when(postRepository.findAll()).thenReturn(List.of(postFirst, postSecond));
        StatisticRequest request = StatisticRequest.builder()
                .dateFromGraph("2021-01-01T00:00:00 04:00")
                .dateToGraph("2021-01-03T01:00:19 04:00")
                .graphPeriod("days")
                .dateFromDiagram("2021-01-01T02:00:00 04:00")
                .dateToDiagram("2021-01-03T01:00:19 04:00")
                .diagramPeriod("allTime")
                .build();

        StatisticResponse response = adminService.getPostStatistic(request);
        assertEquals(2, response.getTotalDataCount());
        assertEquals(1, response.getFoundDataCount());
        assertEquals(3, response.getGraphData().size());
        assertEquals(24, response.getDataByHour().size());
        assertEquals(50.0, response.getDataByHour().get(1));
        assertEquals(50.0, response.getDataByHour().get(2));
    }

    @Test
    void getPersonStatistic_With_graphPeriod_years(){
        Mockito.when(personRepository.findAllByRegTimeBetweenDates(Mockito.any(), Mockito.any())).thenReturn(List.of(personFirst, personSecond));
        Mockito.when(personRepository.count()).thenReturn(2L);
        StatisticRequest request = StatisticRequest.builder()
                .dateFromGraph("2010-01-01T01:00:19 05:00")
                .dateToGraph("2021-01-01T01:00:19 05:00")
                .graphPeriod("years")
                .build();

        PersonStatisticResponse response = adminService.getPersonStatistic(request);
        assertEquals(2, response.getTotalPersonCount());
        assertEquals(2, response.getFoundPersonCount());
        assertEquals(12, response.getPersonGraphData().size());
        assertEquals(6, response.getAgeDistribution().size());
        assertEquals(50.0, response.getAgeDistribution().get("24-30"));
        assertEquals(50.0, response.getAgeDistribution().get("19-23"));
        assertEquals(2, response.getSexDistribution().size());
        assertEquals(50.0, response.getSexDistribution().get("Мужчины"));
        assertEquals(50.0, response.getSexDistribution().get("Женщины"));
    }
    @Test
    void getPersonStatistic_With_graphPeriod_months(){
        Mockito.when(personRepository.findAllByRegTimeBetweenDates(Mockito.any(), Mockito.any())).thenReturn(List.of(personFirst));
        Mockito.when(personRepository.count()).thenReturn(2L);
        StatisticRequest request = StatisticRequest.builder()
                .dateFromGraph("2020-01-01T01:00:19 05:00")
                .dateToGraph("2021-01-01T01:00:19 05:00")
                .graphPeriod("months")
                .build();

        PersonStatisticResponse response = adminService.getPersonStatistic(request);
        assertEquals(2, response.getTotalPersonCount());
        assertEquals(1, response.getFoundPersonCount());
        assertEquals(12, response.getPersonGraphData().size());
        assertEquals(6, response.getAgeDistribution().size());
        assertEquals(100.0, response.getAgeDistribution().get("24-30"));
        assertEquals(0.0, response.getAgeDistribution().get("19-23"));
        assertEquals(2, response.getSexDistribution().size());
        assertEquals(0.0, response.getSexDistribution().get("Мужчины"));
        assertEquals(100.0, response.getSexDistribution().get("Женщины"));
    }

    @Test
    void getPersonStatistic_With_graphPeriod_days(){
        Mockito.when(personRepository.findAllByRegTimeBetweenDates(Mockito.any(), Mockito.any())).thenReturn(List.of(personFirst));
        Mockito.when(personRepository.count()).thenReturn(2L);
        StatisticRequest request = StatisticRequest.builder()
                .dateFromGraph("2020-12-28T01:00:19 05:00")
                .dateToGraph("2021-01-01T01:00:19 05:00")
                .graphPeriod("days")
                .build();

        PersonStatisticResponse response = adminService.getPersonStatistic(request);
        assertEquals(2, response.getTotalPersonCount());
        assertEquals(1, response.getFoundPersonCount());
        assertEquals(4, response.getPersonGraphData().size());
        assertEquals(6, response.getAgeDistribution().size());
        assertEquals(100.0, response.getAgeDistribution().get("24-30"));
        assertEquals(0.0, response.getAgeDistribution().get("19-23"));
//        assertEquals(2, response.getSexDistribution().size());
//        assertEquals(50, response.getSexDistribution().get("мужчины"));
//        assertEquals(50, response.getSexDistribution().get("женщины"));
    }

    @Test
    void getPostCommentStatistic_With_graphPeriod_years(){
        Mockito.when(postCommentsRepository.findAllByTimeBetweenDates(Mockito.any(), Mockito.any())).thenReturn(List.of(postCommentFirst, postCommentSecond));
        Mockito.when(postCommentsRepository.findAll()).thenReturn(List.of(postCommentFirst, postCommentSecond));
        Mockito.when(postCommentsRepository.count()).thenReturn(2L);
        StatisticRequest request = StatisticRequest.builder()
                .dateFromGraph("2011-01-03T02:00:00 04:00")
                .dateToGraph("2021-01-03T01:00:19 04:00")
                .graphPeriod("years")
                .dateFromDiagram("2011-01-03T02:00:00 04:00")
                .dateToDiagram("2021-01-03T01:00:19 04:00")
                .diagramPeriod("allTime")
                .build();

        StatisticResponse response = adminService.getCommentStatistic(request);
        assertEquals(2, response.getTotalDataCount());
        assertEquals(2, response.getFoundDataCount());
        assertEquals(11, response.getGraphData().size());
        assertEquals(24, response.getDataByHour().size());
        assertEquals(50.0, response.getDataByHour().get(1));
        assertEquals(50.0, response.getDataByHour().get(2));
    }

    @Test
    void getPostCommentStatistic_With_graphPeriod_months(){
        Mockito.when(postCommentsRepository.findAllByTimeBetweenDates(Mockito.any(), Mockito.any())).thenReturn(List.of(postCommentFirst));
        Mockito.when(postCommentsRepository.count()).thenReturn(2L);
        Mockito.when(postCommentsRepository.findAll()).thenReturn(List.of(postCommentFirst, postCommentSecond));
        StatisticRequest request = StatisticRequest.builder()
                .dateFromGraph("2020-01-03T02:00:00 04:00")
                .dateToGraph("2021-01-03T01:00:19 04:00")
                .graphPeriod("months")
                .dateFromDiagram("2011-01-03T02:00:00 04:00")
                .dateToDiagram("2021-01-03T01:00:19 04:00")
                .diagramPeriod("allTime")
                .build();

        StatisticResponse response = adminService.getCommentStatistic(request);
        assertEquals(2, response.getTotalDataCount());
        assertEquals(1, response.getFoundDataCount());
        assertEquals(12, response.getGraphData().size());
        assertEquals(24, response.getDataByHour().size());
        assertEquals(50.0, response.getDataByHour().get(1));
        assertEquals(50.0, response.getDataByHour().get(2));
    }

    @Test
    void getPostCommentStatistic_With_graphPeriod_days(){
        Mockito.when(postCommentsRepository.findAllByTimeBetweenDates(Mockito.any(), Mockito.any())).thenReturn(List.of(postCommentFirst));
        Mockito.when(postCommentsRepository.count()).thenReturn(2L);
        Mockito.when(postCommentsRepository.findAll()).thenReturn(List.of(postCommentFirst, postCommentSecond));
        StatisticRequest request = StatisticRequest.builder()
                .dateFromGraph("2021-01-01T00:00:00 04:00")
                .dateToGraph("2021-01-03T01:00:19 04:00")
                .graphPeriod("days")
                .dateFromDiagram("2021-01-01T02:00:00 04:00")
                .dateToDiagram("2021-01-03T01:00:19 04:00")
                .diagramPeriod("allTime")
                .build();

        StatisticResponse response = adminService.getCommentStatistic(request);
        assertEquals(2, response.getTotalDataCount());
        assertEquals(1, response.getFoundDataCount());
        assertEquals(3, response.getGraphData().size());
        assertEquals(24, response.getDataByHour().size());
        assertEquals(50.0, response.getDataByHour().get(1));
        assertEquals(50.0, response.getDataByHour().get(2));
    }

    @Test
    void getPostLikesStatistic_With_graphPeriod_years(){
        Mockito.when(postLikesRepository.findAllByTimeBetweenDates(Mockito.any(), Mockito.any())).thenReturn(List.of(postLikeFirst, postLikeSecond));
        Mockito.when(postLikesRepository.findAll()).thenReturn(List.of(postLikeFirst, postLikeSecond));
        Mockito.when(postLikesRepository.count()).thenReturn(2L);
        StatisticRequest request = StatisticRequest.builder()
                .dateFromGraph("2011-01-03T02:00:00 04:00")
                .dateToGraph("2021-01-03T01:00:19 04:00")
                .graphPeriod("years")
                .dateFromDiagram("2011-01-03T02:00:00 04:00")
                .dateToDiagram("2021-01-03T01:00:19 04:00")
                .diagramPeriod("allTime")
                .build();

        StatisticResponse response = adminService.getLikeStatistic(request);
        assertEquals(2, response.getTotalDataCount());
        assertEquals(2, response.getFoundDataCount());
        assertEquals(11, response.getGraphData().size());
        assertEquals(24, response.getDataByHour().size());
        assertEquals(50.0, response.getDataByHour().get(1));
        assertEquals(50.0, response.getDataByHour().get(2));
    }
}