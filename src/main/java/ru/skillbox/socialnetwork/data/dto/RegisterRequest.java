package ru.skillbox.socialnetwork.data.dto;

public class RegisterRequest {
    private String email;
    private String passwd1;
    private String passwd2;
    private String firstName;
    private String lastName;

    public RegisterRequest(String email, String passwd1, String passwd2, String firstName, String lastName) {
        this.email = email;
        this.passwd1 = passwd1;
        this.passwd2 = passwd2;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd1() {
        return passwd1;
    }

    public void setPasswd1(String passwd1) {
        this.passwd1 = passwd1;
    }

    public String getPasswd2() {
        return passwd2;
    }

    public void setPasswd2(String passwd2) {
        this.passwd2 = passwd2;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
