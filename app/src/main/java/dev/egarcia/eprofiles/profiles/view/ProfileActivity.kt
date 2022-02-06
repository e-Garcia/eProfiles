package dev.egarcia.eprofiles.profiles.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ImageBySource
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import dev.egarcia.eprofiles.R
import dev.egarcia.eprofiles.profiles.viewmodel.ProfileViewEvents
import dev.egarcia.eprofiles.profiles.viewmodel.ProfileViewModel
import dev.egarcia.eprofiles.profiles.viewmodel.ProfileViewState
import dev.egarcia.eprofiles.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var pagerState: PagerState
    private lateinit var pagerScope: CoroutineScope

    companion object {
        private const val TAG = "ProfileActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewState.observe(this) {
            setContent { ProfilePagerScreen(it) }
        }

        viewModel.viewEvents.observe(this) {
            processEvent(it)
        }
    }

    private fun processEvent(newEvent: ProfileViewEvents) {
        when (newEvent) {
            is ProfileViewEvents.onLikeClicked -> {
                pagerScope.launch {
                    val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                    pagerState.scrollToPage(nextPage)
                }
            }
        }
    }

    @Composable
    private fun ProfilePagerScreen(profileViewState: ProfileViewState) {
        if (profileViewState.isLoading) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        } else {
            pagerState = rememberPagerState()
            pagerScope = rememberCoroutineScope()
            HorizontalPager(profileViewState.userProfiles.size, state = pagerState) { page ->
                // Each page's content
                val user = profileViewState.userProfiles[page]
                Scaffold(
                    content = {
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
                                                .padding(spacing_s),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                    "photo" -> {
                                        GlideImage(
                                            imageModel = user.photo,
                                            circularReveal = CircularReveal(duration = 250),
                                            modifier = Modifier
                                                .padding(spacing_s)
                                                .width(size_image_l)
                                                .height(size_image_l),
                                            failure = {
                                                Text(
                                                    text = getString(R.string.error_image_failed),
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(spacing_s),
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
                                                .padding(spacing_s),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                    "about" -> {
                                        val title = getString(R.string.title_about)
                                        Text(
                                            text = "$title ${user.about ?: getString(R.string.error_null)}",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(spacing_s),
                                            textAlign = TextAlign.Center
                                        )

                                    }
                                    "school" -> {
                                        val title = getString(R.string.title_school)
                                        Text(
                                            text = "$title ${user.school ?: getString(R.string.error_null)}",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(spacing_s),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                    "hobbies" -> {
                                        Text(
                                            text = getString(R.string.title_hobbies),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    top = spacing_s,
                                                    start = spacing_s,
                                                    end = spacing_s
                                                ),
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
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            modifier = Modifier
                                .height(size_xl)
                                .width(size_xl),
                            onClick = {
                                user.id?.let { viewModel.onLike(it) }
                            }) {
                            GlideImage(
                                imageModel = R.drawable.ic_like,
                                modifier = Modifier
                                    .width(size_l)
                                    .height(size_l),
                                alignment = Alignment.Center
                            )
                        }
                    }
                )

            }
        }
    }
}