package com.demo.popcornapp.utils

import com.demo.popcornapp.utils.Clock

class AndroidClock : Clock {

    override fun currentTime(): Long = System.currentTimeMillis()
}