package com.example.thegiftcherk.setup.utils


import android.os.Handler
import android.view.View

class DoubleClick(private val doubleClickListener: DoubleClickListener,
                  private var DOUBLE_CLICK_INTERVAL: Long = 200L  // Time to wait the second click.
) : View.OnClickListener {

    interface DoubleClickListener {
        fun onSingleClick(view: View)
        fun onDoubleClick(view: View)
    }

    /*
   * Handler to process click event.
   */
    private val mHandler = Handler()

    /*
   * Number of clicks in @DOUBLE_CLICK_INTERVAL interval.
   */
    private var clicks: Int = 0

    /*
   * Flag to check if click handler is busy.
   */
    private var isBusy = false

    override fun onClick(view: View) {

        if (!isBusy) {
            //  Prevent multiple click in this short time
            isBusy = true

            // Increase clicks count
            clicks++

            mHandler.postDelayed({
                if (clicks >= 2) {  // Double tap.
                    doubleClickListener.onDoubleClick(view)
                }

                if (clicks == 1) {  // Single tap
                    doubleClickListener.onSingleClick(view)
                }

                // we need to  restore clicks count
                clicks = 0
            }, DOUBLE_CLICK_INTERVAL)
            isBusy = false
        }

    }
}