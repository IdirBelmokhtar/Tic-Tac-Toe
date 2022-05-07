package com.xogamer.tictactoe;

public class Request {
    String id,name,photoUrl,friendName,FriendId;
    boolean play;

    public Request(String id, String name, String photoUrl, String friendName, String friendId, boolean play) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.friendName = friendName;
        FriendId = friendId;
        this.play = play;
    }

    public Request(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendId() {
        return FriendId;
    }

    public void setFriendId(String friendId) {
        FriendId = friendId;
    }

    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }
}
