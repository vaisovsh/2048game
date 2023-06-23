package uz.gita.a2048

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.BuildConfig
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.button.MaterialButton
import uz.gitamy.a2048mySh.MainActivity
import uz.gitamy.a2048mySh.R
import uz.gitamy.a2048mySh.databinding.ActivityEntryBinding

class EntryActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityEntryBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        attachClickListeners()
    }

    private fun attachClickListeners() {
        binding.apply {
            btnPlay.setOnClickListener {
                val intent = Intent(this@EntryActivity, MainActivity::class.java)
                startActivity(intent)
            }
            btnShare.setOnClickListener {
//                share()
                finishAffinity()
            }
            btnInfo.setOnClickListener {
                showDialogAbout()
            }
        }
    }

//    private fun share() {
//        val shareIntent = Intent(Intent.ACTION_SEND)
//        shareIntent.type = "text/plain"
//        shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name)
//        val shareMessage = "Download 2048 game, it is easy to play\nhttps://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
//        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
//        startActivity(Intent.createChooser(shareIntent, "Share via"))
//    }

    private fun showDialogAbout(){
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_about,null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        val btnOk:MaterialButton = view.findViewById(R.id.btn_ok)
        btnOk.setOnClickListener {
            dialog.cancel()
        }
        val linkm = view.findViewById<TextView>(R.id.textlink)
//        linkm.setMovementMethod(LinkMovementMethod.getInstance())
        linkm.movementMethod=LinkMovementMethod.getInstance()

    }
}