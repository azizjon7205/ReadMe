package me.ruyeo.kitobz.ui.profile.orderhistory

import me.ruyeo.kitobz.ui.basket.information.InfoModel

data class OHistoryModel(
    var number: Int,
    var books : ArrayList<InfoModel>
)
