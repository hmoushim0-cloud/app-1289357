package sa.salat.furniture.data

import sa.salat.furniture.model.FurnitureItem
import sa.salat.furniture.model.TableProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface FurnitureItemRepository {
    fun getAvailableCatalog(): Flow<List<TableProduct>>
    fun getActiveRequest(): Flow<FurnitureItem?>
    suspend fun submitRequest(request: FurnitureItem): Result<String>
}

class InMemoryFurnitureItemRepository : FurnitureItemRepository {
    private val _catalog = MutableStateFlow<List<TableProduct>>(listOf(
        TableProduct("1", "طاولة طعام كلاسيكية", "طاولة أنيقة مصنوعة من خشب السنديان، مثالية لتناول الطعام مع العائلة والأصدقاء.", 5000L, "verified"),
        TableProduct("2", "طاولة قهوة عصرية", "تصميم عصري وأنيق، مصنوعة من الزجاج والخشب، تضيف لمسة عصرية لغرفة المعيشة.", 9500L, "bolt")
    ))
    private val _active = MutableStateFlow<FurnitureItem?>(null)

    override fun getAvailableCatalog(): Flow<List<TableProduct>> = _catalog.asStateFlow()
    override fun getActiveRequest(): Flow<FurnitureItem?> = _active.asStateFlow()

    override suspend fun submitRequest(request: FurnitureItem): Result<String> {
        _active.value = request
        return Result.success(request.requestId)
    }
}
