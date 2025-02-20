package extention

fun String.parseQueryString(): Map<String, String> {
    return this.split("&")
        .map { it.split("=") }
        .filter { it.size == 2 }
        .associate { it[0] to it[1] }
}