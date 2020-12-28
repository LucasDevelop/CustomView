package com.cj.customwidget.page.falling

import android.graphics.Path
import android.graphics.PathMeasure
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import java.util.*

class RedPackAnim(val path: Path, val rotation: Float, val view: View) : Animation() {
    val pathMeasure = PathMeasure(path, false)
    val point = FloatArray(2)
    val tan = FloatArray(2)

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        pathMeasure.getPosTan(pathMeasure.length * interpolatedTime, point, tan)
        view.x = point[0] - view.measuredWidth / 2
        view.y = point[1]
        view.rotation = rotation * interpolatedTime
//        "point:${point.toList()}".p()
    }
}