
package com.shakiv_husain.disneywatch.models.images;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Poster {

    @SerializedName("aspect_ratio")
    @Expose
    private Double aspectRatio;
    @SerializedName("file_path")
    @Expose
    private String filePath;
    @SerializedName("height")
    @Expose
    private Long height;
    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;
    @SerializedName("vote_average")
    @Expose
    private String voteAverage;
    @SerializedName("vote_count")
    @Expose
    private String voteCount;
    @SerializedName("width")
    @Expose
    private Long width;

    public Double getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(Double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Poster.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("aspectRatio");
        sb.append('=');
        sb.append(((this.aspectRatio == null) ? "<null>" : this.aspectRatio));
        sb.append(',');
        sb.append("filePath");
        sb.append('=');
        sb.append(((this.filePath == null) ? "<null>" : this.filePath));
        sb.append(',');
        sb.append("height");
        sb.append('=');
        sb.append(((this.height == null) ? "<null>" : this.height));
        sb.append(',');
        sb.append("iso6391");
        sb.append('=');
        sb.append(((this.iso6391 == null) ? "<null>" : this.iso6391));
        sb.append(',');
        sb.append("voteAverage");
        sb.append('=');
        sb.append(((this.voteAverage == null) ? "<null>" : this.voteAverage));
        sb.append(',');
        sb.append("voteCount");
        sb.append('=');
        sb.append(((this.voteCount == null) ? "<null>" : this.voteCount));
        sb.append(',');
        sb.append("width");
        sb.append('=');
        sb.append(((this.width == null) ? "<null>" : this.width));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
