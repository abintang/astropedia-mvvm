package com.example.astropedia.ui.mainMenu

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.astropedia.data.model.UserModel
import com.example.astropedia.databinding.ActivityMainMenuBinding
import com.example.astropedia.ui.menuAr.ListObjekActivity
import com.example.astropedia.ui.menuGame.MenuGameActivity
import com.example.astropedia.ui.menuMateri.ListMateriActivity
import com.example.astropedia.ui.menuPanduan.DetailPanduanActivity
import com.example.astropedia.viewmodel.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class MainMenuActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainMenuBinding.inflate(layoutInflater) }
    private val auth = FirebaseAuth.getInstance()
    private val firebaseUser = auth.currentUser
    private val registerViewModel: RegisterViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (firebaseUser != null) {
            Glide.with(this).load(firebaseUser.photoUrl).into(binding.ivAvatar)
        }

        binding.btnMateri.setOnClickListener {
            startActivity(Intent(this, ListMateriActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }

        binding.btnArCamera.setOnClickListener {
            startActivity(Intent(this, ListObjekActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }

        binding.btnGame.setOnClickListener {
            startActivity(Intent(this, MenuGameActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }

        binding.btnPanduan.setOnClickListener {
            startActivity(Intent(this, DetailPanduanActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    override fun onResume() {
        super.onResume()

        val settings = PreferenceManager.getDefaultSharedPreferences(this)
        val lastTimeStarted = settings.getInt("last_time_started", -1)
        val calendar = Calendar.getInstance()
        val today = calendar[Calendar.DAY_OF_YEAR]

        if (today != lastTimeStarted) {
            assert(firebaseUser != null)
            val userData = UserModel(firebaseUser?.email, firebaseUser?.uid,
                firebaseUser?.displayName, firebaseUser?.photoUrl.toString(), firebaseUser?.uid)
            registerViewModel.postUserData(userData)
            val editor = settings.edit()
            editor.putInt("last_time_started", today)
            editor.apply()
        }
    }
}



