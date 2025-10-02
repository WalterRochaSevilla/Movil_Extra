package com.calyrsoft.ucbp1.features.dollar.data.datasource

import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.calyrsoft.ucbp1.features.dollar.domain.model.TYPE
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

enum class DollarTag(val tag: String) {
    OFFICIAL("dollarOfficial"), PARALLEL("dollarParallel")
}

class RealTimeRemoteDataSource {

    suspend fun getDollarUpdates(): Flow<DollarModel> = callbackFlow {
        val callback = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                close(p0.toException())
            }
            override fun onDataChange(p0: DataSnapshot) {
                val value = p0.getValue(DollarModel::class.java)
                if (value != null) {
                    trySend(value.copy(type = TYPE.OFFICIAL))
                }
            }
        }

//         Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference(DollarTag.OFFICIAL.tag)
        myRef.addValueEventListener(callback)

        awaitClose {
            myRef.removeEventListener(callback)
        }
    }

    suspend fun getDollarParallelUpdates(): Flow<DollarModel> = callbackFlow {
        val callback = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                close(p0.toException())
            }
            override fun onDataChange(p0: DataSnapshot) {
                val value = p0.getValue(DollarModel::class.java)
                if (value != null) {
                    trySend(value.copy(type = TYPE.PARALLEL))
                }
            }
        }

        val database = Firebase.database
        val myRef = database.getReference(DollarTag.PARALLEL.tag)
        myRef.addValueEventListener(callback)

        awaitClose {
            myRef.removeEventListener(callback)
        }
    }
}