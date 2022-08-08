package id.indocyber.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageViewBindingAdapter {
    @BindingAdapter("custom:loadImage")
    @JvmStatic
    fun loadImage(imageView: ImageView, link: String?) {
        link?.let {
            Glide.with(imageView).load(link).into(imageView)
        }
    }

    @BindingAdapter("custom:loadImageCircle")
    @JvmStatic
    fun loadImageCircle(imageView: ImageView, link: String?) {
        link?.let {
            Glide.with(imageView).load(link).placeholder(R.drawable.ic_baseline_account_circle_24)
                .circleCrop().into(imageView)
        }
    }


}