package ru.skillbox.socialnetwork.data.entity;

public enum FriendshipStatusType {
    REQUEST,   // Запрос на добавление в друзья
    FRIEND,    // Друзья
    BLOCKED,   // Пользователь в чёрном списке
    DECLINED,  // Запрос на добавление в друзья отклонён
    SUBSCRIBED // Подписан
}
