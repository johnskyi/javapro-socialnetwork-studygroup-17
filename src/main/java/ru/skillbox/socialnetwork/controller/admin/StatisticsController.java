package ru.skillbox.socialnetwork.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import ru.skillbox.socialnetwork.data.dto.admin.PersonStatisticResponse;
import ru.skillbox.socialnetwork.data.dto.admin.StatisticRequest;
import ru.skillbox.socialnetwork.data.dto.admin.StatisticResponse;
import ru.skillbox.socialnetwork.service.admin.AdminService;

@RestController
@Api(tags = "Статистика в админ панели")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasAuthority('user:administration')")
public class StatisticsController {

    private final AdminService adminService;

    @GetMapping("/api/v1/admin/getCommonStat")
    @ApiOperation(value = "Получить общую статистику")
    @CrossOrigin(allowCredentials = "true", origins = "http://127.0.0.1:8080")
    public String getAll() {
        return "CommonStat";
    }

    @GetMapping("/api/v1/admin/stats/post")
    @ApiOperation(value = "Получить статистику публикаций")
    @CrossOrigin(allowCredentials = "true", origins = "http://127.0.0.1:8080")
    public StatisticResponse getPostStatistic(@RequestBody StatisticRequest request){
        return adminService.getPostStatistic(request);
    }

    @GetMapping("/api/v1/admin/stats/person")
    @ApiOperation(value = "Получить статистику пользователей")
    @CrossOrigin(allowCredentials = "true", origins = "http://127.0.0.1:8080")
    public PersonStatisticResponse getPersonStatistic(@RequestBody StatisticRequest request){
        return adminService.getPersonStatistic(request);
    }

    @GetMapping("/api/v1/admin/stats/comment")
    @ApiOperation(value = "Получить статистику комментариев")
    @CrossOrigin(allowCredentials = "true", origins = "http://127.0.0.1:8080")
    public StatisticResponse getCommentStatistic(@RequestBody StatisticRequest request){
        return adminService.getCommentStatistic(request);
    }

    @GetMapping("/api/v1/admin/stats/likes")
    @ApiOperation(value = "Получить статистику лайков")
    @CrossOrigin(allowCredentials = "true", origins = "http://127.0.0.1:8080")
    public StatisticResponse getLikesStatistic(@RequestBody StatisticRequest request){
        return adminService.getLikeStatistic(request);
    }
}
