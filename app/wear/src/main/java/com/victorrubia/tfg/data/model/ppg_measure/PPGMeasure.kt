package com.victorrubia.tfg.data.model.ppg_measure


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.victorrubia.tfg.data.converters.Converters
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.ZonedDateTime
import kotlinx.serialization.*
import java.text.SimpleDateFormat
import java.util.*

object DateSerializer : KSerializer<Date> {
    override val descriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Date {
        return SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS", Locale("es", "ES")).parse(decoder.decodeString())!!
    }

    override fun serialize(encoder: Encoder, value: Date) {
        val df = SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS", Locale("es", "ES"))
        encoder.encodeString(df.format(value))
    }
}

@Entity(tableName = "ppg_measures")
@Serializable
data class PPGMeasure(
    @PrimaryKey
    val measure: Int,
    @TypeConverters(Converters::class)
    @Serializable(with = DateSerializer::class)
    val date: Date,
)