
package com.shakiv_husain.disneywatch.models.Video;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("results")
    @Expose
    private List<VideoModel> videoModels = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<VideoModel> getResults() {
        return videoModels;
    }

    public void setResults(List<VideoModel> videoModels) {
        this.videoModels = videoModels;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(VideoResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? "<null>" : this.id));
        sb.append(',');
        sb.append("results");
        sb.append('=');
        sb.append(((this.videoModels == null) ? "<null>" : this.videoModels));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
