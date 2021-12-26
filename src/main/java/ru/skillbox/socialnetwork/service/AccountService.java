package ru.skillbox.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.NotificationSettingRequest;
import ru.skillbox.socialnetwork.data.dto.NotificationSettingResponse;
import ru.skillbox.socialnetwork.data.entity.NotificationSettings;
import ru.skillbox.socialnetwork.data.entity.NotificationType;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.repository.NotificationSettingsRepository;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final NotificationSettingsRepository notificationSettingsRepository;
    private final PersonRepo personRepository;

    public NotificationSettingResponse putNotificationSetting(NotificationSettingRequest request, Principal principal) {
        Person person = personRepository.findByEmail(principal.getName()).orElseThrow(()-> new UsernameNotFoundException("User not Found"));
        if(request.getNotificationType().equals("POST")){
            notificationSettingsRepository.setNotificationPost(person, request.isEnable());
        } else if(request.getNotificationType().equals("POST_COMMENT")){
            notificationSettingsRepository.setNotificationPostComment(person, request.isEnable());
        } else if(request.getNotificationType().equals("COMMENT_COMMENT")){
            notificationSettingsRepository.setNotificationCommentOnComment(person, request.isEnable());
        } else if(request.getNotificationType().equals("FRIEND_REQUEST")){
            notificationSettingsRepository.setNotificationFriendRequest(person, request.isEnable());
        } else if(request.getNotificationType().equals("MESSAGE")){
            notificationSettingsRepository.setNotificationMessage(person, request.isEnable());
        }
        return notificationSettingResponseBuilder();
    }

    private NotificationSettingResponse notificationSettingResponseBuilder(){
        return NotificationSettingResponse.builder().error("String")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .build();
    }

    public NotificationSettingResponse getNotificationSetting(Principal principal) {
        Person person = personRepository.findByEmail(principal.getName()).orElseThrow(()-> new UsernameNotFoundException("user Not Found"));
        NotificationSettings settings = notificationSettingsRepository.findByPerson(person);
        List<NotificationSettingResponse.Data> data = new ArrayList<>();
        data.add(new NotificationSettingResponse.Data(NotificationType.POST, settings.isPost()));
        data.add(new NotificationSettingResponse.Data(NotificationType.COMMENT_COMMENT, settings.isCommentOnComment()));
        data.add(new NotificationSettingResponse.Data(NotificationType.FRIEND_REQUEST, settings.isFriendRequest()));
        data.add(new NotificationSettingResponse.Data(NotificationType.POST_COMMENT, settings.isPostComment()));
        data.add(new NotificationSettingResponse.Data(NotificationType.MESSAGE, settings.isMessage()));

        return NotificationSettingResponse.builder()
                .error("String")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(data)
                .build();
    }
}
