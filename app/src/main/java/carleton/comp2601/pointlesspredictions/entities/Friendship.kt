package carleton.comp2601.pointlesspredictions.entities

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "friendships",
    primaryKeys = ["user_id1", "user_id2"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id1"]
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id2"]
        )
    ]
)
data class Friendship (
    @NonNull
    @ColumnInfo(name = "user_id1")
    var user_id1: Int,

    @ColumnInfo(name = "user_id2")
    var user_id2: Int
) : Parcelable