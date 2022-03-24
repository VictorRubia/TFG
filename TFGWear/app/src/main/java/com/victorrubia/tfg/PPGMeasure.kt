package com.victorrubia.tfg
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.*

object DateSerializer : KSerializer<Date> {
    override val descriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Date {
        return SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS").parse(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: Date) {
        val df = SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS")
        encoder.encodeString(df.format(value))
    }
}

@Serializable
data class PPGMeasure(val measure: Int,
                      @Serializable(with = DateSerializer::class)
                      val date: Date)