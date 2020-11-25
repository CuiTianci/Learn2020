package com.example.customview.draw.attributeanimation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.customview.R

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        //ObjectAnimator
        /* val bottomFlipAnimator = ObjectAnimator.ofFloat(view, "bottomFlip", 60f)
             .apply {
                 startDelay = 1000
                 duration = 1000
             }
         val flipRotationAnimator = ObjectAnimator.ofFloat(view, "flipRotation", 270f)
             .apply {
                 startDelay = 200
                 duration = 1000
             }

         val topFlipAnimator = ObjectAnimator.ofFloat(view, "topFlip", -60f)
             .apply {
                 startDelay = 200
                 duration = 1000
             }*/

//        多个动画合并。AnimatorSet
        /* val animatorSet = AnimatorSet()
         animatorSet.playSequentially(bottomFlipAnimator,flipRotationAnimator,topFlipAnimator)
         animatorSet.start()*/


        /*//AnimationHolder
        val bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip",60f)
        val flipRotateHolder = PropertyValuesHolder.ofFloat("flipRotation",270f)
        val topFlipHolder = PropertyValuesHolder.ofFloat("topFlip",-60f)
        val holderAnimator = ObjectAnimator.ofPropertyValuesHolder(view,bottomFlipHolder,flipRotateHolder,topFlipHolder)
        holderAnimator.startDelay = 1000
        holderAnimator.duration = 2000
        holderAnimator.start()*/


        /*//KeyFrame 关键帧
        val length = 200f.dp
        val keyframe0 = Keyframe.ofFloat(0f,0f)
        val keyframe1 = Keyframe.ofFloat(0.2f,1.5f * length)
        val keyframe2 = Keyframe.ofFloat(0.8f,0.6f * length)
        val keyframe3 = Keyframe.ofFloat(1f, length)
        val keyFrameHolder = PropertyValuesHolder.ofKeyframe("translationX",keyframe0,keyframe1,keyframe2,keyframe3)
        val animator = ObjectAnimator.ofPropertyValuesHolder(view,keyFrameHolder)
        animator.startDelay = 1000
        animator.duration = 2000
        animator.start()*/

        /*//Interpolator 插值器
        val animator = ObjectAnimator.ofFloat(view,"translationX",260.dp)
        animator.interpolator = AccelerateInterpolator()//加速插值器。
        animator.duration = 1000
        animator.startDelay = 1000
        animator.start()*/

        /*//TypeEvaluator 估值器，精确计算，每一刻属性的值。
        val animator = ObjectAnimator.ofObject(view,"point",PointFEvaluator(),PointF(100.dp,200.dp))
        animator.duration = 2000
        animator.startDelay = 1000
        animator.start()*/
        /* val animator = ObjectAnimator.ofObject(view,"province",ProvinceView.ProvinceEvaluator(),"陕西省")
         animator.startDelay = 1000
         animator.duration = 5000
         animator.start()*/
    }
}