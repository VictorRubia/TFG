package data

data class Usuario(
    val name : String,
    val surname : String,
) {
    override fun toString(): String {
        return "Usuario(nombre='$name', apellidos='$surname')"
    }
}
