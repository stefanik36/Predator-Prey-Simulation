interface MovementStrategy {

    fun getNextPosition(maxDistance: Double, currentPosition: Vector): Vector
}