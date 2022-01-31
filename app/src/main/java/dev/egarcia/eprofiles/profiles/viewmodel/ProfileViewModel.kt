package dev.egarcia.eprofiles.profiles.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.egarcia.eprofiles.profiles.service.repository.ProfileRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _viewState = MutableLiveData(ProfileViewState())
    val viewState: LiveData<ProfileViewState> = _viewState

    init {
        //TODO: Given more time, I would add error handling
        _viewState.value = _viewState.value?.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val usersDeferred = async { profileRepository.getUsers() }
                val configDeferred = async { profileRepository.getConfig() }
                _viewState.value = _viewState.value?.copy(
                    isLoading = false,
                    userProfiles = usersDeferred.await().users,
                    profileConfig = configDeferred.await().profileList
                )
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

}