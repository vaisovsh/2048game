package uz.gita.a2048.utils

import android.os.Build
import androidx.annotation.RequiresApi
import uz.gitamy.a2048mySh.R

class BackgroundUtil {

    private val backgroundMap = HashMap<Int,Int>()

    init {
        loadMap()
    }

    private fun loadMap(){
        backgroundMap[0] = R.drawable.bg_item_0
        backgroundMap[2] = R.drawable.bg_item_2
        backgroundMap[4] = R.drawable.bg_item_4
        backgroundMap[8] = R.drawable.bg_item_8
        backgroundMap[16] = R.drawable.bg_item_16
        backgroundMap[32] = R.drawable.bg_item_32
        backgroundMap[64] = R.drawable.bg_item_64
        backgroundMap[128] = R.drawable.bg_item_128
        backgroundMap[256] = R.drawable.bg_item_256
        backgroundMap[512] = R.drawable.bg_item_512
        backgroundMap[1024] = R.drawable.bg_item_1024
        backgroundMap[2048] = R.drawable.bg_item_2048
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getBgByAmount(amount:Int):Int{
        return backgroundMap.getOrDefault(amount,R.drawable.bg_item_0)
    }

}