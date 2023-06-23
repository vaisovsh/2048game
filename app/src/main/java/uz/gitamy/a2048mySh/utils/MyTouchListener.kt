package uz.gita.a2048.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import uz.gita.a2048.model.SideEnum
import kotlin.math.abs

class MyTouchListener(context:Context) : View.OnTouchListener {

    private lateinit var onMoveListener:(SideEnum)->Unit

    private val gestureDetector =GestureDetector(context,MyGestureListener())
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return true
    }

    inner class MyGestureListener:GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            if (abs(e2.x - e1.x)>60|| abs(e2.y-e1.y)>60){
                if (abs(e2.x-e1.x)> abs(e2.y-e1.y)){
                    //horizontal
                    if (e2.x>e1.x){
                        // right
                        onMoveListener.invoke(SideEnum.RIGHT)
                    }
                    else{
                        //left
                        onMoveListener.invoke(SideEnum.LEFT)
                    }

                }else{
                    //vertical
                    if (e2.y>e1.y){
                        //down
                        onMoveListener.invoke(SideEnum.DOWN)
                    }
                    else {
                        //up

                        onMoveListener.invoke(SideEnum.UP)
                    }
                }
            }

            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }


    fun setOnMoveListener(block:(SideEnum)->Unit){
        onMoveListener = block
    }

}