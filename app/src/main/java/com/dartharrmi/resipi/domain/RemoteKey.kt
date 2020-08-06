package com.dartharrmi.resipi.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * When remote keys are not directly associated with list items, it is best to store them in a separate table in the local database.
 */
@Entity(tableName = "remote_keys")
data class RemoteKey(@PrimaryKey val label: String, val nextKey: Int?)