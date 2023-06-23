package uz.gita.a2048.repository

import android.util.Log
import uz.gita.a2048.data.source.local.MySharedPreferences
import kotlin.random.Random

class AppRepository private constructor() {
    private val pref = MySharedPreferences.getInstance()

    companion object {
        private lateinit var instance: AppRepository
        fun getInstance(): AppRepository {
            if (!::instance.isInitialized) {
                instance = AppRepository()
            }

            return instance
        }
    }

    private val NEW_ELEMENT = 2

    private var oldMatrix = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )
    var  scoreToDecrease = 0

    var score: Int
    var record: Int


    var matrix = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )

    init {
        if (pref.values == "") {
            addNewElement()
            addNewElement()
        } else {
            val array = pref.values.split("#")

            for (i in matrix.indices) {
                for (j in matrix[i].indices) {
                    matrix[i][j] = array[i * 4 + j].toInt()
                }
            }
        }
        score = pref.score
        record = pref.record
        scoreToDecrease = pref.decrease
        if (pref.oldValues == "")
            newToOldMatrix()
        else {
            val array = pref.oldValues.split("#")

            for (i in oldMatrix.indices) {
                for (j in oldMatrix[i].indices) {
                    oldMatrix[i][j] = array[i * 4 + j].toInt()
                }
            }

        }
    }


    fun saveScore() {
        pref.score = score
    }

    fun saveRecord() {
        pref.record = record
    }


    fun saveValues() {
        val sb = StringBuilder()
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                sb.append(matrix[i][j]).append("#")
            }
        }

        pref.values = sb.toString()
    }

    fun saveOldValues() {
        val sb = StringBuilder()
        for (i in oldMatrix.indices) {
            for (j in oldMatrix[i].indices) {
                sb.append(oldMatrix[i][j]).append("#")
            }
        }

        pref.oldValues = sb.toString()
    }

    fun saveScoreToDecrease(){
        pref.decrease = scoreToDecrease
    }

    private fun addNewElement() {
        val emptyList = ArrayList<Pair<Int, Int>>()

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) emptyList.add(Pair(i, j))
            }
        }
        val random = Random.nextInt(0, emptyList.size)
        matrix[emptyList[random].first][emptyList[random].second] = NEW_ELEMENT
    }


    fun moveToRight(): Boolean {
        if (hasMoveRight()) {
            newToOldMatrix()
            scoreToDecrease = 0
        }
        val newMatrix = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                newMatrix[i][j] = matrix[i][j]
            }
        }
        var addEl = false

        for (i in matrix.indices) {
            val amounts = ArrayList<Int>(4)

            var bool = true
            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[i][j] == 0) continue
                if (amounts.isEmpty())
                    amounts.add(matrix[i][j])
                else {
                    if (amounts.last() == matrix[i][j] && bool) {
                        score += amounts.last() * 2
                        scoreToDecrease += amounts.last() * 2
                        amounts[amounts.size - 1] = amounts.last() * 2
                        addEl = true
                        bool = false
                    } else {
                        amounts.add(matrix[i][j])
                        bool = true
                    }
                }
                matrix[i][j] = 0
            }

            for (k in amounts.indices) {
                matrix[i][matrix[i].size - 1 - k] = amounts[k]
            }
        }


        if (!addEl) {
            for (i in matrix.indices) {
                for (j in matrix[i].indices) {
                    if (matrix[i][j] != newMatrix[i][j]) {
                        addEl = true
                    }
                }
            }
        }

        if (addEl) {
            addNewElement()
            return true
        }

        return false
    }

    fun moveToLeft(): Boolean {
        if (hasMoveLeft()) {
            newToOldMatrix()
            scoreToDecrease = 0
        }
        val newMatrix = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )
        scoreToDecrease = 0
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                newMatrix[i][j] = matrix[i][j]
            }
        }
        var addEl = false

        for (i in matrix.indices) {
            val amounts = ArrayList<Int>(4)

            var bool = true
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) continue
                if (amounts.isEmpty())
                    amounts.add(matrix[i][j])
                else {
                    if (amounts.last() == matrix[i][j] && bool) {
                        score += amounts.last() * 2
                        scoreToDecrease += amounts.last() * 2
                        amounts[amounts.size - 1] = amounts.last() * 2
                        addEl = true
                        bool = false
                    } else {
                        amounts.add(matrix[i][j])
                        bool = true
                    }
                }
                matrix[i][j] = 0
            }

            for (k in amounts.indices) {
                matrix[i][k] = amounts[k]
            }
        }


        if (!addEl) {
            for (i in matrix.indices) {
                for (j in matrix[i].indices) {
                    if (matrix[i][j] != newMatrix[i][j]) {
                        addEl = true
                    }
                }
            }
        }

        if (addEl) {
            addNewElement()
            return true
        }
        return false

    }

    fun moveUp(): Boolean {
        if (hasMoveUp()) {
            newToOldMatrix()
            scoreToDecrease = 0
        }
        val newMatrix = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )
        scoreToDecrease = 0
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                newMatrix[i][j] = matrix[i][j]
            }
        }
        var addEl = false

        for (i in matrix.indices) {
            val amounts = ArrayList<Int>(4)

            var bool = true
            for (j in matrix[i].indices) {
                if (matrix[j][i] == 0) continue
                if (amounts.isEmpty())
                    amounts.add(matrix[j][i])
                else {
                    if (amounts.last() == matrix[j][i] && bool) {
                        score += amounts.last() * 2
                        scoreToDecrease += amounts.last() * 2
                        amounts[amounts.size - 1] = amounts.last() * 2
                        addEl = true
                        bool = false
                    } else {
                        amounts.add(matrix[j][i])
                        bool = true
                    }
                }
                matrix[j][i] = 0
            }

            for (k in amounts.indices) {
                matrix[k][i] = amounts[k]
            }
        }


        if (!addEl) {
            for (i in matrix.indices) {
                for (j in matrix[i].indices) {
                    if (matrix[i][j] != newMatrix[i][j]) {
                        addEl = true
                    }
                }
            }
        }

        if (addEl) {
            addNewElement()
            return true
        }
        return false

    }

    fun moveDown(): Boolean {
        if (hasMoveDown()) {
            newToOldMatrix()
            scoreToDecrease = 0
        }
        val newMatrix = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )
        scoreToDecrease = 0

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                newMatrix[i][j] = matrix[i][j]
            }
        }
        var addEl = false

        for (i in matrix.indices) {
            val amounts = ArrayList<Int>(4)

            var bool = true
            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[j][i] == 0) continue
                if (amounts.isEmpty())
                    amounts.add(matrix[j][i])
                else {
                    if (amounts.last() == matrix[j][i] && bool) {
                        score += amounts.last() * 2
                        scoreToDecrease += amounts.last() * 2
                        amounts[amounts.size - 1] = amounts.last() * 2
                        addEl = true
                        bool = false
                    } else {
                        amounts.add(matrix[j][i])
                        bool = true
                    }
                }
                matrix[j][i] = 0
            }

            for (k in amounts.indices) {
                matrix[matrix.size - 1 - k][i] = amounts[k]
            }
        }


        if (!addEl) {
            for (i in matrix.indices) {
                for (j in matrix[i].indices) {
                    if (matrix[i][j] != newMatrix[i][j]) {
                        addEl = true
                    }
                }
            }
        }

        if (addEl) {
            addNewElement()
            return true
        }

        return false
    }

    fun restart() {
        matrix = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )

        addNewElement()
        addNewElement()
        score = 0
        scoreToDecrease = 0
        newToOldMatrix()
    }

    fun hasMoveRight(): Boolean {

        val newMatrix = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )

        val array = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                newMatrix[i][j] = matrix[i][j]
            }
        }

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                array[i][j] = matrix[i][j]
            }
        }
        var addEl = false

        for (i in array.indices) {
            val amounts = ArrayList<Int>(4)

            var bool = true
            for (j in array[i].size - 1 downTo 0) {
                if (array[i][j] == 0) continue
                if (amounts.isEmpty())
                    amounts.add(array[i][j])
                else {
                    if (amounts.last() == array[i][j] && bool) {
                        amounts[amounts.size - 1] = amounts.last() * 2
                        addEl = true
                        bool = false
                    } else {
                        amounts.add(array[i][j])
                        bool = true
                    }
                }
                array[i][j] = 0
            }

            for (k in amounts.indices) {
                array[i][array[i].size - 1 - k] = amounts[k]
            }
        }


        if (!addEl) {
            for (i in array.indices) {
                for (j in array[i].indices) {
                    if (array[i][j] != newMatrix[i][j]) {
                        addEl = true
                    }
                }
            }
        }

        if (addEl) {
            return true
        }

        return false

    }

    fun hasMoveLeft(): Boolean {
        val newMatrix = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )

        val array = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                newMatrix[i][j] = matrix[i][j]
            }
        }

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                array[i][j] = matrix[i][j]
            }
        }
        var addEl = false

        for (i in array.indices) {
            val amounts = ArrayList<Int>(4)

            var bool = true
            for (j in array[i].indices) {
                if (array[i][j] == 0) continue
                if (amounts.isEmpty())
                    amounts.add(array[i][j])
                else {
                    if (amounts.last() == array[i][j] && bool) {
                        amounts[amounts.size - 1] = amounts.last() * 2
                        addEl = true
                        bool = false
                    } else {
                        amounts.add(array[i][j])
                        bool = true
                    }
                }
                array[i][j] = 0
            }

            for (k in amounts.indices) {
                array[i][k] = amounts[k]
            }
        }


        if (!addEl) {
            for (i in array.indices) {
                for (j in array[i].indices) {
                    if (array[i][j] != newMatrix[i][j]) {
                        addEl = true
                    }
                }
            }
        }

        if (addEl) {
            return true
        }

        return false
    }

    fun hasMoveUp(): Boolean {
        val newMatrix = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )

        val array = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                newMatrix[i][j] = matrix[i][j]
            }
        }

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                array[i][j] = matrix[i][j]
            }
        }
        var addEl = false


        for (i in array.indices) {
            val amounts = ArrayList<Int>(4)

            var bool = true
            for (j in array[i].indices) {
                if (array[j][i] == 0) continue
                if (amounts.isEmpty())
                    amounts.add(array[j][i])
                else {
                    if (amounts.last() == array[j][i] && bool) {
                        amounts[amounts.size - 1] = amounts.last() * 2
                        addEl = true
                        bool = false
                    } else {
                        amounts.add(array[j][i])
                        bool = true
                    }
                }
                array[j][i] = 0
            }

            for (k in amounts.indices) {
                array[k][i] = amounts[k]
            }
        }


        if (!addEl) {
            for (i in array.indices) {
                for (j in array[i].indices) {
                    if (array[i][j] != newMatrix[i][j]) {
                        addEl = true
                    }
                }
            }
        }

        if (addEl) {
            return true
        }

        return false
    }

    fun hasMoveDown(): Boolean {
        val newMatrix = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )

        val array = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                newMatrix[i][j] = matrix[i][j]
            }
        }

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                array[i][j] = matrix[i][j]
            }
        }
        var addEl = false

        for (i in array.indices) {
            val amounts = ArrayList<Int>(4)

            var bool = true
            for (j in array[i].size - 1 downTo 0) {
                if (array[j][i] == 0) continue
                if (amounts.isEmpty())
                    amounts.add(array[j][i])
                else {
                    if (amounts.last() == array[j][i] && bool) {
                        amounts[amounts.size - 1] = amounts.last() * 2
                        addEl = true
                        bool = false
                    } else {
                        amounts.add(array[j][i])
                        bool = true
                    }
                }
                array[j][i] = 0
            }

            for (k in amounts.indices) {
                array[array.size - 1 - k][i] = amounts[k]
            }
        }


        if (!addEl) {
            for (i in array.indices) {
                for (j in array[i].indices) {
                    if (array[i][j] != newMatrix[i][j]) {
                        addEl = true
                    }
                }
            }
        }

        if (addEl) {
            return true
        }

        return false
    }


    fun hasEmptySpace(): Boolean {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) {
                    return true
                }
            }
        }
        return false
    }

    fun isFinished(): Boolean {
        return !hasMoveRight() && !hasMoveLeft() && !hasMoveUp() && !hasMoveDown() && !hasEmptySpace()
    }

    fun newToOldMatrix() {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                oldMatrix[i][j] = matrix[i][j]
            }
        }
    }

    fun undo() {
       /* val sbOld = StringBuilder()
        val sb = StringBuilder()
        for (i in oldMatrix.indices){
            for (j in oldMatrix[i].indices){
                sbOld.append(oldMatrix[i][j]).append(" ")
                sb.append(matrix[i][j]).append(" ")
            }
        }

        Log.d("TTT","oldMatrix -> $sbOld")
        Log.d("TTT","matrix -> $sb")*/

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                matrix[i][j] = oldMatrix[i][j]
            }
        }
        score -= scoreToDecrease
        scoreToDecrease = 0
    }
}