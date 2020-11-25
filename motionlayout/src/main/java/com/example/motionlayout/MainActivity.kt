package com.example.motionlayout

import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.example.motionlayout.extensions.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cl_barrier.*
import kotlinx.android.synthetic.main.cl_barrier.view.*
import kotlinx.android.synthetic.main.cl_basic.*
import kotlinx.android.synthetic.main.cl_basic.view.cbBottom
import kotlinx.android.synthetic.main.cl_basic.view.cbEnd
import kotlinx.android.synthetic.main.cl_basic.view.cbStart
import kotlinx.android.synthetic.main.cl_basic.view.cbTop
import kotlinx.android.synthetic.main.cl_basic.view.mvT
import kotlinx.android.synthetic.main.cl_bias.*
import kotlinx.android.synthetic.main.cl_bias.view.*
import kotlinx.android.synthetic.main.cl_chain.*
import kotlinx.android.synthetic.main.cl_chain.view.*
import kotlinx.android.synthetic.main.cl_chain.view.mvA
import kotlinx.android.synthetic.main.cl_chain.view.mvB
import kotlinx.android.synthetic.main.cl_chain.view.mvC
import kotlinx.android.synthetic.main.cl_circular.*
import kotlinx.android.synthetic.main.cl_circular.view.*
import kotlinx.android.synthetic.main.cl_group.*
import kotlinx.android.synthetic.main.cl_group.view.*
import kotlinx.android.synthetic.main.cl_two_views.*
import kotlinx.android.synthetic.main.cl_two_views.view.*
import kotlinx.android.synthetic.main.cl_bias.view.barrier as barrier1
import kotlinx.android.synthetic.main.cl_circular.view.barrier as barrier1

private lateinit var testVar: String

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        introduction.loadAssetsById()
        basicLayout()
        twoViewsLayout()
        biasLayout()
        chainLayout()
        barrierLayout()
        groupLayout()
        circularLayout()
//        Log.e(TAG, "onCreate: ${null != testVar}")
//        Log.e(TAG, "onCreate: ${::testVar.isInitialized}")
//        testVar = ""
//        Log.e(TAG, "onCreate: ${::testVar.isInitialized}")
    }

    private fun basicLayout() {
        basic.loadAssetsById()
        val constraintSet = ConstraintSet()
        constraintSet.clone(clBasic)
        clBasic.mvT.showConstraintsWhenClicked(constraintSet, this, ConstraintType.LAYOUT)
        val onCheckChangeListener =
            CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                val bound = when (buttonView) {
                    clBasicShell.cbStart -> ConstraintSet.START
                    clBasicShell.cbEnd -> ConstraintSet.END
                    clBasicShell.cbTop -> ConstraintSet.TOP
                    clBasicShell.cbBottom -> ConstraintSet.BOTTOM
                    else -> {
                        Log.wtf(
                            TAG,
                            "basicLayout: bound goes to unset"
                        )
                    }
                }
                val targetId = if (isChecked) ConstraintSet.PARENT_ID else ConstraintSet.UNSET
                constraintSet.connect(clBasic.mvT.id, bound, targetId, bound)
                TransitionManager.beginDelayedTransition(clBasic)
                constraintSet.applyTo(clBasic)
            }
        clBasicShell.cbStart.setOnCheckedChangeListener(onCheckChangeListener)
        clBasicShell.cbEnd.setOnCheckedChangeListener(onCheckChangeListener)
        clBasicShell.cbTop.setOnCheckedChangeListener(onCheckChangeListener)
        clBasicShell.cbBottom.setOnCheckedChangeListener(onCheckChangeListener)
    }

    private fun twoViewsLayout() {
        twoViews.loadAssetsById()
        val constraintSet = ConstraintSet()
        constraintSet.clone(clTwoViews)
        clTwoViews.mvT.showConstraintsWhenClicked(constraintSet, this, ConstraintType.LAYOUT)
        val onCheckChangeListener =
            CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                val bound = when (buttonView) {
                    clTwoViewShell.cbStart -> ConstraintSet.START
                    clTwoViewShell.cbEnd -> ConstraintSet.END
                    clTwoViewShell.cbTop -> ConstraintSet.TOP
                    clTwoViewShell.cbBottom -> ConstraintSet.BOTTOM
                    else -> {
                        Log.wtf(
                            TAG,
                            "twoViewsLayout: bound goes to unset"
                        )
                    }
                }
                val selectTargetId =
                    if (clTwoViewShell.swParent.isChecked) ConstraintSet.PARENT_ID else clTwoViews.mvA.id
                val targetBound = bound.targetBounds(clTwoViewShell.swReverse.isChecked)
                val targetId =
                    if (isChecked) selectTargetId else ConstraintSet.UNSET
                constraintSet.connect(clTwoViews.mvT.id, bound, targetId, targetBound)
                TransitionManager.beginDelayedTransition(clTwoViews)
                constraintSet.applyTo(clTwoViews)
            }
        clTwoViewShell.cbStart.setOnCheckedChangeListener(onCheckChangeListener)
        clTwoViewShell.cbEnd.setOnCheckedChangeListener(onCheckChangeListener)
        clTwoViewShell.cbTop.setOnCheckedChangeListener(onCheckChangeListener)
        clTwoViewShell.cbBottom.setOnCheckedChangeListener(onCheckChangeListener)
    }

    private fun biasLayout() {
        bias.loadAssetsById()
        val constraintSet = ConstraintSet()
        constraintSet.clone(clBias)
        clBiasShell.sbHorizontal.setOnProgressChangeListener {
            constraintSet.setHorizontalBias(clBias.mvT.id, it / 100.0f)
            constraintSet.applyTo(clBias)
        }
        clBiasShell.sbVertical.setOnProgressChangeListener {
            constraintSet.setVerticalBias(clBias.mvT.id, it / 100.0f)
            constraintSet.applyTo(clBias)
        }
        clBias.mvT.showConstraintsWhenClicked(constraintSet, this, ConstraintType.BIAS)
    }

    private fun chainLayout() {
        chain.loadAssetsById()
        val constraintSet = ConstraintSet()
        constraintSet.clone(clChain)
        clChainShell.rgChainStyle.setOnCheckedChangeListener { _, id ->
            val index = when (id) {
                clChainShell.rbSpread.id -> 0
                clChainShell.rbSpreadInside.id -> 1
                clChainShell.rbPacked.id -> 2
                else -> {
                    Log.wtf(TAG, "chain style out of bounds")
                }
            }
            constraintSet.setHorizontalChainStyle(clChain.mvA.id, index)
            TransitionManager.beginDelayedTransition(clChain)
            constraintSet.applyTo(clChain)
        }
        clChain.mvA.showConstraintsWhenClicked(
            constraintSet,
            this,
            ConstraintType.LAYOUT,
            ConstraintType.CHAIN
        )
        clChain.mvB.showConstraintsWhenClicked(constraintSet, this, ConstraintType.LAYOUT)
        clChain.mvC.showConstraintsWhenClicked(constraintSet, this, ConstraintType.LAYOUT)
    }

    private fun barrierLayout() {
        clBarrierShell.barrierMd.loadAssetsById()
        val constraintSet = ConstraintSet()
        constraintSet.clone(clBarrier)
        var widthOfA: Int? = null
        var widthOfB: Int? = null
        clBarrier.mvA.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                clBarrier.mvA.viewTreeObserver.removeOnGlobalLayoutListener(this)//需要当前上下文时，不能写成lamuda表达式。
                widthOfA = clBarrier.mvA.measuredWidth
            }
        })
        clBarrier.mvB.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                clBarrier.mvB.viewTreeObserver.removeOnGlobalLayoutListener(this)//需要当前上下文时，不能写成lamuda表达式。
                widthOfB = clBarrier.mvB.measuredWidth
            }
        })
        clBarrierShell.swWidthOfA.setOnProgressChangeListener {
            if (it == 0) return@setOnProgressChangeListener
            val layoutParamsA = clBarrier.mvA.layoutParams
            layoutParamsA.width = (widthOfA!! * (it / 100.0f)).toInt()
            clBarrier.mvA.layoutParams = layoutParamsA
            val layoutParamsB = clBarrier.mvB.layoutParams
            layoutParamsB.width = (widthOfB!! + 100 - it)
            clBarrier.mvB.layoutParams = layoutParamsB
        }
        clBarrier.mvT.showConstraintsWhenClicked(constraintSet, this, ConstraintType.LAYOUT)
        clBarrier.mvB.showConstraintsWhenClicked(
            constraintSet,
            this,
            ConstraintType.REFERENCE_IDS,
            target = clBarrier.barrier
        )
    }

    private fun groupLayout() {
        clGroupShell.group.loadAssetsById()
        val constraintSet = ConstraintSet()
        constraintSet.clone(clGroup)
        val onCheckChangeListener =
            CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                when (buttonView) {
                    clGroupShell.swInvisible -> {
                        if (isChecked)
                            clGroupShell.swGone.isChecked = false
                    }
                    clGroupShell.swGone -> {
                        if (isChecked)
                            clGroupShell.swInvisible.isChecked = false
                    }
                }
                clGroup.groupV.visibility = when {
                    clGroupShell.swGone.isChecked -> View.GONE
                    clGroupShell.swInvisible.isChecked -> View.INVISIBLE
                    else -> View.VISIBLE
                }
            }
        clGroupShell.swInvisible.setOnCheckedChangeListener(onCheckChangeListener)
        clGroupShell.swGone.setOnCheckedChangeListener(onCheckChangeListener)
        clGroup.mvT.showConstraintsWhenClicked(
            constraintSet,
            this,
            ConstraintType.REFERENCE_IDS,
            target = clGroup.groupV
        )
    }

    private fun circularLayout() {
        val baseRadius = 80.dp
        circular.loadAssetsById()
        val constraintSet = ConstraintSet()
        constraintSet.clone(clCircular)
        val progressChangeListener: (progress: Int) -> Unit = {
            constraintSet.constrainCircle(
                clCircular.mvT.id,
                clCircular.mvA.id,
                baseRadius * clCircularShell.sbRadius.progress / 100,
                360.0f * clCircularShell.sbAngle.progress / 100
            )
            constraintSet.applyTo(clCircular)
        }
        clCircularShell.sbAngle.setOnProgressChangeListener {
            progressChangeListener(it)
        }
        clCircularShell.sbRadius.setOnProgressChangeListener {
            progressChangeListener(it)
        }
        clCircular.mvT.showConstraintsWhenClicked(constraintSet, this, ConstraintType.CIRCULAR)
    }
}