package me.ruyeo.kitobz.utils.bounce

interface OnOverPullListener
{
    fun onOverPulledTop(deltaDistance: Float)

    fun onOverPulledBottom(deltaDistance: Float)

    fun onRelease()
}