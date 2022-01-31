package dev.egarcia.eprofiles.profiles.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import dev.egarcia.eprofiles.R
import dev.egarcia.eprofiles.profiles.viewmodel.ProfileViewModel
import dev.egarcia.eprofiles.profiles.viewmodel.ProfileViewState

@ExperimentalPagerApi
@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private val viewModel: ProfileViewModel by viewModels()

    companion object {
        private const val TAG = "ProfileActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewState.observe(this) {
            setContent { MyScreen(it) }
        }
    }

    @Composable
    private fun MyScreen(profileViewState: ProfileViewState) {
        if (profileViewState.isLoading) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        } else {
            HorizontalPager(profileViewState.userProfiles.size) { page ->
                // Each page's content
                val user = profileViewState.userProfiles[page]
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    for (configItem in profileViewState.profileConfig) {
                        when (configItem) {
                            "name" -> {
                                val title = getString(R.string.title_name)
                                Text(
                                    text = "$title ${user.name}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                            "photo" -> {
                                GlideImage(
                                    imageModel = user.photo,
                                    circularReveal = CircularReveal(duration = 250),
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .width(256.dp)
                                        .height(256.dp),
                                    failure = {
                                        Text(
                                            text = getString(R.string.error_image_failed),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                )
                            }
                            "gender" -> {
                                val title = getString(R.string.title_gender)
                                Text(
                                    text = "$title ${user.gender ?: getString(R.string.error_null)}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                            "about" -> {
                                val title = getString(R.string.title_about)
                                Text(
                                    text = "$title ${user.about ?: getString(R.string.error_null)}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    textAlign = TextAlign.Center
                                )

                            }
                            "school" -> {
                                val title = getString(R.string.title_school)
                                Text(
                                    text = "$title ${user.school ?: getString(R.string.error_null)}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                            "hobbies" -> {
                                Text(
                                    text = getString(R.string.title_hobbies),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                                    textAlign = TextAlign.Center
                                )
                                if (user.hobbies != null) {
                                    for (hobby in user.hobbies) {
                                        Text(
                                            text = hobby,
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                } else {
                                    Text(
                                        text = getString(R.string.error_null),
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}