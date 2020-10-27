package me.brad.dao

import me.brad.model.InventoryItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InventoryDao : JpaRepository<InventoryItem, String>
