package carleton.comp2601.pointlesspredictions.entities

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "user_id")
    var user_id: Int,

    @ColumnInfo(name = "userName")
    var userName: String,

    @ColumnInfo(name = "password")
    var password: String
) : Parcelable