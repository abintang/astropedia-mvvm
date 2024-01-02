package com.example.astropedia.ui.menuGame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.astropedia.databinding.ActivityMenuGameBinding
import com.example.astropedia.ui.mainMenu.MainMenuActivity
import com.google.firebase.auth.FirebaseAuth

class MenuGameActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMenuGameBinding.inflate(layoutInflater) }
    private val auth = FirebaseAuth.getInstance()
    private val firebaseUser = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mToolbar = binding.toolbarGame
        mToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        Glide.with(this).load(firebaseUser?.photoUrl).into(binding.ivProfilePicture)
        binding.tvNama.text = firebaseUser?.displayName

        binding.btnMulai.setOnClickListener {
            startActivity(Intent(this, PlayGameActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }

        binding.btnRanking.setOnClickListener {
            startActivity(Intent(this, RankingActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }


    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainMenuActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}