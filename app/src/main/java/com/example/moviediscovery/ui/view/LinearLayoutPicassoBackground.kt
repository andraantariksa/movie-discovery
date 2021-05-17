package com.example.moviediscovery.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

class LinearLayoutPicassoBackground : LinearLayout, Target {
    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attr: AttributeSet? = null) : super(context, attr) {
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        background = BitmapDrawable(resources, bitmap)
    }

    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
    }
}