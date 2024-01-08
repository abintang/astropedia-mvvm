package com.example.astropedia.viewmodel

import android.content.Context
import android.net.Uri
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.astropedia.R
import com.google.android.filament.utils.normal
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Camera
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.gorisse.thomas.sceneform.scene.await

class KameraArViewModel: ViewModel() {
    private var _objekValue = MutableLiveData<String?>()
    private var model: Renderable? = null
    private var modelView: ViewRenderable? = null
    private var _isGoToDetail = MutableLiveData<Boolean>()
    var isGoToDetailLiveData: LiveData<Boolean> = _isGoToDetail

    suspend fun loadModels(context: Context?) {
        model = ModelRenderable.builder()
            .setSource(context, Uri.parse("models/${_objekValue.value}.glb"))
            .setIsFilamentGltf(true)
            .await()
        modelView = ViewRenderable.builder()
            .setView(context, R.layout.model)
            .await()
    }

    suspend fun updateModels(context: Context?, models: String?) {
        _objekValue.value = models
        model = ModelRenderable.builder()
            .setSource(context, Uri.parse("models/$models.glb"))
            .setIsFilamentGltf(true)
            .await()
        modelView = ViewRenderable.builder()
            .setView(context, R.layout.model)
            .await()
    }

    fun setObjekValue(value: String?) {
        _objekValue.value = value
    }

    fun handleTapPlane(arFragment: ArFragment, hitResult: HitResult, plane: Plane, motionEvent: MotionEvent) {
        val scene = arFragment.arSceneView.scene
        scene.addChild(AnchorNode(hitResult.createAnchor()).apply {
            addChild(TransformableNode(arFragment.transformationSystem).apply {
                renderable = model
                renderableInstance.setCulling(false)
                renderableInstance.animate(true).start()
                addChild(Node().apply {
                    localPosition = Vector3(0.0f, 0.35f, 0.0f)
                    renderable = modelView
                    val viewRenderable: ViewRenderable = this.renderableInstance.renderable as ViewRenderable
                    val view: View = viewRenderable.view
                    val textView: TextView = view.findViewById(R.id.tv_nama_card)
                    textView.text = _objekValue.value
                    val button: Button = view.findViewById(R.id.buttonDetail)
                    button.setOnClickListener {
                        _isGoToDetail.value = true
                    }

                })
            })
        })
    }

    private fun removeAllChildren(node: Node) {
        val children: List<Node> = ArrayList(node.children)
        for (child in children) {
            if (child is Camera) {
                continue
            }
            removeAllChildren(child)
            child.parent = null
        }
    }
    fun removeAllNode(scene: Scene) {
        val nodes: List<Node> = ArrayList(scene.children)
        for (node in nodes) {
            if (node is Camera) {
                removeAllChildren(node)
            } else {
                scene.removeChild(node)
            }
        }
    }
}