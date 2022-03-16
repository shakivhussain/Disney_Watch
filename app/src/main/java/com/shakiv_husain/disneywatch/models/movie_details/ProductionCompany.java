
package com.shakiv_husain.disneywatch.models.movie_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCompany {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("logo_path")
    @Expose
    public String logoPath;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("origin_country")
    @Expose
    public String originCountry;

}
