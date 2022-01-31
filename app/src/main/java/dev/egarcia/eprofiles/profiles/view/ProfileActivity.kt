package dev.egarcia.eprofiles.profiles.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.egarcia.eprofiles.R
import dev.egarcia.eprofiles.profiles.viewmodel.ProfileViewModel
import dev.egarcia.eprofiles.profiles.viewmodel.ProfileViewState

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private val viewModel: ProfileViewModel by viewModels()

    companion object {
        private const val TAG = "ProfileActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        viewModel.viewState.observe(this, { render(it) })
    }

    private fun render(profileViewState: ProfileViewState) {
        Log.d(TAG, profileViewState.toString())
        //TODO: Render UI in REQ-03
    }
}