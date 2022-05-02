package com.victorrubia.tfg.helpers

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView

class LottieHelper(animationView: LottieAnimationView) {

    var animation = animationView

    fun showLoading(){
        animation.visibility = View.VISIBLE
        animation.setMinAndMaxFrame(0, 59)
        animation.playAnimation()
        animation.loop(true)
    }

    fun showLoadingSuccessful(context : Context?, intent : Intent?){
        completeAnimationBeforeNewActivity(context, intent)
        animation.setMinAndMaxFrame(59, 89)
        animation.loop(false)
    }

    fun showLoadingError(){
        animation.setMinAndMaxFrame(90, 138)
        animation.loop(false)
    }

    private fun completeAnimationBeforeNewActivity(context : Context?, intent : Intent?){

        animation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                Log.e("Animation:", "end")
                //Your code for remove the fragment
                if(intent != null && context != null) {
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    ContextCompat.startActivity(context, intent, null)
                    (context as Activity).finish()
                }
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })
    }

}