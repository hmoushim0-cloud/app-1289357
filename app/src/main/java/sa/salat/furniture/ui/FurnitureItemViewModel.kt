package sa.salat.furniture.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import sa.salat.furniture.data.FurnitureItemRepository
import sa.salat.furniture.model.FurnitureItem
import sa.salat.furniture.model.TableProduct
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FurnitureItemViewModel(
    private val repository: FurnitureItemRepository
) : ViewModel() {
    val catalog: StateFlow<List<TableProduct>> = repository.getAvailableCatalog()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activeRequest: StateFlow<FurnitureItem?> = repository.getActiveRequest()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun submitNewOrder(request: FurnitureItem) {
        viewModelScope.launch {
            repository.submitRequest(request)
        }
    }
}
