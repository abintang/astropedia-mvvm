package com.example.astropedia.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.astropedia.R
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.gorisse.thomas.sceneform.scene.await

class KameraArViewModel: ViewModel() {

    private lateinit var model: ModelRenderable
    private lateinit var  modelView: ViewRenderable


    private suspend fun loadModels(context: Context, value: String) {
        model = ModelRenderable.builder()
            .setSource(context, Uri.parse("models/$value.glb"))
            .setIsFilamentGltf(true)
            .await()
        modelView = ViewRenderable.builder()
            .setView(context, R.layout.model)
            .await()
    }
}