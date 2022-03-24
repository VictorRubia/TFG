package data

data class Actividad(
    val id : String,
    val name : String,
    val start_d : String,
    val end_d : String,
    val user_id : String,
) {
    override fun toString(): String {
        return "Actividad(id='$id', name='$name', start_d='$start_d', end_d='$end_d', user_id='$user_id')"
    }
}