package uz.gita.a2048.data.source.local

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences private constructor(context: Context) {

    private var pref: SharedPreferences
    private val SCORE = "SCORE"
    private val RECORD = "RECORD"
    private val VALUES = "VALUES"
    private val OLD_VALUES = "OLD_VALUES"
    private val SCORE_TO_DECREASE = "SCORE_TO_DECREASE"

    init {
        pref = context.getSharedPreferences("2048", Context.MODE_PRIVATE)
    }


    companion object {
        private lateinit var instance: MySharedPreferences

        fun init(context: Context) {
            if (!::instance.isInitialized) {
                instance = MySharedPreferences(context)
            }
        }

        fun getInstance(): MySharedPreferences = instance
    }

    var score: Int
        set(value) = pref.edit().putInt(SCORE, value).apply()
        get() = pref.getInt(SCORE, 0)

    var record: Int
        set(value) = pref.edit().putInt(RECORD, value).apply()
        get() = pref.getInt(RECORD, 0)

    var values: String
        set(value) = pref.edit().putString(VALUES, value).apply()
        get() = pref.getString(VALUES, "").toString()

    var oldValues: String
        set(value) = pref.edit().putString(OLD_VALUES, value).apply()
        get() = pref.getString(OLD_VALUES, "").toString()

    var decrease: Int
        set(value) = pref.edit().putInt(SCORE_TO_DECREASE, value).apply()
        get() = pref.getInt(SCORE_TO_DECREASE, 0)
}