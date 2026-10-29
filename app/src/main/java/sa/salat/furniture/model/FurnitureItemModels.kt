package sa.salat.furniture.model

import kotlinx.serialization.Serializable

@Serializable
data class TableProduct(
    val id: String,
    val title: String,
    val description: String,
    val priceMinorUnits: Long,
    val iconVector: String = "verified"
)

@Serializable
data class FurnitureItem(
    val requestId: String,
    val items: List<TableProduct>,
    val customerPhone: String,
    val totalAmountMinorUnits: Long,
    val status: FurnitureItemStatus = FurnitureItemStatus.PENDING,
    val createdAtEpochMs: Long = System.currentTimeMillis()
)

enum class FurnitureItemStatus {
    PENDING,
    ACCEPTED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}
