package carleton.comp2601.pointlesspredictions.entities

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "predictions")
data class Prediction (
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "prediction_id")
    val prediction_id: Int,

    @NonNull
    @ColumnInfo(name = "user_id")
    val user_id: Int,

    @ColumnInfo(name = "premise")
    val premise: String,

    @ColumnInfo(name = "date_placed")
    val datePlaced: String,

    @ColumnInfo(name = "expiration_date")
    val expirationDate: String,

    @ColumnInfo(name = "confirmation_status")
    val confirmationStatus: Boolean?=null
) : Parcelable