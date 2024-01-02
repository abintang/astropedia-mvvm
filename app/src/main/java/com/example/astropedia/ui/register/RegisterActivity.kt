package com.example.astropedia.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.astropedia.databinding.ActivityRegisterBinding
import com.example.astropedia.ui.mainMenu.MainMenuActivity
import com.example.astropedia.viewmodel.RegisterViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val registerViewModel: RegisterViewModel by viewModels()
    private val auth = FirebaseAuth.getInstance()

    private val RC_SIGN_IN = 9001

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(binding.root)

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("266247651691-j1pa7rv057iqaf1sj7ldisinf94j0hha.apps.googleusercontent.com")
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(this, gso)

            binding.btnRegister.setOnClickListener {
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)

            }

            binding.btnNonRegister.setOnClickListener {  }

            registerViewModel.signInResult.observe(this) { result ->
                if (result.success) {
                    navigateToNextPage()

                }
            }

            checkPreviousSignIn()

    }


    private fun checkPreviousSignIn() {
        if (auth.currentUser != null) {
            navigateToNextPage()
        }

    }

    private fun navigateToNextPage() {
        startActivity(
            Intent(
                this@RegisterActivity,
                MainMenuActivity::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )

        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account?.idToken
            if (idToken != null) {
                registerViewModel.signInWithGoogle(idToken)
            }

        } catch (e: ApiException) {
            Toast.makeText(this, "Sign-in failed: ${e.statusCode}", Toast.LENGTH_SHORT).show()
        }
    }

}