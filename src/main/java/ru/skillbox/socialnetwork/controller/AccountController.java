package ru.skillbox.socialnetwork.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.data.dto.*;
import ru.skillbox.socialnetwork.exception.PasswordsNotEqualsException;
import ru.skillbox.socialnetwork.exception.PersonAlReadyRegisterException;
import ru.skillbox.socialnetwork.service.AccountService;
import ru.skillbox.socialnetwork.service.RegisterService;

import java.security.Principal;

@RestController
@Api(tags = "Работа с аккаунтом")
@Slf4j
@RequiredArgsConstructor
public class AccountController {

    private final RegisterService registerService;
    private final AccountService accountService;


    @CrossOrigin(allowCredentials = "true", origins = "http://127.0.0.1:8080")
    @PostMapping("/api/v1/account/register")
    @ApiOperation(value="Регистрация пользователя")
    public ResponseEntity<RegisterResponse> regPerson(@RequestBody RegisterRequest registerRequest) throws PasswordsNotEqualsException, PersonAlReadyRegisterException {
        log.info("Зарегестрировался пользователь: " + registerRequest.getEmail());
        return ResponseEntity.ok(registerService.regPerson(registerRequest));
    }

    @CrossOrigin(allowCredentials = "true", origins = "http://127.0.0.1:8080")
    @PostMapping("/api/v1/account/register/confirm")
    @ApiOperation(value="Подтверждение регистрации")
    public ResponseEntity<RegisterResponse> confirmPerson(@RequestBody ConfirmUserRequest confirmUserRequest) {
        return ResponseEntity.ok(registerService.confirmPerson(confirmUserRequest));
    }

    @CrossOrigin(allowCredentials = "true", origins = "http://127.0.0.1:8080")
    @PutMapping("/api/v1/account/notifications")
    @ApiOperation(value = "Редактирование настроек оповещения")
    public ResponseEntity<NotificationSettingResponse> putNotificationSetting(@RequestBody NotificationSettingRequest request, Principal principal){
        log.info("Пользователь включил/выключил отображение уведомлений с типом " + request.getNotificationType());
        return ResponseEntity.ok(accountService.putNotificationSetting(request, principal));
    }

    @CrossOrigin(allowCredentials = "true", origins = "http://127.0.0.1:8080")
    @GetMapping("/api/v1/account/notifications")
    @ApiOperation(value = "Получение настроек оповещений")
    public ResponseEntity<NotificationSettingResponse> getNotificationSetting(Principal principal){
        log.info("Запрос настроек уведомлений пользователя " + principal.getName());
        return ResponseEntity.ok(accountService.getNotificationSetting(principal));
    }

}
