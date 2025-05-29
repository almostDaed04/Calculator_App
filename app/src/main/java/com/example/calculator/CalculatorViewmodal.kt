package com.example.calculator

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mariuszgromada.math.mxparser.Expression

class CalculatorViewmodel: ViewModel()  {

    private val _equationText = MutableLiveData("")
    val equationText = _equationText

    private val _resultText = MutableLiveData("")
    val resultText = _resultText

    var flag = mutableStateOf(false)

    fun onBtnClick(symbol: String){

        _equationText.value?.let {
            if (symbol == "AC") {
                _equationText.value = ""
                _resultText.value = ""
                return
            }
            if(symbol == "Del"){
                if(it.isEmpty()) return
                _equationText.value = _equationText.value?.dropLast(1)
                return
            }
            if(symbol == "="){
                try{
                    flag.value = true
                _resultText.value = _equationText.value?.let { it1 -> calculations(it1.toString())
                }

            }
            catch(e: Exception){
                println("Syntax Error")
            }
                return
            }
            flag.value = false

        val operators = listOf("+","-","*","%","/")
            if(operators.contains(symbol)){
                if(it.isEmpty()){
                    return
                }
                if(it.last().toString() in operators){
                    _equationText.value = it.dropLast(1)+symbol
                    return
                }
            }
            _equationText.value = it+symbol
        }
    }
    private fun calculations(equation: String): String {
        var expression = Expression(equation)
        var result = expression.calculate()

        if(result % 1.0 ==0.0){
        return result.toInt().toString()
        }
        else
            return result.toString()
    }
}