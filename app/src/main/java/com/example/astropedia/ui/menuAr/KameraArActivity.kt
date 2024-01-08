package com.example.astropedia.ui.menuAr

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.astropedia.R
import com.example.astropedia.databinding.ActivityKameraArBinding
import com.example.astropedia.ui.listener.OnPassData
import com.example.astropedia.viewmodel.KameraArViewModel

class KameraArActivity : AppCompatActivity(), OnPassData {
    private val binding by lazy { ActivityKameraArBinding.inflate(layoutInflater) }
    private lateinit var bottomSheetUbahObjekFragment: BottomSheetUbahObjekFragment
    val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bundle.putString("materi", intent.getStringExtra("materi"))
        bundle.putInt("id", intent.getIntExtra("id", 3))
        supportFragmentManager.commit {
            add(R.id.containerFragment, ArKameraFragment::class.java, bundle)
        }

        onClick()
    }

    private fun onClick() {
        binding.btnSelanjutnya.setOnClickListener {
            binding.btnSelanjutnya.visibility = View.INVISIBLE
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

        binding.btnGantiModel.setOnClickListener {
            bottomSheetUbahObjekFragment = BottomSheetUbahObjekFragment()
            bottomSheetUbahObjekFragment.show(supportFragmentManager, "BSDialogFragment")
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this, ListObjekActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    override fun passDataObjek(name: String?, id: Int?) {
        bundle.putString("updateName", name)
        if (id != null) {
            bundle.putInt("updateId", id)
        }
        bundle.putBoolean("updateCondition", true)
        val fragment = supportFragmentManager.findFragmentById(R.id.containerFragment) as? ArKameraFragment
        fragment?.updateData(bundle)
    }
}