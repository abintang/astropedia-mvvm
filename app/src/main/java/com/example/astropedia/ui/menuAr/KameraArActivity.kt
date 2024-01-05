package com.example.astropedia.ui.menuAr

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.astropedia.R
import com.example.astropedia.databinding.ActivityKameraArBinding

class KameraArActivity : AppCompatActivity() {
    private val binding by lazy { ActivityKameraArBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val bundle = Bundle()
        bundle.putString("materi", intent.getStringExtra("materi"))
        bundle.putInt("id", intent.getIntExtra("id", 0))
        supportFragmentManager.commit {
            add(R.id.containerFragment, ArKameraFragment::class.java, bundle)
        }

        onClick()
    }

    private fun onClick() {
        binding.btnSelanjutnya.setOnClickListener {
            binding.btnSelanjutnya.visibility = View.GONE
            binding.btnSelesai.visibility = View.VISIBLE
            binding.tvTutorial.text = resources.getText(R.string.tx_tutorial2)
            binding.ivTutorial.setImageResource(R.drawable.moving)
        }

        binding.btnSelesai.setOnClickListener {
            binding.cardTutorial.visibility = View.GONE
        }

        binding.textLewatiTutorial.setOnClickListener {
            binding.cardTutorial.visibility = View.GONE
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this, ListObjekActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}