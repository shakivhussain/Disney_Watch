
package com.shakiv_husain.disneywatch.models.movie_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpokenLanguage {

    @SerializedName("english_name")
    @Expose
    public String englishName;
    @SerializedName("iso_639_1")
    @Expose
    public String iso6391;
    @SerializedName("name")
    @Expose
    public String name;

}
