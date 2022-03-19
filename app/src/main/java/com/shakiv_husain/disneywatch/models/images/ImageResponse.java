
package com.shakiv_husain.disneywatch.models.images;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageResponse {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("backdrops")
    @Expose
    private List<Backdrop> backdrops = null;
    @SerializedName("posters")
    @Expose
    private List<Poster> posters = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Backdrop> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Backdrop> backdrops) {
        this.backdrops = backdrops;
    }

    public List<Poster> getPosters() {
        return posters;
    }

    public void setPosters(List<Poster> posters) {
        this.posters = posters;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ImageResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("backdrops");
        sb.append('=');
        sb.append(((this.backdrops == null)?"<null>":this.backdrops));
        sb.append(',');
        sb.append("posters");
        sb.append('=');
        sb.append(((this.posters == null)?"<null>":this.posters));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}
