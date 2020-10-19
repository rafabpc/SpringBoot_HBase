package com.documentanalyzer.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;
    private String email;
    private Timestamp joinDate;
    private Timestamp lastUpload;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "user_team", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))
    private Set<Team> teams = new HashSet<Team>();

    @JsonManagedReference
    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<Upload> uploads = new HashSet<Upload>();

    public User(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Timestamp getLastUpload() { return lastUpload; }

    public void setLastUpload(Timestamp lastUpload) { this.lastUpload = lastUpload; }

    public Set<Upload> getUploads() { return uploads; }

    public void setUploads(Set<Upload> uploads) { this.uploads = uploads; }

}
