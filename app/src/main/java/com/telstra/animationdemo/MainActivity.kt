package com.telstra.animationdemo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.telstra.animationdemo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var animationTime: Long = 0
    private lateinit var fabOnChange: ExtendedFabOnChange
    private var animating = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        animationTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        fabOnChange = ExtendedFabOnChange(animationTime, ::finishAnimating)
        binding.extendedFab.setOnClickListener{
            if(binding.fab1.visibility == View.VISIBLE) {
                hideFabs()
            } else {
                displayFabs()
            }
        }
    }


    private fun moveView(view: View, movement: Float, hideAtEndOfAnimation: Boolean) {
        view.apply {
            // Set the alpha to 0 if we are transitioning to nothing and vice versa
            alpha = if (hideAtEndOfAnimation) 1f else 0f
            visibility = View.VISIBLE

            animate()
                .alpha(if (hideAtEndOfAnimation) 0f else 1f)
                .translationYBy(movement)
                .setListener(null)
                .setDuration(animationTime)
                .withEndAction {
                    if (hideAtEndOfAnimation) {
                        visibility = View.GONE
                    }
                }
        }
    }

    private fun displayFabs() {
        if (!animating) {
            animating = true
            binding.extendedFab.shrink(fabOnChange);
            moveView(
                binding.fab1Label,
                -resources.getDimensionPixelSize(R.dimen.button1_move).toFloat(),
                false
            )
            moveView(
                binding.fab2Label,
                -resources.getDimensionPixelSize(R.dimen.button2_move).toFloat(),
                false
            )
            moveView(
                binding.fab1,
                -resources.getDimensionPixelSize(R.dimen.button1_move).toFloat(),
                false
            )
            moveView(
                binding.fab2,
                -resources.getDimensionPixelSize(R.dimen.button2_move).toFloat(),
                false
            )
        }
    }

    private fun hideFabs() {
        if (!animating) {
            animating = true
            binding.extendedFab.apply {
                animate()
                    .setDuration(animationTime)
                    .rotation(0f)
                    .withEndAction{
                        binding.extendedFab.extend(fabOnChange)
                    }
            }
            moveView(
                binding.fab1Label,
                resources.getDimensionPixelSize(R.dimen.button1_move).toFloat(),
                true
            )
            moveView(
                binding.fab2Label,
                resources.getDimensionPixelSize(R.dimen.button2_move).toFloat(),
                true
            )
            moveView(
                binding.fab1,
                resources.getDimensionPixelSize(R.dimen.button1_move).toFloat(),
                true
            )
            moveView(
                binding.fab2,
                resources.getDimensionPixelSize(R.dimen.button2_move).toFloat(),
                true
            )
        }
    }

    private fun finishAnimating(){
        animating = false
    }

    class ExtendedFabOnChange(
        private val animationTime: Long,
        private val callbackFunction: () -> Unit
    ) : ExtendedFloatingActionButton.OnChangedCallback() {
        override fun onShrunken(extendedFab: ExtendedFloatingActionButton?) {
            super.onShrunken(extendedFab)
            // Rotate the icon to become an X
            extendedFab?.animate()
                ?.setDuration(animationTime)
                ?.rotation(45f)
                ?.withEndAction{
                    callbackFunction()
                }
        }

        override fun onExtended(extendedFab: ExtendedFloatingActionButton?) {
            super.onExtended(extendedFab)
            callbackFunction()
        }
    }

}