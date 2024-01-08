package com.example.astropedia.ui.menuAr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.astropedia.R
import com.example.astropedia.databinding.FragmentArKameraBinding
import com.example.astropedia.ui.listener.OnPassData
import com.example.astropedia.ui.menuMateri.DetailMateriActivity
import com.example.astropedia.viewmodel.KameraArViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.SceneView
import com.google.ar.sceneform.ux.ArFragment
import com.gorisse.thomas.sceneform.light.LightEstimationConfig
import kotlinx.coroutines.launch

class ArKameraFragment : Fragment(R.layout.fragment_ar_kamera){
    private lateinit var kameraArViewModel: KameraArViewModel
    private lateinit var arFragment: ArFragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel(view)
    }

    private fun setViewModel(view: View) {
        kameraArViewModel = ViewModelProvider(this)[KameraArViewModel::class.java]
        kameraArViewModel.setObjekValue(arguments?.getString("materi"))

        kameraArViewModel.isGoToDetailLiveData.observe(viewLifecycleOwner) {
            if (it) {
                val intent = Intent(context, DetailMateriActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("idMateri", arguments?.getInt("id"))
                startActivity(intent)
            }
        }

        arFragment = (childFragmentManager.findFragmentById(R.id.arFragment) as ArFragment).apply {
            setOnSessionConfigurationListener { session, config ->
                arFragment.arSceneView._lightEstimationConfig = LightEstimationConfig.DISABLED
            }
            setOnViewCreatedListener { arSceneView ->
                arSceneView.setFrameRateFactor(SceneView.FrameRate.FULL)
                val instructionsController = arFragment.instructionsController
                if (instructionsController != null) {
                    instructionsController.isEnabled = false
                }
            }
            setOnTapArPlaneListener(::onTapPlane)
        }

        lifecycleScope.launchWhenCreated {
            kameraArViewModel.loadModels(context)
        }

        val binding = FragmentArKameraBinding.bind(view)
        binding.deleteButton.setOnClickListener {
            kameraArViewModel.removeAllNode(arFragment.arSceneView.scene)
        }

    }

    private fun onTapPlane(hitResult: HitResult, plane: Plane, motionEvent: MotionEvent) {
        kameraArViewModel.handleTapPlane(arFragment,hitResult, plane, motionEvent)
    }

    fun updateData(bundle: Bundle) {
        val updateName = bundle.getString("updateName")
        val updateId = bundle.getInt("updateId")
        val updateCondition = bundle.getBoolean("updateCondition")

        if (updateCondition) {
            lifecycleScope.launch {
                kameraArViewModel.updateModels(context, arguments?.getString("updateName"))
            }
        }
    }
}