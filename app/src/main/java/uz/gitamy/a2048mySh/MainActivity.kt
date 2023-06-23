package uz.gitamy.a2048mySh

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.a2048.data.source.local.MySharedPreferences
import uz.gita.a2048.model.SideEnum
import uz.gita.a2048.repository.AppRepository
import uz.gita.a2048.utils.BackgroundUtil
import uz.gita.a2048.utils.MyTouchListener
import uz.gitamy.a2048mySh.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val textViews = arrayListOf<TextView>()
    private lateinit var mainView: LinearLayout
    private lateinit var repository: AppRepository
    private lateinit var container: ConstraintLayout
    private val bgUtil = BackgroundUtil()
    private val binding by viewBinding(ActivityMainBinding::bind)

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        loadViews()
        val listener = MyTouchListener(this)
        repository = AppRepository.getInstance()
        describeValuesToViews()
        if (MySharedPreferences.getInstance().oldValues == "")
            binding.btnUndo.isEnabled = false
        listener.setOnMoveListener {
            when (it) {
                SideEnum.RIGHT -> {
                    if (repository.moveToRight()) {
                        binding.btnUndo.isEnabled = true
                    }
                    describeValuesToViews()
                }
                SideEnum.LEFT -> {
                    if (repository.moveToLeft())
                        binding.btnUndo.isEnabled = true
                    describeValuesToViews()
                }
                SideEnum.UP -> {
                    if (repository.moveUp())
                        binding.btnUndo.isEnabled = true
                    describeValuesToViews()
                }
                SideEnum.DOWN -> {
                    if (repository.moveDown())
                        binding.btnUndo.isEnabled = true
                    describeValuesToViews()
                }
            }
            if (repository.isFinished()) {
                binding.txtGameOver.visibility = View.VISIBLE
                binding.btnRestart.setBackgroundResource(R.drawable.bg_item_8)
                binding.btnUndo.isEnabled = true
            }
        }
        container.setOnTouchListener(listener)
        attachClickListeners()

    }


    private fun loadViews() {
        mainView = findViewById(R.id.main_view)
        container = findViewById(R.id.container_main)
        for (i in 0 until mainView.childCount) {
            val linear = mainView.getChildAt(i) as LinearLayout
            for (j in 0 until linear.childCount) {
                textViews.add(linear.getChildAt(j) as TextView)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun describeValuesToViews() {
        val matrix = repository.matrix

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                textViews[i * 4 + j].apply {
                    text = if (matrix[i][j] == 0) ""
                    else matrix[i][j].toString()
                    if (matrix[i][j] == 2 || matrix[i][j] == 4) {
                        setTextColor(resources.getColor(R.color.txt_color_black))
                    } else {
                        setTextColor(resources.getColor(R.color.white))
                    }
                    setBackgroundResource(bgUtil.getBgByAmount(matrix[i][j]))
                }
            }
        }

        binding.apply {
            txtScore.text = "${repository.score}"
            if (repository.score > repository.record) {
                repository.record = repository.score
            }
            txtRecord.text = "${repository.record}"
            if (repository.isFinished()) {
                txtGameOver.visibility = View.VISIBLE
                binding.btnRestart.setBackgroundResource(R.drawable.bg_item_8)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun attachClickListeners() {
        binding.apply {
            btnRestart.setOnClickListener {
                if (repository.isFinished()) {
                    binding.txtGameOver.visibility = View.GONE
                    binding.btnRestart.setBackgroundResource(R.drawable.bg_main_view)
                }
                repository.restart()
                describeValuesToViews()

            }
            btnHome.setOnClickListener {
                finish()
            }
            btnUndo.setOnClickListener {
                if (repository.isFinished()) {
                    binding.btnRestart.setBackgroundResource(R.drawable.bg_main_view)
                    binding.txtGameOver.visibility = View.GONE
                }

                repository.undo()
                describeValuesToViews()
                btnUndo.isEnabled = false

            }
        }
    }

    override fun onPause() {
        super.onPause()
        repository.saveValues()
        repository.saveRecord()
        repository.saveScore()
        repository.saveOldValues()
        repository.saveScoreToDecrease()
    }

}