package me.brad.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class InventoryItem(@Id var itemName: String = "", var itemCount: Int = 0)
