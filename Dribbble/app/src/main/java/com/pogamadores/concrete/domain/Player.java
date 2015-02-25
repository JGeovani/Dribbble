package com.pogamadores.concrete.domain;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by igorcferreira on 1/25/15.
 */
public class Player implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("username")
    private String username;
    @SerializedName("name")
    private String name;
    @SerializedName("location")
    private String location;
    @SerializedName("followers_count")
    private int followersCount;
    @SerializedName("draftees_count")
    private int drafteesCount;
    @SerializedName("likes_count")
    private int likesCount;
    @SerializedName("likes_received_count")
    private int likesReceivedCount;
    @SerializedName("url")
    private String url;
    @SerializedName("views_count")
    private int viewsCount;
    @SerializedName("comments_count")
    private int commentsCount;
    @SerializedName("comments_received_count")
    private int commentsReceivedCount;
    @SerializedName("rebounds_count")
    private int reboundsCount;
    @SerializedName("rebounds_received_count")
    private int reboundsReceivedCount;
    @SerializedName("website_url")
    private String websiteUrl;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("twitter_screen_name")
    private String twitterScreenName;
    @SerializedName("shots_count")
    private int shotsCount;
    @SerializedName("following_count")
    private int followingCount;
    @SerializedName("drafted_by_player_id")
    private int draftedByPlayerId;
    @SerializedName("created_at")
    private String creationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getDrafteesCount() {
        return drafteesCount;
    }

    public void setDrafteesCount(int drafteesCount) {
        this.drafteesCount = drafteesCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getLikesReceivedCount() {
        return likesReceivedCount;
    }

    public void setLikesReceivedCount(int likesReceivedCount) {
        this.likesReceivedCount = likesReceivedCount;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getCommentsReceivedCount() {
        return commentsReceivedCount;
    }

    public void setCommentsReceivedCount(int commentsReceivedCount) {
        this.commentsReceivedCount = commentsReceivedCount;
    }

    public int getReboundsCount() {
        return reboundsCount;
    }

    public void setReboundsCount(int reboundsCount) {
        this.reboundsCount = reboundsCount;
    }

    public int getReboundsReceivedCount() {
        return reboundsReceivedCount;
    }

    public void setReboundsReceivedCount(int reboundsReceivedCount) {
        this.reboundsReceivedCount = reboundsReceivedCount;
    }

    public String getTwitterScreenName() {
        return twitterScreenName;
    }

    public void setTwitterScreenName(String twitterScreenName) {
        this.twitterScreenName = twitterScreenName;
    }

    public int getShotsCount() {
        return shotsCount;
    }

    public void setShotsCount(int shotsCount) {
        this.shotsCount = shotsCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getDraftedByPlayerId() {
        return draftedByPlayerId;
    }

    public void setDraftedByPlayerId(int draftedByPlayerId) {
        this.draftedByPlayerId = draftedByPlayerId;
    }

    public Uri getUrl() {
        return url == null ? null : Uri.parse(url);
    }

    public void setUrl(Uri url) {
        this.url = url == null ? null : url.toString();
    }

    public Uri getWebsiteUrl() {
        return websiteUrl == null ? null : Uri.parse(websiteUrl);
    }

    public void setWebsiteUrl(Uri websiteUrl) {
        this.websiteUrl = websiteUrl == null ? null : websiteUrl.toString();
    }

    public Uri getAvatarUrl() {
        return avatarUrl == null ? null : Uri.parse(avatarUrl);
    }

    public void setAvatarUrl(Uri avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.toString();
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.username);
        dest.writeString(this.name);
        dest.writeString(this.location);
        dest.writeInt(this.followersCount);
        dest.writeInt(this.drafteesCount);
        dest.writeInt(this.likesCount);
        dest.writeInt(this.likesReceivedCount);
        dest.writeString(this.url);
        dest.writeInt(this.viewsCount);
        dest.writeInt(this.commentsCount);
        dest.writeInt(this.commentsReceivedCount);
        dest.writeInt(this.reboundsCount);
        dest.writeInt(this.reboundsReceivedCount);
        dest.writeString(this.websiteUrl);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.twitterScreenName);
        dest.writeInt(this.shotsCount);
        dest.writeInt(this.followingCount);
        dest.writeInt(this.draftedByPlayerId);
        dest.writeString(this.creationDate);
    }

    public Player() {
    }

    private Player(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.name = in.readString();
        this.location = in.readString();
        this.followersCount = in.readInt();
        this.drafteesCount = in.readInt();
        this.likesCount = in.readInt();
        this.likesReceivedCount = in.readInt();
        this.url = in.readString();
        this.viewsCount = in.readInt();
        this.commentsCount = in.readInt();
        this.commentsReceivedCount = in.readInt();
        this.reboundsCount = in.readInt();
        this.reboundsReceivedCount = in.readInt();
        this.websiteUrl = in.readString();
        this.avatarUrl = in.readString();
        this.twitterScreenName = in.readString();
        this.shotsCount = in.readInt();
        this.followingCount = in.readInt();
        this.draftedByPlayerId = in.readInt();
        this.creationDate = in.readString();
    }

    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}
