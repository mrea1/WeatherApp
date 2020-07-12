package com.mrea.weatherapp.presentation

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.ImageLoaderBuilder
import coil.api.load
import coil.decode.SvgDecoder
import coil.transform.RoundedCornersTransformation
import coil.util.DebugLogger
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.textfield.TextInputLayout
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import org.koin.core.inject

@BindingAdapter("animationUiState")
fun LottieAnimationView.setUiState(uiState: AnimationUiState) {
    setIsVisible(uiState.isVisible)

    repeatCount = if (uiState.loopEnabled) LottieDrawable.INFINITE else 0

    if (uiState.resourceId != 0 && uiState.resourceId != tag) {
        setAnimation(uiState.resourceId)
        tag = uiState.resourceId
        playAnimation()
    }
}

@BindingAdapter("isVisible", "setInvisibleOnFalse", requireAll = false)
fun View.setIsVisible(viewIsVisible: Boolean, setInvisibleOnFalse: Boolean = false) {
    if (isVisible != viewIsVisible) {
        if (setInvisibleOnFalse) {
            isInvisible = viewIsVisible.not()
        } else {
            isVisible = viewIsVisible
        }
    }
}

@BindingAdapter("textInputUiState")
fun TextInputLayout.setUiState(uiState: TextInputUiState) {
    setIsVisible(uiState.isVisible)
    error = if (uiState.hasError) uiState.errorText else null
}

object ImageViewAdapter : KoinComponent {

    private val okHttpClient: OkHttpClient by inject()

    @JvmStatic
    @BindingAdapter("imageUiState")
    fun bindImageUiState(imageView: ImageView, imageUiState: ImageUiState): Unit = imageView.run {
        if (tag == imageUiState) return

        isVisible = imageUiState.isVisible

        if (imageUiState.localImage != 0) {
            tag = imageUiState
            setImageViewResource(this, imageUiState.localImage)
            return
        }

        if (imageUiState.url.isEmpty()) return

        tag = imageUiState

        val imageLoader = ImageLoaderBuilder(context)
            .componentRegistry { add(SvgDecoder(context)) }
            .okHttpClient(okHttpClient)
            .apply { if (imageUiState.debugLoggingEnabled) logger(DebugLogger()) }
            .build()

        load(imageUiState.url, imageLoader = imageLoader) {
            lifecycle(findLifecycleOwner())
            if (imageUiState.crossfadeEnabled) {
                crossfade(imageUiState.crossfadeDuration)
            }
            if (imageUiState.roundedCorners) {
                transformations(RoundedCornersTransformation(imageUiState.roundedCornerRadius.px.toFloat()))
            }
        }
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageViewResource(imageView: ImageView, @DrawableRes resourceId: Int) = imageView.run {
        if (resourceId == 0) return

        val drawable = ContextCompat.getDrawable(context, resourceId)
        if (this.drawable != drawable) {
            setImageDrawable(drawable)
        }
    }
}
