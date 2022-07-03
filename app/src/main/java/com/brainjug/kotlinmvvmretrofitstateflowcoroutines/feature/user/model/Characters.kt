package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.data.model.response.Location
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.SerializedName

data class Characters(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("species") var species: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("location") var location: Location? = Location(),
    @SerializedName("image") var image: String? = null,
)

@BindingAdapter("characterImage")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(imageUrl).apply(RequestOptions().circleCrop())
        .into(view)
}
