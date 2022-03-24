package data

data class Usuario(
    val id : String,
    val name : String,
    val surname : String,
) {
    override fun toString(): String {
        return "Usuario(id='$id', name='$name', surname='$surname')"
    }
}