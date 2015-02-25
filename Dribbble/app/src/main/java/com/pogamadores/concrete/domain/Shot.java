package com.pogamadores.concrete.domain;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by igorcferreira on 1/25/15.
 */
public class Shot implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("height")
    private int height;
    @SerializedName("width")
    private int width;
    @SerializedName("likes_count")
    private int likesCount;
    @SerializedName("comments_count")
    private int commentsCount;
    @SerializedName("rebounds_count")
    private int reboundsCount;
    @SerializedName("url")
    private String url;
    @SerializedName("short_url")
    private String shortUrl;
    @SerializedName("views_count")
    private int viewsCount;
    @SerializedName("rebound_source_id")
    private BigDecimal reboundSourceId;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("image_teaser_url")
    private String teaserImageUrl;
    @SerializedName("image_400_url")
    private String errorImageUrl;
    @SerializedName("player")
    private Player player;
    @SerializedName("created_at")
    private String creationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getReboundsCount() {
        return reboundsCount;
    }

    public void setReboundsCount(int reboundsCount) {
        this.reboundsCount = reboundsCount;
    }

    public Uri getUrl() {
        return url == null ? null : Uri.parse(url);
    }

    public void setUrl(Uri url) {
        this.url = url == null ? null : url.toString();
    }

    public Uri getShortUrl() {
        return shortUrl == null ? null : Uri.parse(shortUrl);
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public BigDecimal getReboundSourceId() {
        return reboundSourceId;
    }

    public void setReboundSourceId(BigDecimal reboundSourceId) {
        this.reboundSourceId = reboundSourceId;
    }

    public Uri getImageUrl() {
        return imageUrl == null ? null : Uri.parse(imageUrl);
    }

    public void setImageUrl(Uri imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.toString();
    }

    public Uri getTeaserImageUrl() {
        return teaserImageUrl == null ? null : Uri.parse(teaserImageUrl);
    }

    public void setTeaserImageUrl(Uri teaserImageUrl) {
        this.teaserImageUrl = teaserImageUrl == null ? null : teaserImageUrl.toString();
    }

    public Uri getErrorImageUrl() {
        return errorImageUrl == null ? null : Uri.parse(errorImageUrl);
    }

    public void setErrorImageUrl(Uri errorImageUrl) {
        this.errorImageUrl = errorImageUrl == null ? null : errorImageUrl.toString();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeInt(this.height);
        dest.writeInt(this.width);
        dest.writeInt(this.likesCount);
        dest.writeInt(this.commentsCount);
        dest.writeInt(this.reboundsCount);
        dest.writeString(this.url);
        dest.writeString(this.shortUrl);
        dest.writeInt(this.viewsCount);
        dest.writeSerializable(this.reboundSourceId);
        dest.writeString(this.imageUrl);
        dest.writeString(this.teaserImageUrl);
        dest.writeString(this.errorImageUrl);
        dest.writeParcelable(this.player, flags);
        dest.writeString(this.creationDate);
    }

    public Shot() {
    }

    private Shot(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.height = in.readInt();
        this.width = in.readInt();
        this.likesCount = in.readInt();
        this.commentsCount = in.readInt();
        this.reboundsCount = in.readInt();
        this.url = in.readString();
        this.shortUrl = in.readString();
        this.viewsCount = in.readInt();
        this.reboundSourceId = (BigDecimal) in.readSerializable();
        this.imageUrl = in.readString();
        this.teaserImageUrl = in.readString();
        this.errorImageUrl = in.readString();
        this.player = in.readParcelable(Player.class.getClassLoader());
        this.creationDate = in.readString();
    }

    public static final Parcelable.Creator<Shot> CREATOR = new Parcelable.Creator<Shot>() {
        public Shot createFromParcel(Parcel source) {
            return new Shot(source);
        }

        public Shot[] newArray(int size) {
            return new Shot[size];
        }
    };
}
