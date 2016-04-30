package com.pld.velociraptor.tools;

/**
 * Created by Thibault on 29/04/2016.
 */
public class VeloTokenCredentials {
    String id;
    int ttl;
    String created;
    int userId;

    public VeloTokenCredentials(String id, int ttl, String created, int userId) {
        this.id = id;
        this.ttl = ttl;
        this.created = created;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
