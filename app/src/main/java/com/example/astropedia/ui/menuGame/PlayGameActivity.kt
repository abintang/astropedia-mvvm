package com.example.astropedia.ui.menuGame

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.astropedia.R
import com.example.astropedia.data.model.GameModel
import com.example.astropedia.data.model.PointModel
import com.example.astropedia.databinding.ActivityPlayGameBinding
import com.example.astropedia.viewmodel.GameViewModel
import com.example.astropedia.viewmodel.TokenViewModel
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth

class PlayGameActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPlayGameBinding.inflate(layoutInflater) }
    private lateinit var gameViewModel: GameViewModel
    private lateinit var tokenViewModel: TokenViewModel
    private val auth = FirebaseAuth.getInstance()
    private val firebaseUser = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setViewModel()
    }

    private fun setViewModel() {
        gameViewModel = ViewModelProvider(this@PlayGameActivity)[GameViewModel::class.java]
        gameViewModel.getDataGame()

        gameViewModel.observeGetDataGameLiveData().observe(this) { gameData ->
            var number = 0
            gameViewModel.updateSoalGame(gameData, number)

            binding.btnNext.setOnClickListener {
                if (number == 9) {
                    binding.btnNext.isEnabled = false
                    getTokenAndInsertPoint(firebaseUser?.uid)
                } else {
                    gameViewModel.disableAction()
                    number++
                    gameViewModel.updateSoalGame(gameData, number)
                }
            }
        }

        gameViewModel.soalGameLiveData.observe(this) {
            binding.tvSoal.text = it.soalGame
            binding.tvAnswer1.text = it.jawabanA
            binding.tvAnswer2.text = it.jawabanB
            binding.tvAnswer3.text = it.jawabanC
            binding.tvAnswer4.text = it.jawabanD
            Glide.with(applicationContext)
                .load("https://tata-surya.skripsijoss.my.id/public/quiz/" + it.urlGambar)
                .into(binding.ivSoalQuiz)
            binding.tvNumberQuiz.text = resources
                .getString(R.string.tx_noGame, it.currentNumber.toString())

            it.corectAnswer?.let { correctAnswer ->
                it.jawabanA?.let { a ->
                    setAnswerButtonOnclick(binding.btnAnswer1,
                        a, correctAnswer)
                }
                it.jawabanB?.let { b ->
                    setAnswerButtonOnclick(binding.btnAnswer2,
                        b, correctAnswer)
                }
                it.jawabanC?.let { c->
                    setAnswerButtonOnclick(binding.btnAnswer3,
                        c,correctAnswer)
                }
                it.jawabanD?.let { d ->
                    setAnswerButtonOnclick(binding.btnAnswer4,
                        d, correctAnswer)
                }
            }
        }

        gameViewModel.timerLiveData.observe(this) {
            binding.tvTimer.text = it
        }

        gameViewModel.correctBarLiveData.observe(this) {
            if (it == true) {
                binding.cvCorrectSection.visibility = View.VISIBLE
                setAnimationDown( binding.cvCorrectSection)
            } else {
                binding.cvCorrectSection.visibility = View.INVISIBLE
            }
        }

        gameViewModel.falseBarLiveData.observe(this) {
            if (it == true) {
                binding.cvUncorrectSection.visibility = View.VISIBLE
                setAnimationDown( binding.cvUncorrectSection)
            } else {
                binding.cvUncorrectSection.visibility = View.INVISIBLE
            }
        }

        gameViewModel.nextBarLiveData.observe(this) {
            if (it == true) {
                binding.cvNextSection.visibility = View.VISIBLE
                setAnimationUp( binding.cvNextSection)
            } else {
                binding.cvNextSection.visibility = View.INVISIBLE
            }
        }

        gameViewModel.scoreLivedata.observe(this) {
            binding.tvScore.text = it.toString()
        }

        gameViewModel.loadingLivedata.observe(this) {
            if (it) {
                binding.cvProgressBar.visibility = View.VISIBLE
            } else {
                binding.cvProgressBar.visibility = View.GONE
            }
        }

        gameViewModel.insertPointResult.observe(this) {
            if (it.success) {
                nextPage()
            } else {
                Toast.makeText(applicationContext, "Failed to Input Data", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getTokenAndInsertPoint(id: String?) {
        tokenViewModel = ViewModelProvider(this@PlayGameActivity)[TokenViewModel::class.java]
        tokenViewModel.getTokenUser(id)
        tokenViewModel.observeGetTokenLiveData().observe(this) { user ->
            gameViewModel.jawabanBenarLiveData.observe(this) {
                val pointModel = PointModel(
                    user.id.toString(),
                    binding.tvScore.text.toString(),
                    "$it/10", binding.tvTimer.text.toString()
                )
                gameViewModel.postDataPoint(pointModel, user.apiToken.toString())
            }
        }
    }

    private fun setAnimationUp(cardView: MaterialCardView) {
        val animation = TranslateAnimation(0f, 0f, cardView.height.toFloat(), 0f)
        animation.duration = 500
        cardView.startAnimation(animation)
    }

    private fun setAnimationDown(cardView: MaterialCardView) {
        val animation = TranslateAnimation(0f, 0f, -cardView.height.toFloat(), 0f)
        animation.duration = 500
        cardView.startAnimation(animation)
    }

    private fun disableButton() {
        binding.btnAnswer1.isClickable = false
        binding.btnAnswer2.isClickable = false
        binding.btnAnswer3.isClickable = false
        binding.btnAnswer4.isClickable = false
    }

    private fun setAnswerButtonOnclick(button: MaterialCardView, jawabanBenar: String, jawaban: String) {
        button.setOnClickListener {
            gameViewModel.performAction()
            gameViewModel.answerButton(jawabanBenar, jawaban)
            disableButton()
        }
    }

    private fun nextPage() {
        startActivity(Intent(this, RankingActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        finish()
    }

}