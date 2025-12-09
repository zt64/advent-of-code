package util

data class Point3D(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Point3D): Point3D {
        return Point3D(x + other.x, y + other.y, z + other.z)
    }
    operator fun minus(other: Point3D): Point3D {
        return Point3D(x - other.x, y - other.y, z - other.z)
    }
    operator fun unaryMinus(): Point3D = Point3D(-x, -y, -z)

    fun dist(b: Point3D): Double {
        return sqrt((x - b.x).sq() + (y - b.y).sq() + (z - b.z).sq())
    }

    fun distSqr(b: Point3D): Int {
        return (x - b.x).sq() + (y - b.y).sq() + (z - b.z).sq()
    }
}