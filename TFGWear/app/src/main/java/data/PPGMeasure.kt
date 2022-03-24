package data

data class PPGMeasure(
    val id : String,
    val measurement : String,
    val activity_id : String,
) {
    override fun toString(): String {
        return "PPGMeasure(id='$id', measurement='$measurement', activity_id='$activity_id')"
    }
}