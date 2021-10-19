package com.explore.support.constants

class ConstantSupport {
    companion object{
    }

    object TIME_DURATION {
        const val TOAST_DELAY_TIME : Long = 2 * 1000
        const val SPLASH_TIME_OUT :Long = 1500 // 3000 = 1 sec
    }

    object ACTIVITY_RESULT_CODES {
        const val REQUEST_CODE_PICK_VIDEO = 101
        const val REQUEST_CODE_PICK_IMAGE = 102
    }

    object STRING {
        const val EMPTY = ""
        const val SPACE = " "
        const val NOT_AVAILABLE_IN_SHORT = "NA"

        const val PRESS_BACK_AGAIN_TO_EXIT = "Press back again to exit !"
    }
}