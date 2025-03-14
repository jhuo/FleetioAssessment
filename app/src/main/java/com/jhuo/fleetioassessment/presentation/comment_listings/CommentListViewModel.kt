package com.jhuo.fleetioassessment.presentation.comment_listings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhuo.fleetioassessment.domain.repository.VehicleRepository
import com.jhuo.fleetioassessment.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentListViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val vehicleId = savedStateHandle.get<Int>("vehicleId")

    private var _commentListState = MutableStateFlow(CommentListState())
    val commentListState = _commentListState.asStateFlow()

    init {
        getCommentList(true)
    }

    private fun getCommentList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _commentListState.update {
                it.copy(isLoading = true)
            }

            vehicleRepository.getCommentsListByVehicleId(
                vehicleId?: -1,
                forceFetchFromRemote,
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _commentListState.update {
                            it.copy(isLoading = false, error = result.message)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { commentList ->
                            _commentListState.update {
                                it.copy(
                                    commentsList = commentList
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _commentListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }
}