package com.example.account.service.dto;
import java.time.Instant;
import java.util.Set;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDetailedView {
    private Long id;

    private String login;

    private String firstName;

    private String lastName;

    private String email;
    private String imageUrl;

    private boolean activated;
    private String langKey;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Set<String> authorities;

    public Long getId() {
        return id;
    }

    public UserDetailedView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public UserDetailedView setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDetailedView setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDetailedView setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDetailedView setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserDetailedView setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public boolean isActivated() {
        return activated;
    }

    public UserDetailedView setActivated(boolean activated) {
        this.activated = activated;
        return this;
    }

    public String getLangKey() {
        return langKey;
    }

    public UserDetailedView setLangKey(String langKey) {
        this.langKey = langKey;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public UserDetailedView setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public UserDetailedView setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public UserDetailedView setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public UserDetailedView setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public UserDetailedView setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
        return this;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
                   "login='" + login + '\'' +
                   ", firstName='" + firstName + '\'' +
                   ", lastName='" + lastName + '\'' +
                   ", email='" + email + '\'' +
                   ", imageUrl='" + imageUrl + '\'' +
                   ", activated=" + activated +
                   ", langKey='" + langKey + '\'' +
                   ", createdBy=" + createdBy +
                   ", createdDate=" + createdDate +
                   ", lastModifiedBy='" + lastModifiedBy + '\'' +
                   ", lastModifiedDate=" + lastModifiedDate +
                   ", authorities=" + authorities +
                   "}";
    }
}
