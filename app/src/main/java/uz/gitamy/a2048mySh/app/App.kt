package uz.gita.a2048.app

import android.app.Application
import uz.gita.a2048.data.source.local.MySharedPreferences

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MySharedPreferences.init(this)

    }
}