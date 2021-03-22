package dev.unfair.game

object Logic {

    private val tiles = Sudoku.INSTANCE.tiles

     // Check if every tile is filled and if they are right
    fun checkPuzzle(): Boolean {
        var y = 0
        for (row in tiles) {
            var x = 0
            for (t in row) {
                if (t.isPre) {
                    x++
                    continue
                }

                if (t.num == 0) {
                    println("Incomplete!")
                    return false
                }

                if (!checkTile(x, y, t.num))
                    return false

                x++
            }

            y++
        }

        return true
    }

    // Check if there is a duplicate number in the same row and column
    private fun checkTile(x: Int, y: Int, num: Int): Boolean {
        for (t in tiles[y]) {
            if (t.num == num)
                return false
        }

        for (row in tiles) {
            if (row[x].num == num)
                return false
        }

        return true
    }

}